package miniCAD;

import java.util.ArrayList;
import java.awt.*;

import miniCAD.shapes.Shape;

public class View {
    //repaint all shapes on the canvas
    public void Repaint(Graphics g, ArrayList<Shape> shapes){
        for(Shape s : shapes)
            s.drawShape((Graphics2D)g);
    }
}

