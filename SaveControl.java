package miniCAD;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import miniCAD.shapes.Shape;

public class SaveControl {
    private Model model;
    private ShapeLists shapeLists;
    private File curFile;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SaveControl(Model model, ShapeLists shapeLists){
        this.model = model;
        this.shapeLists = shapeLists;
    }

    public void controlListen(){
        //Open a file
        model.getMenu().getOpen().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        //Save the shapes on the canvas
        model.getMenu().getSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        //Save the shapes on the canvas into a file
        model.getMenu().getSaveAs().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });
        //Exit the miniCAD system
        model.getMenu().getExit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitCAD();
            }
        });
        //Exit the miniCAD system
        model.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                exitCAD();
            }
        });
        //get some relative information
        model.getMenu().getAbout().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null,
                        "JAVA应用技术\n这是一个MiniCAD画图软件！",
                        "关于", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    //Save the shapes on the canvas into a file
    public void saveFileAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if(fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
            return;
        curFile = fileChooser.getSelectedFile();  //Choose a file
        curFile.canWrite();
        if(curFile==null || curFile.getName().equals("")){
            JOptionPane.showMessageDialog(fileChooser,"The File Name is Invalid",
                    "The File Name is Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            curFile.delete();
            oos = new ObjectOutputStream(new FileOutputStream(curFile));
            oos.writeInt(model.getNum());  //Write the number of shapes into the file
            for(int i = 0; i < model.getNum(); i++){  //write all shapes into the file
                Shape s = model.getShape(i);
                oos.writeObject(s);
                oos.flush();
            }
            oos.close();
            model.setIfSave(true);
            model.setSaveFlag(true);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"error during writing into the file",
                    "Write Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    //Save the shapes on the canvas
    public void saveFile(){
        if(model.getIfSave()){
            try{
                curFile.delete();
                oos = new ObjectOutputStream(new FileOutputStream(curFile));
                oos.writeInt(model.getNum());
                for(int i = 0; i < model.getNum(); i++){
                    Shape s = model.getShape(i);
                    oos.writeObject(s);
                    oos.flush();
                }
                oos.close();
                model.setIfSave(true);
                model.setSaveFlag(true);
            }catch(IOException e){
                JOptionPane.showMessageDialog(null,"error during reading from file",
                        "Read Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        //if all shapes on the canvas haven't been saved into a file
        else
            saveFileAs();
    }

    //Open the file
    public void openFile(){
        int opt = 0;
        if(model.getNum()!=0 && !model.getSaveFlag())
            opt = JOptionPane.showConfirmDialog(null,"当前图片尚未保存，是否确认打开其他图片？",
                    "提示",JOptionPane.YES_NO_OPTION);
        if(opt!=0)	return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if(fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
            return;
        curFile=fileChooser.getSelectedFile();  //Choose the file
        curFile.canRead();
        if(curFile==null || curFile.getName().equals("")){
            JOptionPane.showMessageDialog(fileChooser,"The File Name is Invalid",
                    "The File Name is Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            ois = new ObjectInputStream(new FileInputStream(curFile));
            model.clearShape();
            Shape s;
            int num = ois.readInt();  //Read the number of shapes
            for(int i=0; i<num; i++){  //Read all shapes
                s = (Shape)ois.readObject();
                model.addShape(s);
            }
            ois.close();
            model.setIfSave(true);
            model.setSaveFlag(true);
            shapeLists.clearList();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"error during reading from file",
                    "Read Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    //Exit the miniCAD system
    public void exitCAD(){
        int i = JOptionPane.YES_OPTION;
        if(model.getNum()!=0 && !model.getSaveFlag())
            i = JOptionPane.showConfirmDialog(null,"当前图片未保存，是否退出？","",JOptionPane.YES_NO_OPTION);
        if(i == JOptionPane.YES_OPTION)
            System.exit(0);
    }


}

