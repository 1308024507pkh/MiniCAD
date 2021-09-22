package miniCAD.shapes;

import java.awt.*;
import java.lang.Math;

public class Rectangle extends Shape{
    private static final long serialVersionUID = 1L;
    public Rectangle(int x1, int x2, int y1, int y2, Color color, float stroke){
        super("Rectangle", x1, x2, y1, y2, color, stroke);
    }

    //draw a rectangle
    public void drawShape(Graphics2D g){
        int minX = Math.min(getX1(), getX2());
        int minY = Math.min(getY1(), getY2());
        int width = Math.abs(getX1()-getX2());
        int height = Math.abs(getY1()-getY2());
        g.setPaint(getColor());
        g.setStroke(new BasicStroke(getStroke()));
        g.drawRect(minX, minY, width, height);
        setPointSet();
    }

    //set the points the rectangle contains
    public void setPointSet(){
        int i, j, m;
        int minX = Math.min(getX1(), getX2()), maxX = Math.max(getX1(), getX2());
        int minY = Math.min(getY1(), getY2()), maxY = Math.max(getY1(), getY2());
        float stroke = getStroke();
        int k = (int)(stroke/2);
        if(k>=20)   k = 20;
        else if(k<2)    k = 2;
        for(i = -k; i<=k; i++){
            for(j = -k; j<=k; j++){
                for(m = minX+i; m<=maxX+i; m++){
                    addPoint(new Point(m, getY1()+j));
                    addPoint(new Point(m, getY2()+j));
                }
                for(m = minY+j; m<=maxY+j; m++){
                    addPoint(new Point(getX1()+i, m));
                    addPoint(new Point(getX2()+i, m));
                }
            }
        }
    }

}
