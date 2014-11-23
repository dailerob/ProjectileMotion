/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectilemotion;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class PhysicsObject {

    private double x;
    private double y;
    private double z;
    private double xcom;
    private double ycom;
    private double zcom;
    private double gravity;
    private double xradian;
    private double yradian; 
    private double zradian;
    private double width; 
    private double height;
    ArrayList<double[]> trail = new ArrayList<double[]>(); 
    Color c;
    Color [] colors = new Color [255];
   
    

    public PhysicsObject(double xx, double yy, double xc, double yc, double g, int width, int height) {
        x = xx;
        y = yy;
        xcom = xc;
        ycom = yc;
        gravity = g;
        this.width  = width;
        this.height = height; 
        for(int index = 0; index < 100; index++)
        {
            trail.add(new double[3]);
        }
        
        for(double index = 0; index <255; index ++)
        {
            colors[(int)index] = new Color ((int)((index/255)*(index/255)*255),(int)(index),(int)(index));
        }
    }

    public PhysicsObject(double xx, double yy, double zz, double xc, double yc, double zc, double mass, int trailLength, int width, int hight) {
        x = xx;
        y = yy;
        z = zz;
        xcom = xc;
        ycom = yc;
        zcom = zc;
        gravity = mass;
        this.width  = width;
        this.height = hight; 
        for(int index = 0; index < trailLength; index++)
        {
            trail.add(new double[3]);
        }
        
        for(double index = 0; index <255; index ++)
        {
            //colors[(int)index] = new Color ((int)(index),255,255,(int)(index/2));
            colors[(int)index] = new Color ((int)((index/255)*(index/255)*255),(int)(index),(int)(index));
        }
    }

    public void draw(Graphics window, double xradian, double yradian, double zradian) {
        this.xradian = xradian;
        this.yradian = yradian;
        this.zradian = zradian;
        
        //c = new Color((int)brightness,(int) brightness, 0);
        
        //c = new Color(0,255, 255);
        double ind = trail.size()+25;
        
        for (int index = trail.size()-1; index >=0 ; index--) {
            double xc = 255 - Math.abs(trail.get(index)[0] / 3);
            double yc = 255 - Math.abs(trail.get(index)[1] / 3);
            double zc = 255 - Math.abs(trail.get(index)[2] / 3);

            if (xc < 0) {
                xc = 0;
            }
            if (yc < 0) {
                yc = 0;
            }
            if (zc < 0) {
                zc = 0;
            }

            xc /= 255;
            yc /= 255;
            zc /= 255;

            zc =  xc * xc  * yc * yc ;
            zc *= 254;
            zc = Math.abs(zc);
            //window.setColor(colors[(int)(254-ind/2)]);
            window.setColor(colors[(int)(254/(ind/25))]);

            double[] temp = yRotation(xRotation(zRotation(trail.get(index))));
            
            temp[2] = temp[2]*-1+600;
            double d0 = 1200;
            double viewSize;
            viewSize = d0/(d0+2 *1/Math.atan(1.047)*temp[2]);
            
            temp[0]*= viewSize;
            temp[1]*= viewSize;
            temp[0]+=600;
            temp[1]+=500;
            ind--;
            //ind*=.995;
            //if(temp[2]>0)
            window.fillRect((int) (temp[0]), (int) (temp[1]), (int)(1), (int)(1));
        }
    }
    
    
    private double [] xRotation (double [] preRotation)
    {
        double [] rotated = {Math.cos(xradian)*preRotation[0]+Math.sin(xradian)
                *preRotation[2],preRotation[1],Math.cos(xradian)*preRotation[2]
                -Math.sin(xradian)*preRotation[0]};
        return rotated;
    }
    
    private double [] yRotation (double [] preRotation)
    {
        double [] rotated = {preRotation[0],Math.cos(yradian)*preRotation[1]
                +Math.sin(yradian)*preRotation[2],Math.sin(yradian)
                *preRotation[1]-Math.cos(yradian)*preRotation[2]};
        return rotated;
    }
    
    private double [] zRotation (double [] preRotation)
    {
        double [] rotated = {Math.cos(zradian)*preRotation[0]+Math.sin(zradian)*preRotation[1],Math.cos(zradian)*preRotation[1]-Math.sin(zradian)*preRotation[0],preRotation[2]};
        return rotated;
    }
    

    public boolean calcVec(double xx, double yy, double zz, double mass, double collisionDistance) {
        double dx = xx - x;
        double dy = yy - y;
        double dz = zz - z;
        double mag = Math.sqrt((dx * dx + dy * dy + dz * dz));
        if(mag<=collisionDistance)
            return true;
        
        mag += 1;
        mag *= mag /15/mass;

        xcom += dx / mag;
        ycom += dy / mag;
        zcom += dz / mag;
        
        return false;
        
    }

    public void step(double resolution) {
        x += xcom / resolution;
        y += ycom / resolution;
        z += zcom / resolution; 
        
        stepTrail();
    }
    
    private void stepTrail()
    {
        trail.get(0)[0] = x;
        trail.get(0)[1] = y;
        trail.get(0)[2] = z;
        
        for(int index = trail.size()-1; index > 0; index-- )
        {
            trail.get(index)[0] = trail.get(index-1)[0];
            trail.get(index)[1] = trail.get(index-1)[1];
            trail.get(index)[2] = trail.get(index-1)[2];
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXcom() {
        return xcom;
    }

    public void setXcom(double xcom) {
        this.xcom = xcom;
    }

    public double getYcom() {
        return ycom;
    }

    public void setYcom(double ycom) {
        this.ycom = ycom;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZcom() {
        return zcom;
    }

    public void setZcom(double zcom) {
        this.zcom = zcom;
    }

    public double getYradian() {
        return yradian;
    }
    
    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    
    public void comineObjects(PhysicsObject collideWith)
    {
        double grav2 = collideWith.getGravity();
        double xcom2 = collideWith.getXcom();
        double ycom2 = collideWith.getXcom();
        
        xcom = (xcom*gravity+xcom2*grav2)/(gravity+grav2);
        ycom = ((ycom*gravity+grav2*grav2)/2)/(gravity+grav2);
        gravity+=collideWith.getGravity();
    }
}//physicsObject
