/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectilemotion;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/**
 *
 * @author Roberto
 */
public class DrawRequest implements Comparable<DrawRequest> {
    private double size;
    private double color;
    private double x;
    private double y;
    private double zdepth;
    private int currentColor;
    static Color [] colors = new Color [255];
    
    
    
    
    public DrawRequest(double x, double y, double zdepth, double size, int currentColor)
    {
        this.x = x;
        this.y = y;
        this.zdepth = zdepth;
        this.size= size;
        this.currentColor = currentColor;
        
    }
    
    public static void initialize()
    {
        for(double index = 0; index <255; index ++)
        {
            colors[(int)index] = new Color ((int)((index/255)*(index/255)*255),(int)(index),(int)(index));
        }
    }
    
    public int compareTo(DrawRequest test)
    {
        if(zdepth<test.getZdepth())
            return -1;
        else
            return 1;
    }
    
    public void draw (Graphics window)
    {
        window.setColor(colors[currentColor]);
        window.fillRect((int) (x), (int) (y), (int)(size), (int)(size));
        
    }
    
    
    

    public double getZdepth() {
        return zdepth;
    }

    public void setZdepth(double Zdepth) {
        this.zdepth = Zdepth;
    }
    
    
    
}
