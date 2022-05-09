package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.plaf.metal.MetalComboBoxButton;

public class Game extends JFrame implements KeyListener, ActionListener{

	Board board = new Board();
	Pacman pacman = new Pacman(30,40);
	Timer t = new Timer(10,this);
	Balls[] balls = new Balls[10];
	Random r = new Random();
	int par;
	
	Game(int width, int height, String name, int known) {
		this.setResizable(false);
		this.setSize(500, 700);
		this.add(board);
		board.setBounds(500, 700, 0, 0);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setLocation(width/4, height/4);		
		this.setTitle(name);
		//frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("pacman.png"));
		this.setVisible(true);
		setFocusable(true);
		addKeyListener(this);
		t.start();
		this.par = known;
		
	}
	
	public class Board extends JPanel {
		
		boolean isOver = false;
		
		Board(){}

		protected void paintComponent(Graphics g) {
			g.setColor(Color.darkGray);
			g.fillRect(0, 0, getWidth(), getHeight()); 
			pacman.draw(g);
			for (int i = 0; i < 10; i++) {
				if (balls[i] == null) {
					continue; //wracamy do linijki 61
				}
				balls[i].draw(g);
				if (pacman.eat(balls[i])) {
					pacman.punkty += balls[i].punkty;
					balls[i] = null;
					if(this.isAllBallsNull()) {
						isOver = true;
					}
				}
				if (balls[i] != null && (balls[i].punkty == 0 || balls[i].punkty < 0)) {
					balls[i] = null;
					if(this.isAllBallsNull()) {
						isOver = true;					}
				}
			}
			
			
		}
		
		public void showResult() {
			
		}
		
		private boolean isAllBallsNull() {
			for (int i = 0; i < 10; i++) {
				if (balls[i] != null) {
					return false;
				}
			}
			return true;
				
		}
		
		/*private List<Integer> createPointsList() {
			int sum = 0;
			List<Integer> points = new ArrayList<>();
			for(int i = 0; i < 10; i++) {
				points.add(r.nextInt((300 - sum) / (10 - i)) + 1);
				sum += points.get(i);
				System.out.println(sum);
				}

			return points;
		}*/
		
		 public int[] createPointsArray(int size, int sumOfPoints) {

		        int pointsArray[] = new int[size];
		        for (int i = 0; i < sumOfPoints; i++)
		        {
		            pointsArray[(int)(Math.random() * size)]++;
		        }
		        return pointsArray;
		    }
		
		void setPosition() {
	//		int sum = 0;
			int x,y, randX,randY,punkty;
	//		List<Integer> sumPoints = createPointsList();
			int pointsTable[] = createPointsArray(10,300);
	//		System.out.println(sumPoints.toString());
			for (int i = 0; i < 10; i++) {
			randX = r.nextInt(getWidth()-20);
			x = randX < 10 ? randX + 10 : randX;
			randY = r.nextInt(getHeight()-20);
			y = randY < 10 ? randY + 10 : randY;
			if (par == 0){
	//			punkty = pointsArray[i];
				punkty = pointsTable[i];
			} else {
				punkty= r.nextInt(25)+25;
			}
			balls[i] = new Balls(x,y,punkty);
			}}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_UP) {
			pacman.direction(0, -1); // w górê
			}
			if (keyCode == KeyEvent.VK_DOWN) {
			pacman.direction(0, 1); // w dó³
			}
			if (keyCode == KeyEvent.VK_LEFT) {
			pacman.direction(-1, 0); // w lewo
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
			pacman.direction(1, 0); // w prawo
			}
			if (keyCode == KeyEvent.VK_SPACE) {
			pacman.direction(0, 0); // stop
			}
			
			if (keyCode == KeyEvent.VK_ENTER) {
				start(); }
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void start() {
		pacman = new Pacman(30, 40);
		pacman.direction(1, 0);
		new Thread(pacman).start();
		board.setPosition();
		for (int i = 0; i < 10; i++) {
		new Thread(balls[i]).start();
		}
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		board.repaint() ;

		if(board.isOver) {//System.out.println(pacman.punkty);
			this.removeAll();
			//Board result = new Board();
			//result.setBounds(500, 700, 0, 0);
			//this.add(result);
			//result.setVisible(true);
			this.dispose();
			board.isOver = false;
		}
		
		//board.repaint() ;
		
	}

}
