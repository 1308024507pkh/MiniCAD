package miniCAD.components;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import miniCAD.*;
import miniCAD.shapes.Shape;

public class MyCanvas extends JPanel{
    private static final long serialVersionUID = 1L;
    ArrayList<Shape> shapes;
    View view;

    public MyCanvas(ArrayList<Shape> shapes, View view){
        this.shapes = shapes;
        this.view = view;
        this.setBackground(Color.WHITE);
    }

    //how to paint the canvas
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.Repaint(g, shapes);
    }

    //set the shapes on the canvas
    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
}
