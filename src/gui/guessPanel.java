package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class guessPanel extends JPanel{
	private JLabel guessLabel;
	private JTextField guess;
	
	public guessPanel() {
		guessLabel = new JLabel("Whose turn?");
		guess = new JTextField(10);
		
		add(guessLabel);
		add(guess);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
	}
}
