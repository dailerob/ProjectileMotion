package projectilemotion;

/**
 * Created by Roberto on 12/18/2014.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class TrailParticle implements DrawPoint, Comparable<DrawPoint>{


    private double x;
    private double y;
    private double z;
    private int width;
    private int height;
    private Color c;
    private int age;

    public TrailParticle (double x, double y, double z, Color c, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
        this.width = width;
        this.height = height;
        age = 0;
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


        age++;
        double[] temp = {x,y,z};
        temp = ViewTransformations.zRotation(temp, zradian);
        temp = ViewTransformations.xRotation(temp, xradian);
        temp = ViewTransformations.yRotation(temp, yradian);

        temp[2] = temp[2]*-1+width/2;
        double viewSize;
        viewSize = ViewTransformations.perspectiveAdjustment(zoom,temp[2],width);
        temp[0]*= viewSize;
        temp[1]*= viewSize;
        temp[0]+=width/2;
        temp[1]+=height/2;
        window.setColor(new Color(0,250-age,250-age));
        window.fillRect((int) temp[0], (int) temp[1],(int) 1,(int) 1);
    }

    public double getZdepth()
    {
        return z*-1+width/2;
    }
}
