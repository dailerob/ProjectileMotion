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
    private double xCom;
    private double yCom;
    private double zCom;
    private double mass;
    private static double xradian;
    private static double yradian;
    private static double zradian;
    private static int width;
    private static int height;
    int currentIndex = 0;
    ArrayList<double[]> trail = new ArrayList<double[]>();
    Color c;
    Color [] colors = new Color [255];
   
    

    public PhysicsObject(double x, double y, double xCom, double yCom, double g, int width, int height) {
        this.x = x;
        this.y = y;
        this.xCom = xCom;
        this.yCom = yCom;
        mass = g;
        this.width  = width;
        this.height = height; 
        for(int index = 0; index < 100; index++)
        {
            trail.add(new double[3]);
        }
    }

    public PhysicsObject(double x, double y, double z, double xCom, double yCom, double zCom, double mass, int trailLength, int width, int hight) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xCom = xCom;
        this.yCom = yCom;
        this.zCom = zCom;
        this.mass = mass;
        this.width  = width;
        this.height = hight; 
        for(int index = 0; index < trailLength; index++)
        {
            trail.add(new double[3]);
        }
    }

    public void draw(Graphics window, double xradian, double yradian, double zradian, double zoom) {
        this.xradian = xradian;
        this.yradian = yradian;
        this.zradian = zradian;

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

            double[] temp = yRotation(xRotation(zRotation(trail.get(index))));
            //double [] temp  = trail.get(index).clone();
            
            temp[2] = temp[2]*-1+width/2;
            double d0 = width;
            double viewSize;
            viewSize = d0/(d0+2/Math.atan(1.047+zoom)*temp[2]);
 
            viewSize = 1;
            temp[0]*= viewSize;
            temp[1]*= viewSize;
            temp[0]+=width/2;
            temp[1]+=height/2;
                ind--;


            PhysicsProjectile.drawMap.add(new DrawRequest(temp[0],temp[1],
                    temp[2],viewSize*10,(int)(254/(ind/25)) ));
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
        double [] rotated = {Math.cos(zradian)*preRotation[0]+Math.sin(zradian)
                *preRotation[1],Math.cos(zradian)*preRotation[1]-
                Math.sin(zradian)*preRotation[0],preRotation[2]};
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

        xCom += dx / mag;
        yCom += dy / mag;
        zCom += dz / mag;
        
        return false;
        
    }

    public void step(double resolution) {
        x += xCom / resolution;
        y += yCom / resolution;
        z += zCom / resolution;
        
        stepTrail();
    }
    
    private void stepTrail()
    {
        //todo


        if(currentIndex<trail.size()-1)
            currentIndex++;
        else
            currentIndex = 0;
        trail.get(currentIndex)[0] = x;
        trail.get(currentIndex)[1] = y;
        trail.get(currentIndex)[2] = z;
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

    public double getxCom() {
        return xCom;
    }

    public void setxCom(double xCom) {
        this.xCom = xCom;
    }

    public double getyCom() {
        return yCom;
    }

    public void setyCom(double yCom) {
        this.yCom = yCom;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getzCom() {
        return zCom;
    }

    public void setzCom(double zCom) {
        this.zCom = zCom;
    }

    public double getYradian() {
        return yradian;
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public void combineObjects(PhysicsObject collideWith)
    {
        double grav2 = collideWith.getMass();
        double xcom2 = collideWith.getxCom();
        double ycom2 = collideWith.getxCom();
        
        xCom = (xCom * mass +xcom2*grav2)/(mass +grav2);
        yCom = ((yCom * mass +grav2*grav2)/2)/(mass +grav2);
        mass +=collideWith.getMass();
    }
}//physicsObject
