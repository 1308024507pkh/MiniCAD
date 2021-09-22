package miniCAD;

import java.util.ArrayList;
import miniCAD.shapes.Shape;

public class ShapeLists {
    //save all lists of shapes
    ArrayList<ArrayList<Shape> > shapeList = new ArrayList<ArrayList<Shape> >();

    //get the former list of shapes
    public ArrayList<Shape> getShapeList(){
        ArrayList<Shape> shapes  = shapeList.get(0);
        shapeList.remove(0);
        return shapes;
    }

    //save a new list of shapes
    public void addShapeLists(ArrayList<Shape> shapes){
        while(shapeList.size()>=10)
            shapeList.remove(9);
        shapeList.add(0, shapes);
    }

    public int getSize(){
        return shapeList.size();
    }

    public void clearList(){
        shapeList.clear();
    }
}
