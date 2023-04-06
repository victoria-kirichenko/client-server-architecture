package edu.lab1.app;

import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.thoughtworks.xstream.security.AnyTypePermission;
import edu.lab1.models.AbstractGraphObject;
import edu.lab1.models.GraphObjectType;
import edu.lab1.models.Picture;
import edu.lab1.models.Text;
import org.apache.commons.io.IOUtils;


public class EventListener extends JPanel {
    final public static int WIDTH = 1800;
    final public static int HEIGHT = 1000;
    final private int POSITION_X = 0;
    final private int POSITION_Y = 0;

    private Thread thread;

    public ArrayList<AbstractGraphObject> list = new ArrayList<>();
    private boolean toDraw = true;
    private boolean isRunImage = true;
    private boolean isRunText = true;

    public EventListener() {
        setBackground(Color.white);
        setSize(WIDTH, HEIGHT);
        setLocation(POSITION_X, POSITION_Y);
        setFocusable(true);
        addMouseListener(new PanelMouseListener());
        addKeyListener(new PanelKeyboardListener());
        setLayout(null);

        thread = new Thread(() -> update());
        thread.start();
    }

    public void saveToTXT () {
        try (OutputStream f = new FileOutputStream("file.txt")) {
            try (OutputStreamWriter writer = new OutputStreamWriter(f)) {
                for (Object o : list)
                    writer.write(o.toString());
            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
    }

    public void loadTXT () throws FileNotFoundException {
        InputStream is = new FileInputStream("C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\file.txt");
        Scanner scanner = new Scanner(is);
        double x, y, centerX, centerY;
        int radius;
        while (scanner.hasNextLine()) {
            String className = scanner.nextLine();
            System.out.println(className);
            switch (className) {
                case "Picture":
                        x = Double.parseDouble(scanner.nextLine().trim());
                        y = Double.parseDouble(scanner.nextLine().trim());
                        list.add(new Picture(x, y, "C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\src\\main\\resources\\barhat2.png"));
                        break;
                case "Text":
                        x = Double.parseDouble(scanner.nextLine());
                        y = Double.parseDouble(scanner.nextLine());
                        radius = Integer.parseInt(scanner.nextLine().trim());
                        centerX = Double.parseDouble(scanner.nextLine().trim());
                        centerY = Double.parseDouble(scanner.nextLine().trim());
                        list.add(new Text(x, y, radius, centerX, centerY));
                        break;
            }
        }
        updateUI();
    }

    public void saveToBIN () throws IOException {
        OutputStream f = new FileOutputStream("file.txt");
        ObjectOutputStream writer = new ObjectOutputStream(f);
        writer.writeObject(list);
        writer.flush();
        writer.close();
    }

    public void loadBIN () throws IOException, ClassNotFoundException {
        InputStream is = new FileInputStream("C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\file.txt");
        ObjectInputStream ois = new ObjectInputStream(is);
        list = (ArrayList<AbstractGraphObject>) ois.readObject();
        ois.close();
    }
    public void saveToXML() throws IOException {
        OutputStream f = new FileOutputStream("file.xml");
        OutputStreamWriter osw = new OutputStreamWriter(f);
        XStream xstream = new XStream();
        String dataXml = xstream.toXML(list);
        f.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
        byte[] bytes = dataXml.getBytes("UTF-8");
        f.write(bytes);
        osw.flush();
        osw.close();
    }

    public void loadXML() throws IOException {
        InputStream is = new FileInputStream("C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\file.xml");
        XStream xstream = new XStream();
        String xmlData = IOUtils.toString(is);
        xstream.addPermission(AnyTypePermission.ANY);
        list = (ArrayList<AbstractGraphObject>) xstream.fromXML(xmlData);
    }

    public void addImage(int x, int y) {
        list.add(new Picture(x, y, "C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\src\\main\\resources\\barhat2.png"));
    }

    public void addText(int x, int y) {
        list.add(new Text(x, y));
    }

    public void moveObject() {
        AbstractGraphObject o;

        for (int i = 0; i < list.size(); i++) {
            o = list.get(i);
            if (o != null) {
                if (o.getType().equals(GraphObjectType.IMAGE) && isRunImage) {
                    o.move();
                } else if (o.getType().equals(GraphObjectType.TEXT) && isRunText) {
                    o.move();
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).paint(g);
        }
    }

    public void update() {
        while (true) {
            moveObject();
            repaint();
        }
    }
    class PanelMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isBelongs(e.getX(), e.getY())) {
                    if (list.get(i).getType().equals(GraphObjectType.IMAGE)) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            list.remove(i);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            if (list.get(i).isMoving()) {
                                list.get(i).setMoving(false);
                            } else {
                                list.get(i).setMoving(true);
                            }
                        }
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            list.remove(i);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            if (list.get(i).isMoving()) {
                                list.get(i).setMoving(false);
                            } else {
                                list.get(i).setMoving(true);
                            }
                        }
                    }
                    toDraw = false;
                }
            }
            if (toDraw) {
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    addImage(e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    addText(e.getX(), e.getY());
                }
            }
            toDraw = true;
        }
    }
    class PanelKeyboardListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                isRunImage = (isRunImage) ? false : true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                isRunText = (isRunText) ? false : true;
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                isRunImage = (isRunImage) ? false : true;
                isRunText = (isRunText) ? false : true;
            }
        }
    }
}

