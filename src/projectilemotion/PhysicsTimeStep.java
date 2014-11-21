/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectilemotion;

import javax.swing.JFrame;
import java.awt.Component;

public class PhysicsTimeStep extends JFrame
{
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 1000;

	public PhysicsTimeStep()
	{
		super("Phyics");
		setSize(WIDTH,HEIGHT);
		
		PhysicsProjectile game = new PhysicsProjectile(WIDTH, HEIGHT);
		
		((Component)game).setFocusable(true);			
		getContentPane().add(game);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main( String args[] )
	{
		PhysicsTimeStep run = new PhysicsTimeStep();
	}
}

