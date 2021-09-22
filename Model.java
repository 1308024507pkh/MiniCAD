package miniCAD;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import miniCAD.shapes.Shape;
import miniCAD.components.*;

public class Model extends JFrame{
    private static final long serialVersionUID = 1L;
    //all components in the miniCAD
    private MyCanvas canvas;
    private MyColorBar colorBar;
    private MyFontBar fontBar;
    private MyToolBar toolBar;
    private MyMenuBar menuBar;

    private View view;
    private boolean isSave = false;     //to judge whether the shapes on the canvas are saved into a file.
    private boolean saveFlag = true;    //to judge whether the shapes on the canvas are modified.
    private ArrayList<Shape> shapeList = new ArrayList<Shape>(); //to save all shapes on the canvas.

    public Model(View view){
        this.view = view;
        setModel();
    }

    //Set up the miniCAD
    public void setModel(){
        this.setTitle("MiniCAD");
        this.setSize(1100, 900);

        menuBar = new MyMenuBar();
        this.setJMenuBar(menuBar);

        fontBar = new MyFontBar();
        this.add(fontBar, BorderLayout.NORTH);

        toolBar = new MyToolBar();
        this.add(toolBar, BorderLayout.WEST);

        canvas = new MyCanvas(shapeList, view);
        this.add(canvas, BorderLayout.CENTER);

        colorBar = new MyColorBar();
        colorBar.setPreferredSize(new Dimension(super.getWidth(),85));
        this.add(colorBar, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    //modify the shape on the canvas
    public void setShape(int index, Shape curShape){
        if(index < shapeList.size())
            shapeList.set(index, curShape);
        canvas.setShapes(shapeList);
        setSaveFlag(false);
        repaint();
    }

    //add the shape on the canvas
    public void addShape(Shape curShape){
        shapeList.add(curShape);
        canvas.setShapes(shapeList);
        setSaveFlag(false);
        repaint();
    }

    //delete the shape on the canvas
    public void deleteShape(int index){
        shapeList.remove(index);
        canvas.setShapes(shapeList);
        setSaveFlag(false);
        repaint();
    }

    //delete all shapes on the canvas
    public void clearShape(){
        shapeList.clear();
        canvas.setShapes(shapeList);
        setSaveFlag(false);
        repaint();
    }

    //get the shape on the canvas
    public Shape getShape(int index){
        return shapeList.get(index);
    }

    //get the number of shapes on the canvas
    public int getNum(){
        return shapeList.size();
    }

    //get all shapes on the canvas
    public ArrayList<Shape> getShapes(){
        ArrayList<Shape> _shapes = new ArrayList<Shape>();
        for(Shape s:shapeList){
            _shapes.add(s);
        }
        return _shapes;
    }

    //set the shapes on the canvas
    public void setShapes(ArrayList<Shape> shapeList){
        this.shapeList.clear();
        for(Shape s:shapeList){
            this.shapeList.add(s);
        }
        canvas.setShapes(this.shapeList);
        setSaveFlag(false);
        repaint();
    }

    public void setIfSave(boolean b){
        isSave = b;
    }

    public void setSaveFlag(boolean b){
        saveFlag = b;
    }

    //judge whether a shape on the canvas contains (x, y)
    public int getIndex(int x, int y){
        int i;
        for(i=0; i<shapeList.size(); i++){
            Shape shape = shapeList.get(i);
            if(shape.isContains(x, y))
                return i;
        }
        return -1;
    }

    public String getCommand(){
        return toolBar.getButtonName();
    }

    public Color getColor(){
        return colorBar.getColor();
    }

    public MyCanvas getCanvas(){
        return canvas;
    }

    public MyFontBar getFontBar(){
        return fontBar;
    }

    public MyColorBar getColorBar(){
        return colorBar;
    }

    public MyMenuBar getMenu(){
        return menuBar;
    }

    public boolean getIfSave(){
        return isSave;
    }

    public boolean getSaveFlag(){
        return saveFlag;
    }

}

