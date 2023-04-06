package edu.lab1.models;

import edu.lab1.app.EventListener;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

import static edu.lab1.models.GraphObjectType.IMAGE;

public class Picture extends AbstractGraphObject implements Serializable {

    transient Image image;

    String path;

    double square = 500;

    double count = 0;

    double speed = 0.00001;

    public Picture(double x, double y, int height, int width){
        super(x, y, height, width);
        setHeight(image.getHeight(null));
        setWidth(image.getWidth(null));
        path = "C:\\Users\\Виктория\\dev\\programming\\ClientServer_labs\\MovingObjects\\src\\main\\resources\\barhat2.png";
        type = IMAGE;
    }

    public Picture(double x, double y, String filename){

        super(x, y, 0, 0);
        path = filename;
        image = new ImageIcon(filename).getImage();

        setHeight(image.getHeight(null));
        setWidth(image.getWidth(null));

        type = IMAGE;

//        setX(getX()-getWidth()/2);
//        setY(getY()-getHeight()/2);

        System.out.println("Добавляем картинку по заданным координатам:");
        System.out.println("Ширина: " + getX());
        System.out.println("Высота: " + getY());
        System.out.println("Картинка успешно добавлена!" + "\n");
    }

    public String getPath() {
        return path;
    }

    @Override
    public void paint(Graphics g){
        image = new ImageIcon(path).getImage();
        g.drawImage(image, (int) getX(), (int) getY(), null);
    }

    @Override
    public void moveX(double v) {
        if (isMoving) setX(getX() + v);
    }

    @Override
    public void moveY(double v) {
        if (isMoving) setY(getY() + v);
    }

    @Override
    public void move() {
        if (isBottom == false &&isLeft == false && isRight == false && isTop == false) {
            isBottom = true;
        }
        if (isBottom) {
            if (getY() + speed >= EventListener.HEIGHT || count > square) {
                isRight = true;
                isBottom = false;
                count = 0;
            } else {
                moveY(speed);
                count += speed;
            }
        } else if (isRight) {
            if (getX() + speed >= EventListener.WIDTH || count > square) {
                isTop = true;
                isRight = false;
                count = 0;
            } else {
                moveX(speed);
                count += speed;
            }
        } else if (isTop) {
            if (getY() - speed <= 0 || count > square) {
                isLeft = true;
                isTop = false;
                count = 0;
            } else {
                moveY(-speed);
                count += speed;
            }
        } else if (isLeft) {
            if (getX() - speed <= 0 || count > square) {
                isBottom = true;
                isLeft = false;
                count = 0;
            } else {
                moveX(-speed);
                count += speed;
            }
        }
    }

    @Override
    public String toString() {
        return "Picture\n" + x +
                "\n" + y + "\n";
    }
}