package miniCAD.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MyFontBar extends JPanel{
    private static final long serialVersionUID = 1L;
    private JComboBox<String> typeface;
    private JComboBox<String> fontSize;
    private JCheckBox ifItalics;
    private JCheckBox ifBold;
    //some font style options
    String[] styles = {"宋体", "楷体", "微软雅黑", "Calibri", "Times New Roman", "Arial", "Verdana", "Garamond"};
    //some font size options
    String[] sizes = new String[196];

    public MyFontBar(){
        for(int i=5; i<=200; i++)
            sizes[i-5] = Integer.toString(i);
        this.setPreferredSize(new Dimension(600,40));
        this.setLayout(null);
        //the block to choose the font style
        JTextField text1 = new JTextField("字体：");
        text1.setEditable(false);
        text1.setBorder(new LineBorder(new Color(0,0,0,0)));
        text1.setBounds(35, 0, 55, 40);
        text1.setBackground(null);
        text1.setFont(new Font("微软雅黑",1,15));
        this.add(text1);

        typeface = new JComboBox<String>(styles);
        typeface.setBounds(90, 7, 175, 27);
        typeface.setBackground(null);
        typeface.setSelectedIndex(0);
        typeface.setMaximumRowCount(8);
        typeface.setFont(new Font("微软雅黑",1,15));
        this.add(typeface);
        //the block to choose the font size
        JTextField text2 = new JTextField("字号：");
        text2.setEditable(false);
        text2.setBorder(new LineBorder(new Color(0,0,0,0)));
        text2.setBounds(305, 0, 55, 40);
        text2.setBackground(null);
        text2.setFont(new Font("微软雅黑",1,15));
        this.add(text2);

        fontSize=new JComboBox<String>(sizes);
        fontSize.setBounds(360, 7, 75, 26);
        fontSize.setBackground(null);
        fontSize.setSelectedIndex(60);
        fontSize.setMaximumRowCount(8);
        fontSize.setFont(new Font("微软雅黑",1,15));
        this.add(fontSize);
        //the block to determine whether the font is italics
        ifItalics = new JCheckBox("斜体");
        ifItalics.setBounds(470, 0, 75, 40);
        ifItalics.setBackground(null);
        ifItalics.setFont(new Font("微软雅黑", 1, 15));
        this.add(ifItalics);
        //the block to determine the font is bold
        ifBold = new JCheckBox("粗体");
        ifBold.setBounds(560, 0, 75, 40);
        ifBold.setBackground(null);
        ifBold.setFont(new Font("微软雅黑", 1, 15));
        this.add(ifBold);
    }

    //set the current font style
    public void setStyle(String str){
        int i = 0;
        for(; i<8; i++){
            if(styles[i].equals(str)){
                typeface.setSelectedIndex(i);
                break;
            }
        }
    }

    //get the current font style
    public String getStyle(){
        return styles[typeface.getSelectedIndex()];
    }

    //set the current font size
    public void setFontSize(int size){
        int i = 0;
        for(; i<sizes.length; i++)
            if(Integer.parseInt(sizes[i])==size)
                break;
        if(i < sizes.length)
            fontSize.setSelectedIndex(i);
    }

    //get the current font size
    public int getFontSize(){
        String size = sizes[fontSize.getSelectedIndex()];
        return Integer.parseInt(size);
    }

    //set whether the font is italics
    public void setItalics(int italics){
        if(italics == Font.ITALIC)
            ifItalics.setSelected(true);
        else
            ifItalics.setSelected(false);
    }

    //get whether the font is italics
    public int getItalics(){
        if(ifItalics.isSelected()){
            return Font.ITALIC;
        }
        else
            return Font.PLAIN;
    }

    //set whether the font is bold
    public void setBold(int bold){
        if(bold == Font.BOLD)
            ifBold.setSelected(true);
        else
            ifBold.setSelected(false);
    }

    //get whether the font is bold
    public int getBold(){
        if(ifBold.isSelected())
            return Font.BOLD;
        else
            return Font.PLAIN;
    }

    public JComboBox<String> getStyleBox(){
        return typeface;
    }

    public JComboBox<String> getSizeBox(){
        return fontSize;
    }

    public JCheckBox getItalicsBox(){
        return ifItalics;
    }

    public JCheckBox getBoldBox(){
        return ifBold;
    }
}