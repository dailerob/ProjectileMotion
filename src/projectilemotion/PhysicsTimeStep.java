/*
 * Roberto Dailey, 2015
 *
 * Class for running project
 */

package projectilemotion;

import javax.swing.JFrame;
import java.awt.Component;

public class PhysicsTimeStep extends JFrame
{
	private static final int WIDTH = 1680;
	private static final int HEIGHT = 1050;

	public PhysicsTimeStep()
	{
		super("Phyics");
		setSize(WIDTH,HEIGHT);
		
		PhysicsEngine game = new PhysicsEngine(WIDTH, HEIGHT);
		
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

