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
public class DrawRequest {
    private double size;
    private double color;
    static Color [] colors = new Color [255];
    
    
    
    public static void initialize()
    {
        for(double index = 0; index <255; index ++)
        {
            colors[(int)index] = new Color ((int)((index/255)*(index/255)*255),(int)(index),(int)(index));
        }
    }
    
    
    
}
