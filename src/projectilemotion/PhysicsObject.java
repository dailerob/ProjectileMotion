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
    ArrayList<double[]> trail = new ArrayList<double[]>(); 
    Color c;

    public PhysicsObject(double xx, double yy, double xc, double yc, double g) {
        x = xx;
        y = yy;
        xcom = xc;
        ycom = yc;
        gravity = g;
        
        for(int index = 0; index < 100; index++)
        {
            trail.add(new double[3]);
        }
    }

    public PhysicsObject(double xx, double yy, double zz, double xc, double yc, double zc, double g, int trailLength) {
        x = xx;
        y = yy;
        z = zz;
        xcom = xc;
        ycom = yc;
        zcom = zc;
        
        for(int index = 0; index < trailLength; index++)
        {
            trail.add(new double[3]);
        }
    }

    public void draw(Graphics window) {
        double blueness = 500 * .1 * Math.sqrt(xcom * xcom + ycom * ycom + zcom *zcom);
        if(blueness > 255)
            blueness = 255; 
        
        c = new Color(0, 0, (int) blueness);
        
        for(double [] index :trail)
        {
            window.setColor(c);
            window.fillRect((int) (index[0]), (int) (index[1]), 1, 1);
        }
    }

    public void calcVec(double xx, double yy, double zz, double g) {
        double dx = xx - x;
        double dy = yy - y;
        double dz = zz - z;
        double mag = Math.sqrt((dx * dx + dy * dy + dz * dz));
        double mult = mag;
        mult = Math.abs(mult);
        mag += 1;
        mag *= mag;

        xcom += dx / mag;
        ycom += dy / mag;
        zcom += dz / mag;
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
            //if((int)trail.get(index)[0] != (int)trail.get(index-1)[0]);
                trail.get(index)[0] = trail.get(index-1)[0];
            //if((int)trail.get(index)[1] != (int)trail.get(index-1)[1]);
                trail.get(index)[1] = trail.get(index-1)[1];
           // if((int)trail.get(index)[2] != (int)trail.get(index-1)[2]);
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

    public double getGravity() {
        return gravity;
    }
}//physicsObject
