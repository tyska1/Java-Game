package project;

import java.awt.Color;
import java.awt.Graphics;

public class Pacman extends Figure {

	private int dx, dy;
	boolean eat(Balls p) {
		return Math.sqrt((X - p.X)*(X - p.X) + (Y - p.Y) * (Y - p.Y)) < 15;
		}
	@Override
	void draw(Graphics g) {
		g.setColor(Color.yellow);
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
		X += dx;
		Y += dy;
		
		if (X < 15) { X = 15; dx = -dx;}
		if (X > 470) { X = 470; dx = -dx; }
		
		if (Y < 10) { Y = 10; dy = -dy;}
		if (Y > 630) { Y = 630; dy = -dy; }
	}
	
	Pacman(int X, int Y) {
		super(X, Y);
		dx = 0;
		dy = 0;
		delay = 7;
		}
	
	public void direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		}
}