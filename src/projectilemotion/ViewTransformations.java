package projectilemotion;

/**
 * Created by Roberto on 12/18/2014.
 */
public class ViewTransformations {




    /**
     *
     * @param preRotation - the coordinates of the item before the roatation.
     * @return - the coordinates of the item after the rotation about the xy plane.
     */
    static double [] xRotation (double [] preRotation, double xRadian)
    {
        double [] rotated = {Math.cos(xRadian)*preRotation[0]+Math.sin(xRadian)
                *preRotation[2],preRotation[1],Math.cos(xRadian)*preRotation[2]
                -Math.sin(xRadian)*preRotation[0]};
        return rotated;
    }

    /**
     *
     * @param preRotation - the coordinates of the item before the roatation.
     * @return - the coordinates of the item after the rotation about the yz plane.
     */
    static double [] yRotation (double [] preRotation, double yRadian)
    {
        double [] rotated = {preRotation[0],Math.cos(yRadian)*preRotation[1]
                +Math.sin(yRadian)*preRotation[2],Math.sin(yRadian)
                *preRotation[1]-Math.cos(yRadian)*preRotation[2]};
        return rotated;
    }

    /**
     *
     * @param preRotation - the coordinates of the item before the roatation.
     * @return - the coordinates of the item after the rotation about the xz plane.
     */
    static double [] zRotation (double [] preRotation, double zRadian)
    {
        double [] rotated = {Math.cos(zRadian)*preRotation[0]+Math.sin(zRadian)
                *preRotation[1],Math.cos(zRadian)*preRotation[1]-
                Math.sin(zRadian)*preRotation[0],preRotation[2]};
        return rotated;
    }

    /**
     *
     * @param zoom - the amount to increase or decrease the camera angle
     * @param zDepth - the depth of the particles within the sim
     * @param width - the width of the frame
     * @return - the adjustment to the position of the particle
     */
    static double perspectiveAdjustment(double zoom, double zDepth, int width){
        return width/(width+2/Math.atan(1.047+zoom)*zDepth);
    }




}//class viewTransformations
