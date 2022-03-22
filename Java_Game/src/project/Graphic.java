package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.plaf.metal.MetalComboBoxButton;

public class Graphic extends JFrame {

	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private JLabel labelTop, labelBottom;
	private JPanel topPanel, centerPanel, bottomPanel;
	private JButton buttonGamePlay, buttonGameExercise, places;
	private JComboBox majorChoice;
	private GUIBilder graphicBilder;
	
	Graphic(){
		
		this.setSize(640,560);
		this.setLocation(width/4, height/4);
		this.setResizable(false);
		this.setTitle("Pacman");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		ImageIcon image = new ImageIcon("pacman.png"); 
		this.setIconImage(image.getImage()); 
		this.getContentPane().setBackground(new Color(250, 248, 205));	
		this.setLocationRelativeTo(null);
		
		graphicBilder = new GUIBilder();
		
		topPanel = graphicBilder.preparePanel("Top");
		
		labelTop = new JLabel();
		ImageIcon image2 = new ImageIcon("painting.png");
		labelTop.setIcon(image2);

		topPanel.add(labelTop);
		this.add(topPanel); 
		
		centerPanel = graphicBilder.preparePanel("Center");
		
		String a = "<html><p style=text-align:center>Nowa gra</p><p style=text-align:center>tryb rozgrywka</p></html>";
		String b = "<html><p style=text-align:center>Nowa gra</p><p style=text-align:center>tryb æwiczenia</p></html>";
		buttonGamePlay = graphicBilder.prepareButton(a);
		buttonGamePlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		JFrame frame1 = new JFrame("Tryb rozgrywka");
            		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		frame1.setSize(300,200);
            		frame1.setLocation(width/4, height/4);
            		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("pacman.png"));
            		frame1.setVisible(true);
            }
        });
		
	    buttonGameExercise = graphicBilder.prepareButton(b);
	    buttonGameExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		JFrame frame1 = new JFrame("Tryb æwiczenia");
            		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		frame1.setSize(300,200);
            		frame1.setLocation(width/4, height/4);
            		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("pacman.png"));
            		frame1.setVisible(true);
            }
        });
	    
	    places = graphicBilder.prepareButton("Ranking");
		
		centerPanel.add(buttonGamePlay);
		centerPanel.add(buttonGameExercise);
		centerPanel.add(places);
		this.add(centerPanel);	
		
		bottomPanel = graphicBilder.preparePanel("Bottom");
		labelBottom = graphicBilder.prepareLabel();
		majorChoice = graphicBilder.prepareBox();

		bottomPanel.add(majorChoice);  
		bottomPanel.add(labelBottom);
		this.add(bottomPanel); 
	}
	
}