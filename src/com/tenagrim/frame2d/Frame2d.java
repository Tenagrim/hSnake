package com.tenagrim.frame2d;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;

public class Frame2d {

    public void run() {
        JFrame jf = new JFrame("Frame2d");
        MainWindow win = new MainWindow();
        win.addMouseListener(new MainMouseListener(win));
        win.addKeyListener(new MainWindowKeyListener(win));
        jf.setSize(win.getSize());
        jf.add(win);
        jf.repaint();
        jf.setVisible(true);
        win.setFocusable(true);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}




