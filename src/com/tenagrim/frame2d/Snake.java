package com.tenagrim.frame2d;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Snake {
    private         SnakeHeading heading;
    private         List<Coords> body;
    private int     length;
    private Coords  tail;
    private Map     map;
    private boolean alive;
    private Color   baseColor;


    private         Snake(){}


    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Snake(Coords pos, SnakeHeading h, Map map)
    {
        this.map = map;
        alive = true;
        body = new ArrayList<Coords>();
        body.add(pos);
        body.add(headsOn(pos, getOpposite(h)));
        heading = h;
        tail = headsOn(body.get(body.size() - 1), getOpposite(h));
        length = 2;
        baseColor = new Color( 90,40,50);
    }


    public boolean isAlive() {
        return alive;
    }

    public void step()
    {

        if (!alive)
            return;
        tryDie();
        if (!alive)
            return;
        tail = body.get(body.size()-1);
        Coords tmp = headsOn(body.get(0), heading);
        if (tmp .equals(map.getFood()))
        {
            grow();
            map.newFood();
        }
        for (Coords c : body)
            Coords.swap(tmp, c);
    }

    private void tryDie()
    {
        int headH, stepH;
        Coords head = body.get(0);
        Coords step = headsOn(head, heading);
        if (body.contains(step))
            die();

        headH = map.getHeight(head);
        stepH = map.getHeight(step);
        if (stepH - headH > 1)
            die();
    }

    private void die()
    {
        alive = false;
        System.out.println("GAME OVER");
    }

    public int getLength() {
        return length;
    }

    public Coords getBody(int index)
    {
        return body.get(index);
    }

    public void grow()
    {
        body.add(new Coords(tail.getX(), tail.getY()));
        length++;
    }

    public void turn(SnakeHeading h)
    {
        if (h != getOpposite(heading))
            heading = h;
    }

    public static Coords hToCoords(SnakeHeading h)
    {
        int x = 0;
        int y = 0;
        x += (h == SnakeHeading.EAST) ? 1 : 0;
        x -= (h == SnakeHeading.WEST) ? 1 : 0;
        y += (h == SnakeHeading.SOUTH) ? 1 : 0;
        y -= (h == SnakeHeading.NORTH) ? 1 : 0;
        return  new Coords(x, y);
    }

    public Coords headsOn(Coords c, SnakeHeading heading)
    {
        Coords tmp = c.add(hToCoords(heading));
        if (tmp.getX() < 0)
            tmp.setX(map.getWidth() - 1);
        if (tmp.getY() < 0)
            tmp.setY(map.getHeight() - 1);
        if (tmp.getX() >= map.getWidth())
            tmp.setX(0);
        if (tmp.getY() >= map.getHeight())
            tmp.setY(0);
        return tmp;
    }

    public static SnakeHeading getOpposite(SnakeHeading h)
    {
        if (h == SnakeHeading.NORTH)
            return SnakeHeading.SOUTH;
        if (h == SnakeHeading.SOUTH)
            return SnakeHeading.NORTH;
        if (h == SnakeHeading.WEST)
            return SnakeHeading.EAST;
        if (h == SnakeHeading.EAST)
            return SnakeHeading.WEST;
        return null;
    }

}
