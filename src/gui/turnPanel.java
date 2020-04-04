package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class turnPanel extends JPanel {
	private JLabel turnLabel;
	private JTextField turn;
	
	public turnPanel() {
		turnLabel = new JLabel("Whose turn?");
		turn = new JTextField(10);
		
		add(turnLabel);
		add(turn);
	}
}
