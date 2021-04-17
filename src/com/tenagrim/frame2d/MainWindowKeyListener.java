package com.tenagrim.frame2d;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class MainWindowKeyListener implements KeyListener {
    private MainWindow window;

    public MainWindowKeyListener(MainWindow win)
    {
        this.window = win;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        window.keyEventHandler(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
