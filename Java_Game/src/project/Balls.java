package project;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Balls extends Figure {

	Color[] colors = {Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.RED, Color.PINK};
	Color color;

	public Balls(int X, int Y, int punkty) {
		super(X, Y);
		this.delay = 1000;
		this.punkty = punkty;
		this.color = colors[new Random().nextInt(colors.length)];
	}

	@Override
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(X-10, Y-10, 20, 20);
		g.setColor(Color.white);
		g.drawString(punkty + "", X-5, Y+5);
	}

	@Override
	void changeState() {
		if (punkty > 0) {
			punkty--;}
	}

}
