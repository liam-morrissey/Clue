package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private JButton nextPlayer, makeAccusation;
	
	public ControlPanel() {
		setLayout(new GridLayout(2, 3));
		
		nextPlayer = new JButton("Next player");
		makeAccusation = new JButton("Make an accusation");
		
		turnPanel tPanel = new turnPanel();
		
		diePanel dPanel = new diePanel();
		
		guessPanel gPanel = new guessPanel();
		
		guessResultPanel grPanel = new guessResultPanel();
		
		add(tPanel);
		add(nextPlayer);
		add(makeAccusation);
		add(dPanel);
		add(gPanel);
		add(grPanel);
	}
}
