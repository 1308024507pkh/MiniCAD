package miniCAD.shapes;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;

public abstract class Shape implements Serializable{
    private static final long serialVersionUID = 1L;

    private String type;        //the type of the shape
    private int x1, x2, y1, y2; //the coordinates of the shape
    private Color color;        //the color of the shape
    private float stroke;       //the stroke of the shape
    private HashSet<Point> pointSet = new HashSet<Point>(); //all points the shape contains

    public Shape(String type,int x1, int x2, int y1, int y2, Color color, float stroke){
        this.type = type;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
    }

    //get the coordinates of the shape
    public int getX1(){
        return x1;
    }

    public int getX2(){
        return x2;
    }

    public int getY1(){
        return y1;
    }

    public int getY2(){
        return y2;
    }

    //get the color of the shape
    public Color getColor(){
        return color;
    }

    //get the stroke of the shape
    public float getStroke(){
        return stroke;
    }

    //get the type of the shape
    public String getType(){
        return type;
    }

    //set the type of the shape
    public void setType(String type){
        this.type = type;
    }

    //add the point the shape contains
    public void addPoint(Point p){
        pointSet.add(p);
    }

    //judge whether the shape contains (x,y)
    public boolean isContains(int x, int y){
        Point p = new Point(x, y);
        return pointSet.contains(p);
    }

    //draw the shape
    public abstract void drawShape(Graphics2D g);

}

