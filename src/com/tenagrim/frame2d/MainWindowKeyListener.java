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
/*
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            window.moveSquare(-1, 0);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            window.moveSquare(1, 0);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            window.moveSquare(0, -1);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            window.moveSquare(0, 1);
*/
//        window.repaint();
//        System.out.println("Key Hit " + e.getKeyCode());
        window.keyEventHandler(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
