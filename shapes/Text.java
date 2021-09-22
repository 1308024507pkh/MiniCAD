package miniCAD.shapes;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Text extends Shape{
    private static final long serialVersionUID = 1L;

    private String style, content;
    private int size;
    private double height, width;

    public Text(int x1, int x2, int y1, int y2, Color color, float stroke, String style, String content, int size){
        super("Text", x1, x2, y1, y2, color, stroke);
        this.style = style;
        this.content = content;
        this.size = size;
    }

    //draw a text
    public void drawShape(Graphics2D g){
        Font f = new Font(style, getX2()+getY2(), size);
        g.setPaint(getColor());
        g.setFont(f);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D rec = f.getStringBounds(content, context);
        height = rec.getHeight();
        width = rec.getWidth();
        if(content!=null)
            g.drawString(content, (int)(getX1()-width/2), (int)(getY1()+height/6));
        setPointSet();
    }

    //set the points the text contains
    public void setPointSet(){
        int i, j;
        for(i = (int)(getX1()-width/2); i<=getX1()+width/2;i++)
            for(j = (int)(getY1()-height/2.0); j<=getY1()+height/3.0; j++)
                addPoint(new Point(i,j));
    }

    //get the font size of the text
    public int getFontSize(){
        return size;
    }

    //get the font style of the text
    public String getStyle(){
        return style;
    }

    //get the content of the text
    public String getContent(){
        return content;
    }
}
