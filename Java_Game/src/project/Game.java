package project;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Game extends JFrame implements KeyListener, ActionListener{

	Color startPacman = Color.YELLOW, startBoard;
	Board board = new Board();
	Pacman pacman = new Pacman(30,40,startPacman);
	Timer t = new Timer(10,this), timeCounter;
	Balls[] balls = new Balls[10];
	Random r = new Random();
	int par, limit, time;
	JLabel jlabel, timelabel, pointsLabel, resultLabel, saveLabel, noSaveLabel, escapeLabel;
	JPanel result, menuPanel;
	JButton pacmanColor, boardColor, saveAndExit;
	String language;
	JTextField nickText = new JTextField();
	GUIBilder graphicBilder = new GUIBilder();
	JTable jtable;
	String[][] data = new String[10][3];
	Clip clip;
	boolean isSaved;
	
	Game(int width, int height, String name, int known, String language) {
		this.setResizable(false);
		this.setSize(720, 700);
		this.language = language;
		timelabel = new JLabel();
		pointsLabel = new JLabel();
		jlabel = new JLabel("Press ENTER to start the game!", SwingConstants.CENTER);
		jlabel.setFont(new Font("Verdana",1,20));
		jlabel.setForeground(Color.DARK_GRAY);
		pacmanColor = new JButton("Pacman color");
		pacmanColor.addActionListener(this);
		pacmanColor.setFocusable(false);
		boardColor = new JButton("Board color");
		boardColor.addActionListener(this);
		boardColor.setFocusable(false);
		menuPanel = new JPanel();
		menuPanel.add(pacmanColor);
		menuPanel.add(jlabel);
		menuPanel.add(timelabel);
		menuPanel.add(pointsLabel);
		menuPanel.add(boardColor);
		timelabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		timelabel.setBounds(0,0,100,100);
		timelabel.setText("TIME:   " + time + "  ");
		timelabel.setVisible(false);

		pointsLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		pointsLabel.setBounds(0,0,100,100);
		pointsLabel.setText("POINTS:   " + pacman.punkty + "  ");
		pointsLabel.setVisible(false);

		menuPanel.setBounds(0, 0, 700, 100);
		board.setBounds(50, 100, 600, 500);

		this.add(board);
		this.add(menuPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(name);
		this.setVisible(true);
		this.par = known;
		
		setFocusable(true);
		addKeyListener(this);
		
		t.start();
		timelabel.setFont(new Font("Verdana",1,24));
		pointsLabel.setFont(new Font("Verdana",1,24));
		resultLabel = graphicBilder.prepareResultLabels();
		resultLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		saveLabel = graphicBilder.prepareResultLabels();
		noSaveLabel = graphicBilder.prepareResultLabels();
		escapeLabel = graphicBilder.prepareResultLabels();
		escapeLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		result = new JPanel();
		result.setLayout(new GridLayout(4,1));
		result.add(resultLabel);
		JPanel textPanel = new JPanel();
		textPanel.add(nickText);
		JPanel savePanel = new JPanel();
		savePanel.add(saveLabel);
		savePanel.add(textPanel);
		saveAndExit = new JButton("Save");
		saveAndExit.addActionListener(this);
		saveAndExit.setFocusable(false);
		savePanel.add(saveAndExit);
		savePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		result.add(savePanel);
		nickText.setEnabled(true);
		nickText.setPreferredSize(new Dimension(300, 50));
		nickText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nickText.setText("");
			}
		});
		String [] columnNames = {"Lp", "Nick", "Punkty"};

		jtable = new JTable(data, columnNames);
		jtable.setEnabled(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 3; i ++) {
            jtable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
		JScrollPane sp = new JScrollPane(jtable);
		sp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		result.add(sp);
		result.add(escapeLabel);
		this.add(result);
		readRankFile();
		setLanguage();
		isSaved = false;

	}

	private void prepareSound() {
		String soundName = "src/project/burger.wav";
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readRankFile() {
		try {
			File myObj = new File(this.par == 0 ? "src/project/filenamegame.txt" : "src/project/filename.txt");
			Scanner myReader = new Scanner(myObj);
			int i = 0;
			while (myReader.hasNextLine()) {
				String[] splitLine = myReader.nextLine().split(" ");
				data[i][0] = String.valueOf(i+1);
				data[i][1] = splitLine[0];
				data[i][2] = splitLine[1];
				i++;
			}
			limit = Integer.parseInt(data[9][2]);
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
	}
	}

	public int calcPoints() {
		int points = pacman.punkty;
		for (int i = 0; i < 10; i++) {
			if (points >=  Integer.parseInt(data[i][2])) {
				rewritePoints(i);
				return i;
			}
		}
		return 0;
	}

	public void rewritePoints(int index) {
		if (!isSaved) {
			for (int i = 9; i > index; i--) {
				data[i][1] = data [i-1][1];
				data[i][2] = data [i-1][2];
			}
			data[index][1] = "YOUR PLACE";
			data[index][2] = String.valueOf(pacman.punkty);
			isSaved = true;
		}
	}

	public String makeRanking() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			sb.append(data[i][1]);
			sb.append(" ");
			sb.append(data[i][2]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public void gameOver() {
		result.setVisible(true);
		if (pacman.punkty > limit) {
			nickText.setVisible(true);
			data[calcPoints()][1] = nickText.getText().replaceAll("\\s+","");
		} else {
			nickText.setVisible(false);
		}
		refreshPoints();

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
					continue; 
				}
				balls[i].draw(g);
				if (pacman.eat(balls[i])) {
					prepareSound();
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

		private boolean isAllBallsNull() {
			for (int i = 0; i < 10; i++) {
				if (balls[i] != null) {
					return false;
				}
			}
			return true;
				
		}
		
		 public int[] createPointsArray(int size, int sumOfPoints) {

		        int pointsArray[] = new int[size];
		        for (int i = 0; i < sumOfPoints; i++)
		        {
		            pointsArray[(int)(Math.random() * size)]++;
		        }
		        return pointsArray;
		    }
		
		void setPosition() {
			int x,y, randX,randY,punkty;
			int pointsTable[] = createPointsArray(10,300);
			for (int i = 0; i < 10; i++) {
			randX = r.nextInt(getWidth()-20);
			x = randX < 10 ? randX + 10 : randX;
			randY = r.nextInt(getHeight()-20);
			y = randY < 10 ? randY + 10 : randY;

			if (par == 0){
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
	}

	@Override
	public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_UP) {
			pacman.direction(0, -1); 
			}
			if (keyCode == KeyEvent.VK_DOWN) {
			pacman.direction(0, 1); 
			}
			if (keyCode == KeyEvent.VK_LEFT) {
			pacman.direction(-1, 0); 
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
			pacman.direction(1, 0); 
			}
			if (keyCode == KeyEvent.VK_SPACE) {
			pacman.direction(0, 0);
			}
			
			if (keyCode == KeyEvent.VK_ENTER) {
				start();}
			if (keyCode == KeyEvent.VK_ESCAPE) {
			this.dispose();
			}
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
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
		if(e.getSource() == saveAndExit)
		{
			calcPoints();
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(this.par == 0 ? "src/project/filenamegame.txt" : "src/project/filename.txt"));
				writer.write(makeRanking());
				writer.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			};
			
			this.dispose();
		}

		board.repaint();

		if(board.isOver) {
			timeCounter.stop();
			board.setVisible(false);
			menuPanel.setVisible(false);
			gameOver();
		} else {
			board.repaint();
		}
	}

	private void setLanguage() {
		switch (this.language) {
		case "Polski":
			jlabel.setText("Wciœnij ENTER aby rozpocz¹æ");
			jtable.getColumnModel().getColumn(1).setHeaderValue("nazwa");
			jtable.getColumnModel().getColumn(2).setHeaderValue("punkty");
			pacmanColor.setText("Kolor pacmana");
			boardColor.setText("Kolor planszy");
			resultLabel.setText("KONIEC GRY, czas: " + time + " punkty: " + pacman.punkty);
			saveLabel.setText("Brawo! Aby dodaæ do rankingu wpisz swój nick poni¿ej:");
			noSaveLabel.setText("Niestety, zdoby³eœ zbyt ma³o punktów. Próbuj dalej!");
			nickText.setText("Nazwa");
			escapeLabel.setText("Aby powróciæ do menu g³ównego wciœnij klawisz ESCAPE");
			saveAndExit.setText("Zapisz i zamknij");
			break;
		case "English":
			jlabel.setText("Press ENTER to start the game!");
			jtable.getColumnModel().getColumn(1).setHeaderValue("name");
			jtable.getColumnModel().getColumn(2).setHeaderValue("points");
			pacmanColor.setText("Pacman color");
			boardColor.setText("Board color");
			resultLabel.setText("GAME OVER, time: " + time + " points: " + pacman.punkty);
			saveLabel.setText("Congrats! Your score qualifies for a ranking. Enter your nickname below:");
			noSaveLabel.setText("Unfortunately, you scored too little points to enter the ranking. Keep trying!");
			nickText.setText("Nickname");
			escapeLabel.setText("To return to the main menu, press the ESCAPE key");
			saveAndExit.setText("Save and close");
			break;
		case "Deutsch":
			jlabel.setText("Zum Starten ENTER drücken");
			jtable.getColumnModel().getColumn(1).setHeaderValue("Name");
			jtable.getColumnModel().getColumn(2).setHeaderValue("Punkte");
			pacmanColor.setText("Pacman farbe");
			boardColor.setText("Board farbe");
			resultLabel.setText("SPIEL IST AUS, zeit: " + time + " punkte: " + pacman.punkty);
			saveLabel.setText("Weiter so! Um Ihre Punktzahl zur Rangliste hinzuzufügen, geben Sie Ihren Spitznamen ein:");
			noSaveLabel.setText("Leider haben Sie zu wenig Punkte erzielt. Versuchen Sie es noch einmal");
			nickText.setText("Spitznamen");
			escapeLabel.setText("Um zum Hauptmenü zurückzukehren, drücken Sie ESCAPE");
			saveAndExit.setText("Speichern und schließen");
			break;
			default:
				throw new IllegalStateException("Unexpected value");
		}
	}
	private void refreshPoints() {
		switch (this.language) {
		case "Polski":
			resultLabel.setText("KONIEC GRY, czas: " + time + " punkty: " + pacman.punkty);
			saveLabel.setText(pacman.punkty > limit ? "Brawo! Aby dodaæ do rankingu wpisz swój nick poni¿ej:" : "Niestety, zdoby³eœ zbyt ma³o punktów. Próbuj dalej!");
			saveAndExit.setText("Zapisz i zamknij");
			break;
			case "English":
			resultLabel.setText("GAME OVER, time: " + time + " points: " + pacman.punkty);
			saveLabel.setText(pacman.punkty > limit ? "Congrats! Your score qualifies for a ranking. Enter your nickname below:" : "Unfortunately, you scored too little points to enter the ranking. Keep trying!");
			saveAndExit.setText("Save and close");
			break;
		case "Deutsch":
			resultLabel.setText("SPIEL IST AUS, zeit: " + time + " punkte: " + pacman.punkty);
			saveLabel.setText(pacman.punkty > limit ? "Weiter so! Um Ihre Punktzahl zur Rangliste hinzuzufügen, geben Sie Ihren Spitznamen ein:" : "Leider haben Sie zu wenig Punkte erzielt. Versuchen Sie es noch einmal");
			saveAndExit.setText("Speichern und schließen");
			break;
			default:
				throw new IllegalStateException("Unexpected value");
		}
	}
}
