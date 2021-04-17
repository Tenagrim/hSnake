package com.tenagrim.frame2d;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;

public class Frame2d {

    public void run() {
        JFrame jf = new JFrame("hSnahe");
        MainWindow win = new MainWindow();
        win.addMouseListener(new MainMouseListener(win));
        win.addKeyListener(new MainWindowKeyListener(win));
        System.out.println("FRAME H: " + jf.getHeight());
        //jf.setSize(win.getWidth(),  win.getHeight());
        jf.add(win);
        jf.pack();
        jf.repaint();
        //jf.setLocationByPlatform(true);;
        jf.setVisible(true);
        jf.setResizable(false);
        win.setFocusable(true);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}




