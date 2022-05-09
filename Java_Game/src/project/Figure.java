package project;

import java.awt.Graphics;

public abstract class Figure implements Runnable {

	public int X, Y, punkty;
	protected int delay;
	
	public Figure(int X, int Y) {
		this.X = X;
		this.Y = Y;
		}
	
	abstract void draw(Graphics g); 
	abstract void changeState();
	
	public void run() {
		while (true) {
		changeState();
		try {
			Thread.sleep(delay); //usypia w¹tek
		} catch (InterruptedException ex) { 
			//throw new Exception(ex.getMessage());
			 //throw new InterruptedException();
			System.out.println(ex.getMessage());
		}
		
		}
	}
}
