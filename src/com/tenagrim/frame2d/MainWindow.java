package com.tenagrim.frame2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;


class MainWindow extends JPanel implements ActionListener
{
    private static final String MAP_QUCKSAVE_FILE = "mapQuicksave.ser";
    private static final int    MAP_WIDTH = 25;
    private static final int    MAP_HEIGHT = 25;
    private static final int    SQ_SIZE = 20;
    private static final int    SNAKE_SQ_SIZE = 16;
    private static final int    FOOD_SQ_SIZE = 10;
    private static int          stepTime = 170;
    private boolean             allowTurn = false;
    private int                 ticksToGrow = 10;
    private int                 ticks = 0;
    private boolean             run = true;

    //private Rectangle   rect = new Rectangle(10, 10, 10 ,10);
    private Map         map = new Map(MAP_WIDTH, MAP_HEIGHT);
    private Timer       mainTimer = new Timer(stepTime, this);
    private Snake       snake = new Snake(new Coords(MAP_HEIGHT / 2, MAP_WIDTH / 2), SnakeHeading.NORTH, map);
    private Mouse       mouse = new Mouse();

    //private Color       foodColor = new Color(120,80, 30);
    private Color       foodColor = new Color(100,80, 30);


    public MainWindow(){
        setPreferredSize( new Dimension(MAP_WIDTH * SQ_SIZE, MAP_HEIGHT * SQ_SIZE));
        map.setBaseColor(new Color(0,70,0));
//        map.growBlock(0,0, 3,3, 3);
//        map.growBlock(1,1, 3,3, 2);
//        map.growBlock(2,2, 3,3, 1);
        mainTimer.start();
    }

    public void mousePressedHandler(MouseEvent e)
    {
       // if (e.getX() < map.getWidth() * SQ_SIZE && e.getY() < map.getHeight() * SQ_SIZE)
       //     map.growOne(e.getX() / SQ_SIZE, e.getY() / SQ_SIZE);
        System.out.println("PRESS ON " + (e.getX() / SQ_SIZE) + " " + (e.getY() / SQ_SIZE));
        mouse.setPos(new Coords(e.getX() / SQ_SIZE, e.getY() / SQ_SIZE));
        if (e.getButton() == MouseEvent.BUTTON1)
            mouse.setState(MouseState.ADD);
        if (e.getButton() == MouseEvent.BUTTON3)
            mouse.setState(MouseState.SUB);
        repaint();

    }

    public void mouseReleasedHandler(MouseEvent e)
    {
        System.out.println("RELEASE ON " + (e.getX() / SQ_SIZE) + " " + (e.getY() / SQ_SIZE));

        mouse.setPos(new Coords(e.getX() / SQ_SIZE, e.getY() / SQ_SIZE));
        if (mouse.getState() == MouseState.ADD)
            map.growBlock(mouse.getPos(), mouse.getPrevPos(), 1);
        if (mouse.getState() == MouseState.SUB)
            map.growBlock(mouse.getPos(), mouse.getPrevPos(), -1);
        mouse.setState(MouseState.IDLE);
        repaint();
    }


    private void quickSaveMap()
    {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(MAP_QUCKSAVE_FILE));
            out.writeObject(map);
            System.out.println("QUICKSAVED\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quickLoadMap()
    {
        try {
            ObjectInputStream oin = new ObjectInputStream(
                    new FileInputStream(MAP_QUCKSAVE_FILE)
            );
            map = (Map) oin.readObject();
            resetSnake();
            repaint();
            System.out.println("QUICKLOADED\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // F5 116
    // F9 120

    public void keyEventHandler(KeyEvent e){

        System.out.println("GOT KEY CODE: " +  e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_F5)
            quickSaveMap();
        if (e.getKeyCode() == KeyEvent.VK_F9)
            quickLoadMap();
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
//        ticks++;
//        if (ticks == ticksToGrow)
//        {
//            snake.grow();
//            ticks = 0;
//        }
        if (!snake.isAlive())
        {
            resetSnake();
            pause();
            /*
            try {
                Thread.sleep(5000, 0);
                resetSnake();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        }
        //System.out.println("tick");
    }

    @Override
    public void paint(Graphics g) {
        paintMap(g);
        paintSnake(g);
        g.setColor(Color.BLUE);
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
        Coords f = map.getFood();
        g.setColor(map.shiftedBaseColor(f.getX(), f.getY(), foodColor));
        g.fillRect(f.getX() * SQ_SIZE + (SQ_SIZE - FOOD_SQ_SIZE) / 2,
                f.getY() * SQ_SIZE + (SQ_SIZE - FOOD_SQ_SIZE) / 2,
                FOOD_SQ_SIZE, FOOD_SQ_SIZE);
    }

    private void paintSnake(Graphics g)
    {
        Coords b;
        //g.setColor(snake.getBaseColor());
        for(int i = 0; i < snake.getLength(); i++)
        {
            b = snake.getBody(i);
            g.setColor(map.shiftedBaseColor(b.getX(), b.getY(), snake.getBaseColor()));
            g.fillRect(b.getX() * SQ_SIZE + (SQ_SIZE - SNAKE_SQ_SIZE) / 2,
                    b.getY() * SQ_SIZE + (SQ_SIZE - SNAKE_SQ_SIZE) / 2,
                    SNAKE_SQ_SIZE, SNAKE_SQ_SIZE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainTimer)
            tick();
            repaint();
    }
}
