package projectilemotion;

/**
 * Created by Roberto on 12/18/2014.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class TrailParticle implements DrawPoint{


    private double x;
    private double y;
    private double z;
    private int width;
    private int height;
    private Color c;

    public TrailParticle (double x, double y, double z, Color c, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
        this.width = width;
        this.height = height;
    }




    @Override
    public int compareTo(DrawPoint test) {
        if(getZdepth()<test.getZdepth())
            return 1;
        else
            return -1;
    }

    @Override
    public void draw(Graphics window, double xradian, double yradian, double zradian, double zoom) {

    }

    public double getZdepth()
    {
        return z*-1+width/2;
    }
}
