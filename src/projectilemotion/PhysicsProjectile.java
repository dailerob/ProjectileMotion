/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.Random;
import java.util.PriorityQueue;

public class PhysicsProjectile extends Canvas implements KeyListener, Runnable
{
	private ArrayList<PhysicsObject> list = new ArrayList<PhysicsObject>();
	private boolean[] keys;
	private BufferedImage back;
        private final int width;
        private final int height;
        private double xradian; 
        private double yradian;
        private double zradian;
        private double zoom;
        public static PriorityQueue<DrawRequest> drawMap =  new PriorityQueue<DrawRequest>();
	double on = 1;
        Random rand = new Random();
	Color t = new Color(0,0,0,255);

	public PhysicsProjectile(int width, int hight)
	{
            DrawRequest.initialize();
            this.width = width; 
            this.height = hight;
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
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        graphToBack.setColor(t);
        graphToBack.fillRect(0, 0, width, height);

        //draws each projectile then moves them
        for (int x = 0; x < list.size(); x++) {
            list.get(x).draw(graphToBack, xradian ,yradian,zradian,zoom);
            list.get(x).step(1000);
        }

        while(drawMap.size()>0)
            {
                drawMap.poll().draw(graphToBack);
            }

        //adds the vector to each of the projectiles
        
        
        for (int x = 0; x < list.size(); x++) {
            for (int check = 0; check < list.size(); check++) {
                if (check != x) {
                    if(list.get(x).calcVec(list.get(check).getX(), list.get(check).getY(), list.get(check).getZ(), list.get(check).getGravity(),0)==true){
                        list.get(check).comineObjects(list.get(x));
                        list.remove(x);
                    }    
                }
            }
        }
        
        twoDGraph.drawImage(back, null, 0, 0);
    }//paint

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
                
            case 'R':keys[2] = true;
                on *= -1;
                list.add(new PhysicsObject(rand.nextDouble()*width/2*on, 0,0, 0, rand.nextGaussian()*100+1800*on, rand.nextGaussian()*150, Math.abs(rand.nextGaussian()*1), 500 , width, height));
                list.add(new PhysicsObject(0, rand.nextDouble()*width/2*on,0, rand.nextGaussian()*100+1800*-on, 0, rand.nextGaussian()*150, Math.abs(rand.nextGaussian()*1), 500 , width, height));
                list.add(new PhysicsObject(0 , 0, 0,0,0, 0 , 1 ,500, width, height));
                
            
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
    
    public void incYradian() {
        yradian+=.01;
    }
    
    public void decYradian()
    {
        yradian-= .01;
    }
    
    public void incXradian() {
        xradian+=.01;
    }
    
    public void decXradian()
    {
        xradian-= .01;
    }
    
    public void incZradian() {
        zradian+=.01;
    }
    
    public void decZradian()
    {
        zradian-= .01;
    }
    
    public void incZoom() {
        zoom+=.01;
    }
    
    public void decZoom()
    {
        zoom-= .01;
    }
	
        
//voodoo majic right here
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

