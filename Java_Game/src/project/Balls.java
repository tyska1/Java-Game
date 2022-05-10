package project;

import java.awt.Color;
import java.awt.Graphics;

public class Balls extends Figure {

	public Balls(int X, int Y, int punkty) {
		super(X, Y);
		this.delay = 1000;
		this.punkty = punkty;
	}
	
	@Override
	void draw(Graphics g) {
		//g.setColor(new Color(255 - 5*punkty, 5*punkty, 0));
		g.setColor(Color.pink);
		g.fillOval(X-10, Y-10, 20, 20);
		////g.fillOval(X, Y, 20, 20);
		g.setColor(Color.white);
		g.drawString(punkty + "", X-5, Y+5);
		
	}

	@Override
	void changeState() {
		// TODO Auto-generated method stub
		if (punkty > 0) {
			punkty--;}
	}

}
