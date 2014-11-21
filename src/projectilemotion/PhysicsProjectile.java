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
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class PhysicsProjectile extends Canvas implements KeyListener, Runnable
{
	private ArrayList<PhysicsObject> list = new ArrayList<PhysicsObject>();
	private boolean[] keys;
	private BufferedImage back;
        private int width;
        private int hight;
	int p1 = 0;
	int p2 = 0;
	double on = 1;
	Color t = new Color(255,255,255,2);

	public PhysicsProjectile(int width, int hight)
	{
            
            this.width = width; 
            this.hight = hight;
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
        graphToBack.fillRect(0, 0, width, hight);

        //draws each projectile then moves them
        for (int x = 0; x < list.size(); x++) {
            list.get(x).draw(graphToBack);
            list.get(x).step(1000);
        }

        //adds the vector to each of the projectiles
        for (int x = 0; x < list.size(); x++) {
            for (int check = 0; check < list.size(); check++) {
                if (check != x) {
                    list.get(x).calcVec(list.get(check).getX(), list.get(check).getY(), list.get(check).getGravity());
                }
            }
        }
        
        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void keyPressed(KeyEvent e) {

        switch (toUpperCase(e.getKeyChar())) {
            case 'W':keys[0] = true;break;
                
            case 'S':keys[1] = true;
                list.add(new PhysicsObject(600 + 0 * on, 500, 0, 0 * on, 10));
                on *= -1;
                break;
            case 'A':keys[2] = true;
                list.add(new PhysicsObject(600 + 450*on, 500, 0, 100*on, .2));
                on *= -1;
                break;
            case 'M':keys[3] = true;break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (toUpperCase(e.getKeyChar())) {
            case 'W':keys[0] = false;break;
            case 'Z':keys[1] = false;break;
            case 'A':keys[2] = false;break;
            case 'M':keys[3] = false;break;
        }
    }

    //has to be here for keyListener
    public void keyTyped(KeyEvent e) {
    }
	
        
//voodoo majic right here
   public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(0);
            repaint();
         }
      }catch(Exception e)
      {
      }
  	}	
}
