package miniCAD;

public class State {
    public enum Task{
        EMPTY,    //do nothing
        DRAW,     //draw a shape
        DELETE,   //delete a shape
        SELECT    //select a shape
    };
    private Task task;

    public State(){
        task = Task.EMPTY;
    }

    //get the state
    public Task getState(){
        return task;
    }

    //set the state
    public void setState(Task t){
        task = t;
    }
}
