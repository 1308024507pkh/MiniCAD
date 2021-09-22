package miniCAD;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

import miniCAD.components.*;
import miniCAD.shapes.*;
import miniCAD.shapes.Shape;
import miniCAD.shapes.Rectangle;
import miniCAD.shapes.Text;

public class DrawControl {

    Model model;
    State state;

    private Shape curShape;  //the current shape
    private int curId = -1;  //the index of the current shape
    private String curType;  //the type of the current shape
    private int curX1, curX2, curY1, curY2; //the coordinate of the current shape
    private int moveX1, moveX2, moveY1, moveY2; //how to move or resize the current shape
    private Color curColor = Color.BLACK; //the color of the current shape
    private float curStroke; //the stroke of the current shape
    private String curStyle, curContent; //the current attribute of the font bar
    private int curSize, isBold = Font.PLAIN, isItalics = Font.PLAIN;
    private int isDragged = 0;
    private float k1, k2; //slope
    private int centerX, centerY; //midpoint coordinates
    private ShapeLists shapeLists;
    private int wheelChoice = 1;
    private int mouseButton = 1;
    //the shape of the cursor
    private Image img1 = Toolkit.getDefaultToolkit().getImage("src/miniCAD/image/drawCursor.png");
    private	Image img2 = Toolkit.getDefaultToolkit().getImage("src/miniCAD/image/deleteCursor.png");
    private	Image img3 = Toolkit.getDefaultToolkit().getImage("src/miniCAD/image/resizeCursor.png");

    public DrawControl(Model model, State state, ShapeLists shapeLists){
        this.model = model;
        this.state = state;
        this.shapeLists = shapeLists;
    }

    public void controlListen(){
        actionListen();
        keyboardListen();
        mouseListen();
    }

    public void init(){
        curShape = null;
        curId = -1;
        curType = "";
        curX1 = curX2 = curY1 = curY2 = 0;
        moveX1 = moveX2 = moveY1 = moveY2 = 0;
    }

