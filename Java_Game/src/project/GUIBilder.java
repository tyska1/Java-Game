package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class GUIBilder {
	
	public GUIBilder() {
			
	}
	
	public JButton prepareButton(String buttonName) {
		
		JButton button = new JButton();
		button.setBackground(new Color(168,219,245));
		button.setBounds(500, 500, 50, 50);
		button.setFocusable(false);
		button.setFont(new Font("Comic Sans", Font.BOLD, 20));
		button.setPreferredSize(new Dimension(180, 100));
		button.setText(buttonName);
		
		
		return button;
		
	}
	
	public JLabel prepareLabel() {
		JLabel label = new JLabel();
		ImageIcon imageThree = new ImageIcon("down.png");
		label.setIcon(imageThree);
		//label.setText("Wersja jêzykowa:");
		label.setHorizontalTextPosition(JLabel.RIGHT);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setIconTextGap(-270);
		label.setFont(new Font("Comic Sans", Font.BOLD, 15));
		
		return label;
	}
	
	public JPanel preparePanel(String panelName) {

		String top = "Top";
		String center = "Center";
		String bottom = "Bottom";
		JPanel panel = new JPanel();
		
		if (panelName.equals(top)) {
			panel.setBounds(15, 15, 595, 160);
			panel.setLayout(new BorderLayout()); 
		}
		else if (panelName.equals(center)) {
			panel.setBackground(new Color(250, 248, 205));	
			panel.setBounds(15, 190, 595, 160);
		}
		else if (panelName.equals(bottom)) {
			panel.setBounds(15, 200, 595, 460);
			panel.setLayout(new BorderLayout());
			panel.setBackground(new Color(250, 248, 205));
		}
		
		return panel;
	}
	
	public JComboBox prepareBox(){
		
		String[] majorArray = {"English", "Polski", "Deutsch"};
		JComboBox choice = new JComboBox(majorArray);
		choice.setBounds(460, 284, 100, 50);
		choice.setSelectedIndex(1);
		choice.setSize(new Dimension(120, 30)); 
	
		return choice;
	}

	public JLabel prepareResultLabels() {

		JLabel label = new JLabel("", SwingConstants.CENTER);
		label.setFont(new Font("Comic Sans", Font.BOLD, 20));
		return label;
	}
	
}
