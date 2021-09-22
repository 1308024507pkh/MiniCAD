package miniCAD.components;

import java.awt.*;
import javax.swing.*;

public class MyMenuBar extends JMenuBar{
    private static final long serialVersionUID = 1L;
    JMenu fileMenu, editMenu, helpMenu;
    JMenuItem openItem, saveItem, saveAsItem, exitItem;
    JMenuItem recoverItem, clearItem, aboutItem;
    public MyMenuBar(){
        fileMenu = new JMenu(" 文件 ");
        fileMenu.setFont(new Font("微软雅黑", 1, 16));
        //the menu item to open the file
        openItem = new JMenuItem(" 打开");
        openItem.setFont(new Font("微软雅黑", 1, 14));
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        //the menu item to save all shapes
        saveItem = new JMenuItem(" 保存");
        saveItem.setFont(new Font("微软雅黑", 1, 14));
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        //the menu item to save all shapes into a file
        saveAsItem = new JMenuItem(" 另存为");
        saveAsItem.setFont(new Font("微软雅黑", 1, 14));
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        //the menu item to exit the miniCAD system
        exitItem = new JMenuItem(" 退出");
        exitItem.setFont(new Font("微软雅黑", 1, 14));
        fileMenu.add(exitItem);
        this.add(fileMenu);

        editMenu = new JMenu(" 编辑 ");
        editMenu.setFont(new Font("微软雅黑", 1, 16));
        //the menu item to recover the shapes on the canvas
        recoverItem = new JMenuItem(" 恢复");
        recoverItem.setFont(new Font("微软雅黑", 1, 14));
        editMenu.add(recoverItem);
        editMenu.addSeparator();
        //the menu item to clear all shapes on the canvas
        clearItem = new JMenuItem(" 清空");
        clearItem.setFont(new Font("微软雅黑", 1, 14));
        editMenu.add(clearItem);
        this.add(editMenu);
        //the menu item to get the relative information
        helpMenu = new JMenu(" 帮助 ");
        helpMenu.setFont(new Font("微软雅黑", 1, 16));

        aboutItem = new JMenuItem(" 关于");
        aboutItem.setFont(new Font("微软雅黑", 1, 14));
        helpMenu.add(aboutItem);
        this.add(helpMenu);
    }

    public JMenuItem getOpen(){
        return openItem;
    }

    public JMenuItem getSave(){
        return saveItem;
    }

    public JMenuItem getSaveAs(){
        return saveAsItem;
    }

    public JMenuItem getRecover(){
        return recoverItem;
    }

    public JMenuItem getExit(){
        return exitItem;
    }

    public JMenuItem getClear(){
        return clearItem;
    }

    public JMenuItem getAbout(){
        return aboutItem;
    }
}

