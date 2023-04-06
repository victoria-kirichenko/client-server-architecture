package edu.lab1.models;

import java.awt.*;
import java.io.Serializable;

abstract public class AbstractGraphObject implements Movement, Serializable {
    double x;
    double y;
    transient double height;
    transient double width;

    GraphObjectType type;

    boolean isRight, isLeft, isTop, isBottom = false;

    boolean isMoving = true;

    public AbstractGraphObject(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public GraphObjectType getType() {
        return type;
    }
    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isBelongs(int x, int y){
        if(this.x < x & (this.x + width) > x ){
            if(this.y < y & (this.y + height) > y){
                return true;
            }
        }

        return false;
    }

    public abstract void paint(Graphics g);
}