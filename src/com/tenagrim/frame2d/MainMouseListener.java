package com.tenagrim.frame2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMouseListener implements MouseListener {
    private  MainWindow win;

    public MainMouseListener(MainWindow win)
    {
        this.win = win;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        win.mouseEventHandler(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
