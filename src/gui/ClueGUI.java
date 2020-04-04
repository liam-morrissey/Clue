package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGUI extends JFrame {
	public ClueGUI() {
		setSize(new Dimension(800, 600));
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ControlPanel cPanel = new ControlPanel();
		add(cPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
	}

}
