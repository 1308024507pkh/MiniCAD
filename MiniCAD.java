package miniCAD;

public class MiniCAD{
    public static void main(String[] args){
        State state = new State();
        ShapeLists shapeRecords = new ShapeLists();
        View view = new View();
        Model model = new Model(view);
        DrawControl drawControl = new DrawControl(model, state, shapeRecords);
        SaveControl saveControl = new SaveControl(model, shapeRecords);
        //listen to the events on the miniCAD
        saveControl.controlListen();
        drawControl.controlListen();
    }
}
