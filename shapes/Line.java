package miniCAD.shapes;

import java.awt.*;

public class Line extends Shape{
    private static final long serialVersionUID = 1L;
    public Line(int x1, int x2, int y1, int y2, Color color, float stroke){
        super("Line", x1, x2, y1, y2, color, stroke);
    }

    //draw a line
    public void drawShape(Graphics2D g){
        g.setPaint(getColor());
        g.setStroke(new BasicStroke(getStroke()));
        g.drawLine(getX1(), getY1(), getX2(), getY2());
        setPointSet();
    }

    //set the points the line contains
    public void setPointSet(){
        int i, j;
        int minX = Math.min(getX1(), getX2()), maxX = Math.max(getX1(), getX2());
        int minY = Math.min(getY1(), getY2()), maxY = Math.max(getY1(), getY2());
        float k1 = (float)(getY1()-getY2())/(getX1()-getX2());
        float k2 = (float)(getX1()-getX2())/(getY1()-getY2());
        float stroke = getStroke();
        int k = (int)(stroke/2);
        if(k>=20)   k = 20;
        else if(k<2)    k = 2;
        for(i=-k; i<=k; i++){
            if(k1<=1 && k1>=-1){
                for(j = minX+i; j<=maxX+i; j++)
                    addPoint(new Point(j,(int)(k1*(j-(getX1()+i))+getY1())));
                for(j = minX; j<=maxX; j++)
                    addPoint(new Point(j,(int)(k1*(j-getX1())+getY1()+i)));
            }
            else if(k2>=-1 && k2<=1){
                for(j = minY+i; j<=maxY+i; j++)
                    addPoint(new Point((int)(k2*(j-(getY1()+i))+getX1()),j));
                for(j = minY; j<=maxY; j++)
                    addPoint(new Point((int)(k2*(j-getY1())+getX1()+i), j));
            }
            else{
                for(j = -k; j<=k; j++)
                    addPoint(new Point(getX1()+j,getY1()+i));
            }
        }
    }

}

