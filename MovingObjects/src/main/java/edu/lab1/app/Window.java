package edu.lab1.app;

import javax.swing.*;
import java.io.IOException;
import java.io.*;

public class Window extends JFrame {
    JMenuBar menuBar = new JMenuBar();
    JMenu control = new JMenu("Control");
    JMenu serialization = new JMenu("Serialization");
    JMenuItem moveP = new JMenuItem("Click Q for move/stop picture");
    JMenuItem moveS = new JMenuItem("Click W for move/stop star");
    JMenuItem moveAll = new JMenuItem("Click E for move/stop all objects");

    JMenuItem menuSaveTXT = new JMenuItem("Save to TXT");
    JMenuItem menuLoadTXT = new JMenuItem("Load from TXT");
    JMenuItem menuSaveBIN = new JMenuItem("Save to BIN");
    JMenuItem menuLoadBIN = new JMenuItem("Load from BIN");
    JMenuItem menuSaveXML = new JMenuItem("Save to XML");
    JMenuItem menuLoadXML = new JMenuItem("Load from XML");

    public Window(){
        setTitle("Moving Objects");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(1800, 1000);
        setLocation(0, 0);
        EventListener habitatPanel = new EventListener();
        add(habitatPanel);

        moveP.setEnabled(false);
        moveS.setEnabled(false);
        moveAll.setEnabled(false);
        control.add(moveP);
        control.add(moveS);
        control.add(moveAll);
        menuBar.add(control);
        serialization.add(menuSaveTXT);
        serialization.add(menuLoadTXT);
        serialization.add(menuSaveBIN);
        serialization.add(menuLoadBIN);
        serialization.add(menuSaveXML);
        serialization.add(menuLoadXML);
        menuBar.add(serialization);
        add(menuBar);
        setJMenuBar(menuBar);
        setVisible(true);
        menuSaveTXT.addActionListener(e->{
            habitatPanel.saveToTXT();
        });

        menuLoadTXT.addActionListener(e-> {
            try {
                habitatPanel.loadTXT();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuSaveBIN.addActionListener(e->{
            try {
                habitatPanel.saveToBIN();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuLoadBIN.addActionListener(e-> {
            try {
                habitatPanel.loadBIN();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuSaveXML.addActionListener(e->{
            try {
                habitatPanel.saveToXML();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuLoadXML.addActionListener(e-> {
            try {
                habitatPanel.loadXML();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }


}