    public void actionListen(){
        //clear the canvas
        model.getMenu().getClear().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(model.getNum()!=0)
                    shapeLists.addShapeLists(model.getShapes());
                model.clearShape();
                init();
                if(state.getState() == State.Task.SELECT)
                    state.setState(State.Task.EMPTY);
            }
        });
        //recover the canvas
        model.getMenu().getRecover().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(shapeLists.getSize()!=0){
                    model.setShapes(shapeLists.getShapeList());
                }
                init();
                if(state.getState() == State.Task.SELECT)
                    state.setState(State.Task.EMPTY);
            }
        });
        //get the current color
        model.getColorBar().getColorPanel().addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent e) {
                curColor = model.getColor();
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    shapeLists.addShapeLists(model.getShapes());
                    createShape();
                    modifyShape();
                }
            }
        });
        //get the current font style
        model.getFontBar().getStyleBox().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                curStyle = model.getFontBar().getStyle();
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    shapeLists.addShapeLists(model.getShapes());
                    createShape();
                    modifyShape();
                }
            }
        });
        //get the current font size
        model.getFontBar().getSizeBox().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                curSize = model.getFontBar().getFontSize();
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    shapeLists.addShapeLists(model.getShapes());
                    createShape();
                    modifyShape();
                }
            }
        });
        //get whether the font is italics
        model.getFontBar().getItalicsBox().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                isItalics = model.getFontBar().getItalics();
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    shapeLists.addShapeLists(model.getShapes());
                    createShape();
                    modifyShape();
                }
            }
        });
        //get whether the font is bold
        model.getFontBar().getBoldBox().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                isBold = model.getFontBar().getBold();
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    shapeLists.addShapeLists(model.getShapes());
                    createShape();
                    modifyShape();
                }
            }
        });
    }

    public void keyboardListen(){
        model.getCanvas().addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())) {
                    //change the stroke of the current shape
                    if(e.getKeyCode()==38 || e.getKeyCode()==40){
                        if(e.getKeyCode() == 38){
                            curStroke += 1;
                            if(curStroke<=0)    curStroke = 1;
                        }
                        else{
                            curStroke -= 1;
                            if(curStroke<=0)    curStroke = 1;
                        }
                        if(curType.equals("Text")){
                            if(e.getKeyCode() == 38)
                                curSize += 5;
                            else    curSize -= 5;
                            if(curSize<=5)  curSize = 5;
                            if(curSize>=200)    curSize = 200;
                            model.getFontBar().setFontSize(curSize);
                        }
                        shapeLists.addShapeLists(model.getShapes());
                        createShape();
                        modifyShape();
                    }
                    //change the size of the current shape
                    else if(e.getKeyCode()==37 || e.getKeyCode()==39){
                        moveX1 = moveY2 = 0;
                        if(e.getKeyCode()==39)
                            moveX2 = moveY1 = 1;
                        else
                            moveX2 = moveY1 = -1;
                        shapeLists.addShapeLists(model.getShapes());
                        resizeShape();
                    }
                    //delete the current shape
                    else if(e.getKeyCode()==127){
                        shapeLists.addShapeLists(model.getShapes());
                        model.deleteShape(curId);
                        curId = -1;
                        if(state.getState() == State.Task.SELECT)
                            state.setState(State.Task.EMPTY);
                    }
                }
            }
        });
    }

    public void mouseListen(){
        model.getCanvas().addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e){
                if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                        (0<=curId && curId<=model.getNum())){
                    //change the stroke of the current shape
                    if(wheelChoice == -1){
                        curStroke -= e.getWheelRotation();
                        if(curStroke<=0)    curStroke = 1;
                        if(curType.equals("Text")){
                            curSize -= e.getWheelRotation()*5;
                            if(curSize<=5)  curSize = 5;
                            if(curSize>=200)    curSize = 200;
                            model.getFontBar().setFontSize(curSize);
                        }
                        shapeLists.addShapeLists(model.getShapes());
                        createShape();
                        modifyShape();
                    }
                    //change the size of the current shape
                    else{
                        moveX1 = moveY2 = 0;
                        moveX2 = moveY1 = -e.getWheelRotation();
                        shapeLists.addShapeLists(model.getShapes());
                        resizeShape();
                    }
                }
            }
        });

        model.getCanvas().addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                String buttName = model.getCommand();
                if(e.getButton() == 2){
                    mouseButton = 2;
                    if((state.getState() == State.Task.SELECT || state.getState() == State.Task.DRAW) &&
                            (0<=curId && curId<=model.getNum())) {
                        wheelChoice *= -1;
                    }
                    return;
                }
                //set the initial state
                state.setState(State.Task.EMPTY);
                if(e.getButton() == 1){
                    mouseButton = 1;
                    //turn into DRAW
                    if(buttName.equals("Circle") || buttName.equals("Line") || buttName.equals("Oval") ||
                            buttName.equals("Rectangle") || buttName.equals("Text")){
                        state.setState(State.Task.DRAW);
                        curType = buttName;
                    }
                    else if(buttName.equals("Delete") || buttName.equals("Select")){
                        curId = model.getIndex(e.getX(), e.getY());
                        if(curId == -1)
                            state.setState(State.Task.EMPTY);
                        else{
                            //turn into DELETE
                            if(buttName.equals("Delete"))
                                state.setState(State.Task.DELETE);
                                //turn into SELECT
                            else{
                                state.setState(State.Task.SELECT);
                                model.getCanvas().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img3, new Point(15, 15), "Cursor"));
                                model.getCanvas().requestFocus();
                            }
                        }
                    }
                }
                else if(e.getButton() == 3){
                    mouseButton = 3;
                    if(buttName.equals("Select")){
                        curId = model.getIndex(e.getX(), e.getY());
                        if(curId == -1)
                            state.setState(State.Task.EMPTY);
                            //turn into SELECT
                        else{
                            state.setState(State.Task.SELECT);
                            model.getCanvas().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                            model.getCanvas().requestFocus();
                        }
                    }
                }
                //Draw a new shape
                if(state.getState() == State.Task.DRAW){
                    curX1 = curX2 = e.getX();
                    curY1 = curY2 = e.getY();
                    curStroke = 5;
                    curSize = model.getFontBar().getFontSize();
                    curStyle = model.getFontBar().getStyle();
                    isBold = model.getFontBar().getBold();
                    isItalics = model.getFontBar().getItalics();
                    curColor = model.getColor();
                    curContent = null;
                    if(curType.equals("Text"))
                        curContent = JOptionPane.showInputDialog(null, "输入文本", "请输入文本: ");
                    if(curContent!=null || !curType.equals("Text")){
                        shapeLists.addShapeLists(model.getShapes()); //save the original shapes
                        createShape();
                        setProperty();
                        model.addShape(curShape);
                        curId = model.getNum() - 1;
                    }
                }
                //Delete a shape
                else if(state.getState() == State.Task.DELETE){
                    shapeLists.addShapeLists(model.getShapes());
                    model.deleteShape(curId);
                    curId = -1;
                    state.setState(State.Task.EMPTY);
                }
                //select a shape
                else if(state.getState() == State.Task.SELECT){
                    getCurShape();
                    moveX1 = moveX2 = e.getX();
                    moveY1 = moveY2 = e.getY();
                }

            }

            public void mouseReleased(MouseEvent e){
                isDragged = 0;
                setCursorShape(e);
            }
        });

        model.getCanvas().addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e){
                if(mouseButton == 2)    return;
                if(isDragged == 0){
                    if(state.getState() == State.Task.SELECT)
                        shapeLists.addShapeLists(model.getShapes());
                    isDragged = 1;
                }
                //draw the shape
                if(state.getState() == State.Task.DRAW && !curType.equals("Text")){
                    curX2 = e.getX();
                    curY2 = e.getY();
                    createShape();
                    setProperty();
                    model.setShape(model.getNum()-1, curShape);
                }
                else if(state.getState() == State.Task.SELECT){
                    moveX2 = e.getX();
                    moveY2 = e.getY();
                    //move the shape
                    if(mouseButton == 3){
                        model.getCanvas().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        moveShape();
                    }
                    //resize the shape
                    else if(mouseButton == 1){
                        model.getCanvas().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img3, new Point(15, 15), "Cursor"));
                        resizeShape();
                    }
                }
                model.getCanvas().requestFocus();
            }

            public void mouseMoved(MouseEvent e){
                setCursorShape(e);
            }
        });
    }

    //set the shape of the cursor
    public void setCursorShape(MouseEvent e){
        String buttName = model.getCommand();
        MyCanvas canvas = model.getCanvas();
        //the shape of cursor when drawing
        if(buttName.equals("Line") || buttName.equals("Rectangle") || buttName.equals("Circle") || buttName.equals("Oval")){
            canvas.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img1, new Point(15, 15), "Cursor"));
        }
        //the shape of cursor when drawing the text
        else if(buttName.equals("Text")){
            canvas.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        }
        else if(model.getIndex(e.getX(), e.getY()) != -1){
            //the shape of cursor when deleting a shape
            if(buttName.equals("Delete")){
                canvas.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img2, new Point(15, 15), "Cursor"));
            }
            //the shape of cursor when selecting a shape
            else if(buttName.equals("Select")){
                canvas.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img3, new Point(15, 15), "Cursor"));
            }
            else{
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        else{
            canvas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        canvas.requestFocus();
    }

    public void createShape(){
        //create a line
        if(curType.equals("Line")){
            curShape = new Line(curX1, curX2, curY1, curY2, curColor, curStroke);
        }
        //create a rectangle
        else if(curType.equals("Rectangle")){
            curShape = new Rectangle(curX1, curX2, curY1, curY2, curColor, curStroke);
        }
        //create a circle
        else if(curType.equals("Circle")){
            curShape = new Circle(curX1, curX2, curY1, curY2, curColor, curStroke);
        }
        //create a oval
        else if(curType.equals("Oval")){
            curShape = new Oval(curX1, curX2, curY1, curY2, curColor, curStroke);
        }
        //create a text
        else if(curType.equals("Text")){
            curShape = new Text(curX1, isBold, curY1, isItalics, curColor, curStroke, curStyle, curContent, curSize);
        }
    }

    //Set the slope and midpoint coordinates of the shape
    public void setProperty(){
        if(!curType.equals("Circle")){
            k1 = (float)(curY2-curY1)/(curX2-curX1);
            k2 = (float)(curX2-curX1)/(curY2-curY1);
            centerX = (int)((double)Math.min(curX1, curX2)+(double)Math.abs(curX1-curX2)/2.0);
            centerY = (int)((double)Math.min(curY1, curY2)+(double)Math.abs(curY1-curY2)/2.0);
        }
        else{
            k1 = k2 = 1;
            centerX = (int)((double)Math.min(curX1, curX2)+(double)Math.max(Math.abs(curX1-curX2), Math.abs(curY1-curY2))/2.0);
            centerY = (int)((double)Math.min(curY1, curY2)+(double)Math.max(Math.abs(curX1-curX2), Math.abs(curY1-curY2))/2.0);
        }

    }

    //modify the current shape
    public void modifyShape(){
        model.setShape(curId, curShape);
    }

    //get the attribute of the current shape
    public void getCurShape(){
        curShape = model.getShape(curId);
        curType = curShape.getType();
        curX1 = curShape.getX1();
        curX2 = curShape.getX2();
        curY1 = curShape.getY1();
        curY2 = curShape.getY2();
        curColor = curShape.getColor();
        curStroke = curShape.getStroke();
        setProperty();
        if(curType.equals("Text")){
            Text curText = (Text)curShape;
            curStyle  = curText.getStyle();
            curSize = curText.getFontSize();
            curContent = curText.getContent();
            isBold = curText.getX2();
            isItalics = curText.getY2();
            model.getFontBar().setStyle(curStyle);
            model.getFontBar().setFontSize(curSize);
            model.getFontBar().setBold(isBold);
            model.getFontBar().setItalics(isItalics);
        }
        model.getColorBar().setColor(curColor);
    }

    //move the current shape
    public void moveShape(){
        curX1 = curShape.getX1() + (moveX2 - moveX1);
        curX2 = curShape.getX2() + (moveX2 - moveX1);
        curY1 = curShape.getY1() + (moveY2 - moveY1);
        curY2 = curShape.getY2() + (moveY2 - moveY1);
        createShape();
        modifyShape();
        getCurShape();
        moveX1 = moveX2;
        moveY1 = moveY2;
    }

    //resize the current shape
    public void resizeShape(){
        int m = 5, n = 5;
        if(moveX1 >= moveX2)    m *= -1;
        if(moveY1 >= moveY2)    n *= -1;
        if(curType.equals("Text")){
            curSize += m;
            if(curSize<5)  curSize = 5;
            if(curSize>200) curSize = 200;
            model.getFontBar().setFontSize(curSize);
            curX1 = curShape.getX1();
            curX2 = curShape.getX2();
        }
        else{
            if(k1>=-1 && k1<=1){
                curX1 = curShape.getX1()-m;
                curY1 = centerY+(int)(((curShape.getX1()-centerX)-m)*k1);
                curX2 = curShape.getX2()+m;
                curY2 = centerY+(int)(((curShape.getX2()-centerX)+m)*k1);
            }
            else if(k2>=-1 && k2<=1){
                curX1 = centerX + (int)(((curShape.getY1()-centerY)-n)*k2);
                curY1 = curShape.getY1()-n;
                curX2 = centerX+(int)(((curShape.getY2()-centerY)+n)*k2);
                curY2 = curShape.getY2()+n;
            }
            else{
                curX1 = curShape.getX1()-(moveX2-moveY1);
                curY1 = curShape.getY1()+(moveY2-moveY1);
                curX2 = curShape.getX2()+(moveX2-moveY1);
                curY2 = curShape.getY2()-(moveY2-moveY1);
            }
        }
        createShape();
        modifyShape();
        moveX1 = moveX2;
        moveY1 = moveY2;
    }
}

