package project;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ranking extends JFrame implements KeyListener, ActionListener {


    JTable jtable;
    String[][] data = new String[10][3];
    String language;
    GUIBilder graphicBilder2 = new GUIBilder();
    JButton ranking, cwiczenia;
    int mode;
    JLabel name33;


    Ranking(int width, int height, String name, int known, String language) {
    	
    	this.language = language;
        this.setResizable(false);
        this.setSize(300, 290);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(name);
        this.setVisible(true);
        this.setLocation(400, 200);
        setFocusable(true);
        addKeyListener(this);
        this.setLayout(new BorderLayout());
        name33 = new JLabel("Tryb rozgrywka");
        name33.setHorizontalAlignment(0);
        this.add(name33, BorderLayout.PAGE_START);
        JPanel buttonsBag = new JPanel();
        buttonsBag.setLayout(new GridLayout(1,2));
        ranking = graphicBilder2.prepareButton("Rozgrywka");
        ranking.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 0;
				setLanguage();
				readRankFile();
				jtable.repaint();
			}
		});
        cwiczenia = graphicBilder2.prepareButton("Æwiczenia");
        cwiczenia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 1;
				setLanguage();
				readRankFile();
				jtable.repaint();
			}
		});
    	ranking.setPreferredSize(new Dimension(150, 50));
    	cwiczenia.setPreferredSize(new Dimension(150, 50));
        buttonsBag.add(ranking);
        buttonsBag.add(cwiczenia);
        this.add(buttonsBag, BorderLayout.PAGE_END);
        String [] columnNames = {"Lp", "Nick", "Punkty"};
        readRankFile();
        jtable = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 3; i ++) {
            jtable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        jtable.setEnabled(false);
        setLanguage();
        JScrollPane sp = new JScrollPane(jtable);
        this.add(sp, BorderLayout.CENTER); 


    }
    
    private void setLanguage() {
        switch (this.language) {
            case "Polski":
                jtable.getColumnModel().getColumn(0).setHeaderValue("L.p.");
                jtable.getColumnModel().getColumn(1).setHeaderValue("nazwa");
                jtable.getColumnModel().getColumn(2).setHeaderValue("punkty");
                cwiczenia.setText("Æwiczenia");
                ranking.setText("Rozgrywka");
                if (this.mode == 0)
                	name33.setText("Tryb rozgrywka");
                else
                	name33.setText("Tryb æwiczenia");
                break;
            case "English":
                jtable.getColumnModel().getColumn(0).setHeaderValue("L.p.");
                jtable.getColumnModel().getColumn(1).setHeaderValue("name");
                jtable.getColumnModel().getColumn(2).setHeaderValue("points");
                cwiczenia.setText("Practice");
                ranking.setText("Game");
                if (this.mode == 0)
                	name33.setText("Game mode");
                else
                	name33.setText("Practise mode");
                break;
            case "Deutsch":
                jtable.getColumnModel().getColumn(0).setHeaderValue("L.p.");
                jtable.getColumnModel().getColumn(1).setHeaderValue("Name");
                jtable.getColumnModel().getColumn(2).setHeaderValue("Punkte");
                ranking.setText("Spiel");
                cwiczenia.setText("Übungs");
                if (this.mode == 0)
                	name33.setText("Spielmode");
                else
                	name33.setText("Übungsmode");
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
    }
    public void readRankFile() {
        try {
            File myObj = new File(this.mode == 0 ? "src/project/filenamegame.txt" : "src/project/filename.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String[] splitLine = myReader.nextLine().split(" ");
                data[i][0] = String.valueOf(i+1);
                data[i][1] = splitLine[0];
                data[i][2] = splitLine[1];
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
