package project;

import java.awt.Color;
import java.awt.Graphics;

public class Pacman extends Figure {

	private int dx, dy;
	private Color color;
	boolean eat(Balls p) {
		return Math.sqrt((X - p.X)*(X - p.X) + (Y - p.Y) * (Y - p.Y)) < 15;
		}
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.color);
		int alfa = 45;
		if (dx < 0) alfa = 180 + 45;
		if (dx > 0) alfa = 45;
		if (dy < 0) alfa = 90 + 45;
		if (dy > 0) alfa = 270 + 45;
		g.fillArc(X-15, Y-15, 30, 30, alfa, 270);
		g.setColor(Color.white);
		g.drawString(punkty + "", X, Y-15);
		
	}

	@Override
	void changeState() {
		// TODO Auto-generated method stub
		X += dx;
		Y += dy;
		
		if (X < 15) { X = 15; dx = -dx;}
		if (X > 585) { X = 585; dx = -dx; }
		
		if (Y < 15) { Y = 15; dy = -dy;}
		if (Y > 485) { Y = 485; dy = -dy; }
	}
	
	Pacman(int X, int Y, Color color) {
		super(X, Y);
		dx = 0;
		dy = 0;
		delay = 7;
		this.color = color;
		}
	
	public void direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		}


		public void setColor(Color color) {
		this.color = color;
		}
	
	

	
}
