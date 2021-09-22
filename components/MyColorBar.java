package miniCAD.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MyColorBar extends JPanel{
    private static final long serialVersionUID = 1L;
    private JPanel curColorBlock;
    private Color curColor;
    //Some color options
    Color colors[] = {Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK,
            Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA,
            Color.CYAN, Color.BLUE, new Color(200, 200, 200)};

    public MyColorBar(){
        String imgPath = "src/miniCAD/image/moreColor.png";
        setLayout(null);
        setSize(new Dimension(385,85));
        //the block to show the current color
        curColorBlock = new JPanel();
        curColorBlock.setBounds(20, 14, 50, 50);
        curColorBlock.setBackground(Color.BLACK);
        curColorBlock.setToolTipText("当前颜色");
        curColor = Color.BLACK;
        this.add(curColorBlock);
        //the block to offer some color options
        JPanel chooseBlock = new JPanel();
        chooseBlock.setLayout(new GridLayout(2, 7, 2, 2));
        chooseBlock.setBounds(85, 14, 240, 62);
        for(int i=0; i<14; i++){
            JButton colorButton = new JButton();
            colorButton.setSize(new Dimension(30, 30));
            colorButton.setBackground(colors[i]);
            colorButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton btn = (JButton)e.getSource();
                    setColor(btn.getBackground());
                }
            });
            chooseBlock.add(colorButton);
        }
        this.add(chooseBlock);
        //the color chooser
        Image image = (new ImageIcon(imgPath)).getImage().getScaledInstance(65, 65, 4);
        ImageIcon img = new ImageIcon(image);
        JButton moreColorBlock = new JButton();
        moreColorBlock.setBorderPainted(false);
        moreColorBlock.setBounds(355, 14, 90, 70);
        moreColorBlock.setToolTipText("更多颜色");
        moreColorBlock.setContentAreaFilled(false);
        moreColorBlock.setIcon(img);
        moreColorBlock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Color now = JColorChooser.showDialog(null,"More Color",curColor);
                if(now!=null)
                    setColor(now);
            }
        });
        this.add(moreColorBlock);
    }

    //set the current color
    public void setColor(Color c){
        curColor = c;
        curColorBlock.setBackground(c);
    }

    //get the current color
    public Color getColor(){
        return curColor;
    }

    //get the current color panel
    public JPanel getColorPanel(){
        return curColorBlock;
    }
}

