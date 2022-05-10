package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxButton;

public class Game extends JFrame implements KeyListener, ActionListener{

	Color startPacman = Color.blue, startBoard;
	Board board = new Board();
	Pacman pacman = new Pacman(30,40, startPacman);
	Timer t = new Timer(10,this);
	Balls[] balls = new Balls[10];
	Random r = new Random();
	int par;
	JLabel jlabel = new JLabel("Press ENTER to start the game!", SwingConstants.CENTER);
	JLabel timelabel = new JLabel();
	JLabel pointsLabel = new JLabel();
	Timer timeCounter;
	JPanel result = new JPanel();
	JPanel menuPanel = new JPanel();
	JButton pacmanColor, boardColor;
	int time;
	
	Game(int width, int height, String name, int known) {
		this.setResizable(false);
		this.setSize(920, 900);
		pacmanColor = new JButton("Pacman color");
		pacmanColor.addActionListener(this);
		pacmanColor.setFocusable(false);
		boardColor = new JButton("Board color");
		boardColor.addActionListener(this);
		boardColor.setFocusable(false);
		menuPanel.add(pacmanColor);
		menuPanel.add(jlabel);
		menuPanel.add(timelabel);
		menuPanel.add(pointsLabel);
		menuPanel.add(boardColor);
		timelabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		timelabel.setBounds(0,0,100,100);
		timelabel.setText("TIME: \n  " + time + "  ");
		timelabel.setVisible(false);

		pointsLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		pointsLabel.setBounds(0,0,100,100);
		pointsLabel.setText("POINTS: \n  " + pacman.punkty + "  ");
		pointsLabel.setVisible(false);
		menuPanel.setBounds(0, 0, 900, 100);
		board.setBounds(50, 100, 800, 700);

		this.add(board);
		this.add(menuPanel);
		//board.setBounds(500, 700, 0, 0);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setLocation(width/4, height/4);
		this.setTitle(name);
		//frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("pacman.png"));
		this.setVisible(true);
		setFocusable(true);
		addKeyListener(this);
		t.start();
		this.par = known;
		jlabel.setFont(new Font("Verdana",1,20));
		jlabel.setForeground(Color.RED);
		timelabel.setFont(new Font("Verdana",1,30));
		pointsLabel.setFont(new Font("Verdana",1,30));
		
	}
	
	public class Board extends JPanel {
		
		boolean isOver = false;
		Color color;
		
		Board(){}

		protected void paintComponent(Graphics g) {
			g.setColor(color);
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
					pointsLabel.setText("POINTS: \n  " + pacman.punkty + "  ");
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
		        int suma = Arrays.stream(pointsArray).sum();
		        return pointsArray;
		    }
		
		void setPosition() {
	//		int sum = 0;
			int x,y, randX,randY,punkty;
	//		List<Integer> sumPoints = createPointsList();
			int pointsTable[] = createPointsArray(10,300);
			int suma = Arrays.stream(pointsTable).sum();
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

	public void simpleTimer() {
		timeCounter = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time++;
				timelabel.setText("TIME: \n  " + time + "  ");
			}
		});
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
			pacman.direction(0, -1); // w g�r�
			}
			if (keyCode == KeyEvent.VK_DOWN) {
			pacman.direction(0, 1); // w d�
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
		result.setVisible(false);
		pacman = new Pacman(30, 40, startPacman);
		pacman.direction(1, 0);
		new Thread(pacman).start();
		board.setPosition();
		for (int i = 0; i < 10; i++) {
		new Thread(balls[i]).start();
		}
		time = 0;
		this.simpleTimer();
		jlabel.setVisible(false);
		timelabel.setVisible(true);
		pointsLabel.setVisible(true);
		timeCounter.restart();

		}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == pacmanColor)
		{
			Color newColor = JColorChooser.showDialog(null, "Choose color", Color.white);
			startPacman = newColor;
			pacman.setColor(newColor);
		}
		if(e.getSource() == boardColor)
		{
			Color newColor = JColorChooser.showDialog(null, "Choose color", Color.white);
			startBoard = newColor;
			board.color = newColor;
		}

		board.repaint() ;

		if(board.isOver) {//System.out.println(pacman.punkty);
			//this.removeAll();
			//Board result = new Board();
			//result.setBounds(500, 700, 0, 0);
			//this.add(result);
			//result.setVisible(true);
			//this.dispose();
			//board.isOver = false;
			timeCounter.stop();
			board.setVisible(false);
			menuPanel.setVisible(false);
			this.add(result);
			result.setVisible(true);

			result.add(new JLabel("KONIEC, czas: "+time+" punkty: " + pacman.punkty));
		}
		
		//board.repaint() ;
		
	}

}
