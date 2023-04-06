package edu.lab1.models;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

import static edu.lab1.models.GraphObjectType.TEXT;
import static java.lang.Math.*;

public class Text extends AbstractGraphObject implements Serializable {
    transient JLabel jl = new JLabel("Бархатные тяги");

    transient private double a = 0;

    private int radius;

    private double centerX, centerY;
    public Text(double x, double y) {
        super(x, y,0, 0);
//        jl.setPreferredSize(new Dimension(3, 300));
        jl.setFont(new Font("Verdana", Font.PLAIN, 14));
        jl.setForeground(Color.GREEN);
        radius = 2 + (int)(Math.random() * ((600 - 2) + 1));
        centerX = x;
        centerY = y + radius;
        type = TEXT;

//        setHeight((int)jl.getPreferredSize().getHeight());
//        setWidth((int)jl.getPreferredSize().getWidth());

//        setX(getX()-getWidth()/2);
//        setY(getY()-getHeight()/2);
    }

    public Text(double x, double y, int radius, double centerX, double centerY) {
        super(x, y,0, 0);
//        jl.setPreferredSize(new Dimension(3, 300));
        jl.setFont(new Font("Verdana", Font.PLAIN, 14));
        jl.setForeground(Color.GREEN);
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        type = TEXT;

//        setHeight((int)jl.getPreferredSize().getHeight());
//        setWidth((int)jl.getPreferredSize().getWidth());

//        setX(getX()-getWidth()/2);
//        setY(getY()-getHeight()/2);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Бархатные тяги", (int)getX(), (int)(getY()+getHeight()));
    }

    @Override
    public void moveX(double v) {
        if (isMoving) setX(getCenterX() + v);
    }

    @Override
    public void moveY(double v) {
        if (isMoving) setY(getCenterY() + v);
    }

    @Override
    public void move() {
        if (a >= 2 * PI) {
            a = 0;
        }
        a += 0.000001;
        moveX(radius*cos(a));
        moveY(radius*sin(a));
    }

    @Override
    public String toString() {
        return "Text\n" + x + "\n" + y + "\n" + radius +
                "\n" + centerX +
                "\n" + centerY + "\n";
    }
}