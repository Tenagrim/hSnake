package com.tenagrim.frame2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

class MainWindow extends JPanel implements ActionListener
{
    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 30;
    private static final int SQ_SIZE = 20;
    private static final int SNAKE_SQ_SIZE = 16;
    private static int stepTime = 170;
    private boolean allowTurn = false;
    private int ticksToGrow = 10;
    private int ticks = 0;

    private Rectangle rect = new Rectangle(10, 10, 10 ,10);
    private Map map = new Map(MAP_WIDTH, MAP_HEIGHT);
    private Timer mainTimer = new Timer(stepTime, this);
    private Snake snake = new Snake(new Coords(MAP_HEIGHT / 2, MAP_WIDTH / 2), SnakeHeading.NORTH, map);
    private boolean run = true;


    public MainWindow(){
        setSize(MAP_WIDTH * SQ_SIZE, MAP_HEIGHT * SQ_SIZE);
        map.setBaseColor(new Color(0,70,0));
        map.growBlock(0,0, 3,3, 3);
        map.growBlock(1,1, 3,3, 2);
        map.growBlock(2,2, 3,3, 1);
        mainTimer.start();
    }

    public void mouseEventHandler(MouseEvent e)
    {
        if (e.getX() < map.getWidth() * SQ_SIZE && e.getY() < map.getHeight() * SQ_SIZE)
            map.growOne(e.getX() / SQ_SIZE, e.getY() / SQ_SIZE);
        repaint();
        System.out.println("CLICK ON " + (e.getX() / SQ_SIZE) + " " + (e.getY() / SQ_SIZE));
    }

    public void keyEventHandler(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            pause();
        if (!allowTurn)
            return;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            snake.turn(SnakeHeading.WEST);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            snake.turn(SnakeHeading.EAST);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            snake.turn(SnakeHeading.NORTH);;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            snake.turn(SnakeHeading.SOUTH);
        allowTurn = false;
    }

    private void pause()
    {
        if (run) {
            mainTimer.stop();
        }else
            mainTimer.start();
        run = !run;
    }

    private void resetSnake()
    {
        snake = new Snake(new Coords(MAP_HEIGHT / 2, MAP_WIDTH / 2), SnakeHeading.NORTH, map);
    }

    private void tick()
    {
        snake.step();
        allowTurn = true;
        ticks++;
        if (ticks == ticksToGrow)
        {
            snake.grow();
            ticks = 0;
        }
        if (!snake.isAlive())
        {
            try {
                Thread.sleep(5000, 0);
                resetSnake();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("tick");
    }

    @Override
    public void paint(Graphics g) {
        paintMap(g);
        paintSnake(g);
        g.setColor(Color.BLUE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    private void paintMap(Graphics g)
    {
        for(int i = 0; i < map.getWidth(); i++)
        {
            for (int j = 0; j < map.getHeight(); j++)
            {
                g.setColor(map.getColor(i, j));
                g.fillRect(i * SQ_SIZE, j * SQ_SIZE, SQ_SIZE, SQ_SIZE);
            }
        }
    }

    private void paintSnake(Graphics g)
    {
        Coords b;
        g.setColor(new Color( 210,80,60));
        for(int i = 0; i < snake.getLength(); i++)
        {
            b = snake.getBody(i);
            g.fillRect(b.getX() * SQ_SIZE + (SQ_SIZE - SNAKE_SQ_SIZE) / 2,
                    b.getY() * SQ_SIZE + (SQ_SIZE - SNAKE_SQ_SIZE) / 2,
                    SNAKE_SQ_SIZE, SNAKE_SQ_SIZE);
        }
    }

    public void moveSquare(int dx, int dy)
    {
        final int step = 5;
        rect.x += dx * step;
        rect.y += dy * step;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainTimer)
            tick();
            repaint();
    }
}
