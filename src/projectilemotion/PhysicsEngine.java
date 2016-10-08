/*
 * Roberto Dailey, 2015
 */

package projectilemotion;

/**
 *
 * @author Roberto
 */
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import java.util.PriorityQueue;

public class PhysicsEngine extends Canvas implements KeyListener, Runnable
{
    //window and engine settings
    private ArrayList<PhysicsObject> list = new ArrayList<PhysicsObject>();
    private boolean[] keys;
    private BufferedImage back;
    private final int width;
    private final int height;
    private double xRadian;
    private double yRadian;
    private double zRadian;
    private double zoom;
    public static PriorityQueue<DrawPoint> depthMap = new PriorityQueue<DrawPoint>();
    Random rand = new Random();
    Color backroundColor = new Color(0,0,0,255);

    public PhysicsEngine(int width, int hieght)
    {
        this.width = width;
        this.height = hieght;
        keys = new boolean[4];
        setBackground(Color.WHITE);
        setVisible(true);
        new Thread(this).start();
        addKeyListener(this);		//starts the key thread to log key strokes
    }

    public void update(Graphics window){
        paint(window);
    }

    public void paint(Graphics window) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D twoDGraph = (Graphics2D) window;

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        //create a graphics reference to the back ground image
        //draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        //creates backround color
        graphToBack.setColor(backroundColor);
        graphToBack.fillRect(0, 0, width, height);


        //adds the drawRequests for each of the particles
        for (int x = 0; x < list.size(); x++) {
            list.get(x).draw(graphToBack, xRadian, yRadian, zRadian,zoom);
            list.get(x).step(1000);
        }

        //adds each of the PhysicsEngines and TrailParticles into the depthMap heap
        for(int x = 0; x< list.size(); x++)
        {
            depthMap.add(list.get(x));
            LinkedList<TrailParticle> trailParticles = list.get(x).getTrailParticles();
            for(TrailParticle index: trailParticles)
            {
                depthMap.add(index);
            }
            list.get(x).step(1000);
        }

        //pulls elements from the heap and draws them in order of increasing depth
        while(depthMap.size()>0)
        {
            depthMap.poll().draw(graphToBack,xRadian,yRadian,zRadian,zoom);
        }

        //adds the forces of each of the particles onto eachother.
        for (int x = 0; x < list.size(); x++) {
            for (int check = 0; check < list.size(); check++) {
                if (check != x) {
                    if(list.get(x).calcVec(list.get(check).getX(), list.get(check).getY(), list.get(check).getZ(), list.get(check).getMass(),2)==true){
                        list.get(check).combineObjects(list.get(x));
                        list.remove(x);
                    }
                }
            }
        }

        twoDGraph.drawImage(back, null, 0, 0);
    }//paint

    //serves to create actions when keys are pressed
    public void keyPressed(KeyEvent e) {

        switch (toUpperCase(e.getKeyChar())) {
            case 'A':keys[0] = true;
                decXradian();
                break;

            case 'D':keys[3] = true;
                incXradian();
                break;

            case 'W':keys[0] = true;
                decYradian();
                break;

            case 'S':keys[3] = true;
                incYradian();

                break;

            case 'E':keys[1] = true;
                incZradian();
                break;

            case 'Q':keys[1] = true;
                decZradian();
                break;

            //case for adding particles
            case 'R':keys[2] = true;
                //creating an individual particle's setting
                double xCord = rand.nextGaussian()*width/2;
                double yCord = rand.nextGaussian()*height/2;
                double zCord = rand.nextGaussian()*height/2;
                double xCom = rand.nextGaussian()*10;
                double yCom = rand.nextGaussian()*10;
                double zCom = rand.nextGaussian()*10;
                double mass = 1;
                int trailLength = 500;

                list.add(new PhysicsObject(xCord, yCord, zCord, xCom, yCom, zCom, mass, trailLength, width, height));
                break;

            case 'T':
                keys[1] = true;
                incZoom();
                break;

            case 'G':
                keys[1] = true;
                decZoom();
                break;


        }
    }//keyPressed

    //serves to set the keys back to their original states
    public void keyReleased(KeyEvent e) {
        switch (toUpperCase(e.getKeyChar())) {
            case 'A':keys[0] = false;break;
            case 'E':keys[1] = false;break;
            case 'Q':keys[1] = false;break;
            case 'R':keys[2] = false;break;
            case 'D':keys[3] = false;break;
            case 'W':keys[0] = false;break;
            case 'S':keys[1] = false;break;
            case 'T':keys[0] = false;break;
            case 'G':keys[1] = false;break;
        }
    }//keyReleased

    //has to be here for keyListener
    public void keyTyped(KeyEvent e) {
    }

    //user input variable minipulation
    public void incYradian() {
        yRadian +=.01;
    }

    public void decYradian()
    {
        yRadian -= .01;
    }

    public void incXradian() {
        xRadian +=.01;
    }

    public void decXradian()
    {
        xRadian -= .01;
    }

    public void incZradian() {
        zRadian +=.01;
    }

    public void decZradian()
    {
        zRadian -= .01;
    }

    public void incZoom() {
        zoom+=.01;
    }

    public void decZoom()
    {
        zoom-= .01;
    }


    //allows for the constant key listing
    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(0);
                repaint();
            }
        } catch (Exception e) {
        }
    }//run
}//class Physics Projectile

