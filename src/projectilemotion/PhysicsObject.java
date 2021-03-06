/*
 * Roberto Dailey, 2015
 */
package projectilemotion;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class PhysicsObject implements DrawPoint, Comparable<DrawPoint> {

    private double x;
    private double y;
    private double z;
    private double xCom;
    private double yCom;
    private double zCom;
    private double mass;
    private static double trailLength;
    private static int width;
    private static int height;
    LinkedList<TrailParticle> trailParticles =  new LinkedList<TrailParticle>();
   


    public PhysicsObject(double x, double y, double z, double xCom, double yCom, double zCom, double mass, int trailLength, int width, int height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xCom = xCom;
        this.yCom = yCom;
        this.zCom = zCom;
        this.mass = mass;
        this.width  = width;
        this.height = height;
        this.trailLength = trailLength;
    }

    /**
     *
     * @param window - the window the particle is drawn on
     * @param xRadian - the amount the view is rotated along the xy plane
     * @param yRadian - the amount the view is rotated along the yz plane
     * @param zRadian - the amount the view is rotated along the xz plane
     * @param zoom - the amount the camera angle is incrased/decreased
     */
    public void draw(Graphics window, double xRadian, double yRadian, double zRadian, double zoom) {

            double[] temp = {x,y,z};
            temp = ViewTransformations.zRotation(temp, zRadian);
            temp = ViewTransformations.xRotation(temp, xRadian);
            temp = ViewTransformations.yRotation(temp, yRadian);
            
            temp[2] = temp[2]*-1+width/2;
            double viewSize;
            viewSize = ViewTransformations.perspectiveAdjustment(zoom,temp[2],width);
            temp[0]*= viewSize;
            temp[1]*= viewSize;
            temp[0]+=width/2;
            temp[1]+=height/2;
            window.fillRect((int) temp[0], (int) temp[1],(int) 1,(int) 1);
    }

    /**\
     *
     * @param xx - the x location of the particle exerting force on the current particle
     * @param yy - the y location of the particle exerting force on the current particle
     * @param zz - the z location of the particle exerting force on the current particle
     * @param mass - the mass of the particle exerting force
     * @param collisionDistance - the distance at which two particles might collide and combine
     * @return boolean describing weather the particles collided
     */
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

    /**
     *Steps the position of the particle, calls Steptrail to add to trail
     * @param resolution- the resolution of the vector approximations
     */
    public void step(double resolution) {
        stepTrail();
        x += xCom / resolution;
        y += yCom / resolution;
        z += zCom / resolution;
    }

    /**
     * creates a trail particle from the previous particle position
     */
    private void stepTrail()
    {
        trailParticles.push(new TrailParticle(x, y, z, new Color(254, 254, 254), width, height));
        if(trailParticles.size()>=trailLength)
        {
            trailParticles.pollLast();
        }
    }

    /**
     *
     * @param collideWith- the particle being collided with.
     */
    public void combineObjects(PhysicsObject collideWith)
    {
        double grav2 = collideWith.getMass();
        double xcom2 = collideWith.getxCom();
        double ycom2 = collideWith.getyCom();

        xCom = (xCom * mass +xcom2*grav2)/(mass +grav2);
        yCom = ((yCom * mass +ycom2*grav2)/2)/(mass +grav2);
        mass +=collideWith.getMass();
    }

    public int compareTo(DrawPoint test) {
        if(getZdepth()<test.getZdepth())
            return 1;
        else
            return -1;
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
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getZdepth()
    {
        return z*-1+width/2;
    }

    public LinkedList<TrailParticle> getTrailParticles() {
        return trailParticles;
    }
}//class physicsObject
