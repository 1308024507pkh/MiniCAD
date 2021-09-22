package miniCAD.shapes;

import java.awt.*;
import java.lang.Math;

public class Oval extends Shape{
    private static final long serialVersionUID = 1L;
    public Oval(int x1, int x2, int y1, int y2, Color color, float stroke){
        super("Oval", x1, x2, y1, y2, color, stroke);
    }

    //draw an oval
    public void drawShape(Graphics2D g){
        int minX = Math.min(getX1(), getX2());
        int minY = Math.min(getY1(), getY2());
        int width = Math.abs(getX1()-getX2());
        int height = Math.abs(getY1()-getY2());
        g.setPaint(getColor());
        g.setStroke(new BasicStroke(getStroke()));
        g.drawOval(minX, minY, width, height);
        setPointSet();
    }

    //set the points the oval contains
    public void setPointSet(){
        int i, j, m;
        int dx = Math.abs(getX1()-getX2()), dy = Math.abs(getY1()-getY2());
        double minX = (double)Math.min(getX1(), getX2()), minY = (double)Math.min(getY1(), getY2());
        float stroke = getStroke();
        int k = (int)(stroke/2);
        if(k>=20)   k = 20;
        else if(k<2)    k = 2;
        for(i=0; i<360; i++){
            for(j=-k; j<=k; j++){
                for(m=-k; m<=k; m++){
                    double d1 = (double)dx/2;
                    double d2 = (double)dy/2;
                    int x = (int)(minX+(double)j+(1+Math.cos((double)i/360*Math.PI*2))*d1 );
                    int y = (int)(minY+(double)m+(1+Math.sin((double)i/360*Math.PI*2))*d2 );
                    addPoint(new Point(x, y));
                }
            }
        }
    }
}