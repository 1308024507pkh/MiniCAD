package miniCAD.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyToolBar extends JToolBar{
    private static final long serialVersionUID = 1L;
    private String curButtonName = "Line";
    public MyToolBar(){
        //the image of the buttons
        String[] imgPath = {"src/miniCAD/image/line.png", "src/miniCAD/image/rectangle.png", "src/miniCAD/image/oval.png",
                "src/miniCAD/image/circle.png", "src/miniCAD/image/text.png", "src/miniCAD/image/delete.png",
                "src/miniCAD/image/select.png"};
        //the name of the buttons
        String[] name = {"Line", "Rectangle", "Oval", "Circle", "Text", "Delete", "Select"};
        String[] comment = {"直线", "矩形", "椭圆", "圆", "文本", "删除", "选中"};
        JButton[] buttons = new JButton[7];
        this.setOrientation(JToolBar.VERTICAL);
        //set all seven buttons
        for(int i=0; i<7; i++){
            Image image = (new ImageIcon(imgPath[i])).getImage().getScaledInstance(72, 72, 4);
            ImageIcon img = new ImageIcon(image);
            buttons[i] = new JButton();
            buttons[i].setBackground(null);
            buttons[i].setBorderPainted(false);
            buttons[i].setSize(new Dimension(72, 72));
            buttons[i].setToolTipText(comment[i]);
            buttons[i].setIcon(img);
            buttons[i].setActionCommand(name[i]);
            buttons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    curButtonName =e.getActionCommand();
                }
            });
            this.add(buttons[i]);
        }
        setBackground(null);
    }

    //get the name of the current button
    public String getButtonName(){
        return curButtonName;
    }
}
