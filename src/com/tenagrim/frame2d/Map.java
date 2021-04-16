package com.tenagrim.frame2d;

import java.awt.*;

public class Map {
    private Color _baseColor;
    private int _width;
    private int _height;
    private int _maxHeight;
    private int _minHeight;
    private int [][] _content;
    private int _growOneStep;

    private Map(){}
    public Map(int w, int h)
    {
        _content = new int[w][h];
        _baseColor = Color.DARK_GRAY;
        _width = w;
        _height = h;
        _growOneStep = 1;
    }

    public int getHeight(int x, int y)
    {
        return _content[x][y];
    }

    public void setBaseColor(Color _baseColor) {
        this._baseColor = _baseColor;
    }

    public Color getColor(int x, int y)
    {
        return shiftedBaseColor(x, y, _baseColor);
    }

    private void growHill(int x, int y, int h)
    {
        checkBorders(x, y);


    }

    public void growOne(int x, int y)
    {
        checkBorders(x, y);

        _content[x][y] += _growOneStep;
        if (_content[x][y] > _maxHeight)
            _maxHeight = _content[x][y];
    }

    public void growBlock(int x, int y, int w, int h, int heihgt)
    {
        checkBorders(x, y);
        checkBorders(x + w - 1, y + h - 1);

        if (heihgt > _maxHeight)
            _maxHeight = heihgt;
        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                addHeight(x + i, y + j, heihgt);
            }

        }

    }

    public int      getHeight(Coords c)
    {
        return _content[c.getX()][c.getY()];
    }

    public Color    shiftedBaseColor( int x, int y, Color base)
    {
        final int TRESH = 240;

        int h = _content[x][y];
        int diff = _maxHeight - _minHeight;
        int r = base.getRed();
        int g = base.getGreen();
        int b = base.getBlue();
        if (diff == 0)
            return base;

        return new Color(
                r + ((TRESH - r) * h) / diff,
                g + ((TRESH - g) * h) / diff,
                b + ((TRESH - b) * h) / diff
        );
    }

    private void    addHeight(int x, int y, int h)
    {
        if (_content[x][y] < h)
            _content[x][y] = h;
    }

    private void checkBorders(int x, int y)
    {
        if (x < 0 || y < 0 || x >= _width || y >= _height)
            throw new IndexOutOfBoundsException("Out of the map");
    }

    public int maxHeight()
    {
        return _maxHeight;
    }

    public int getMinHeight() {
        return _minHeight;
    }

    public int getHeight() {
        return _height;
    }

    public int getWidth() {
        return _width;
    }
}
