package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel {
	private JButton nextPlayer, makeAccusation;
	
	public ControlPanel() {
		setLayout(new GridLayout(2, 3));
		
		nextPlayer = new JButton("Next player");
		nextPlayer.addActionListener(new NextPlayerListener());
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
	
	private class NextPlayerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class diePanel extends JPanel {
		private JLabel rollLabel;
		private JTextField roll;
		
		public diePanel() {
			rollLabel = new JLabel("Roll");
			roll = new JTextField(5);
			roll.setEditable(false);
			add(rollLabel);
			add(roll);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		}
	}
	
	private class guessPanel extends JPanel{
		private JLabel guessLabel;
		private JTextField guess;
		
		public guessPanel() {
			guessLabel = new JLabel("Guess Made");
			guess = new JTextField(10);
			guess.setEditable(false);
			add(guessLabel);
			add(guess);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		}
	}
	
	private class guessResultPanel extends JPanel {
		private JLabel responseLabel;
		private JTextField response;
		
		public guessResultPanel() {
			responseLabel = new JLabel("Response");
			response = new JTextField(10);
			response.setEditable(false);
			add(responseLabel);
			add(response);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		}
	}
	
	private class turnPanel extends JPanel {
		private JLabel turnLabel;
		private JTextField turn;
		
		public turnPanel() {
			turnLabel = new JLabel("Whose turn?");
			turn = new JTextField(10);
			turn.setEditable(false);
			add(turnLabel);
			add(turn);
		}
	}
}
