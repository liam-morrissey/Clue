package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.ComputerPlayer;

public class ControlPanel extends JPanel {
	private JButton nextPlayer, makeAccusation;
	private Board board;
	protected Player currentPlayer;
	protected ArrayList<Player> players;
	protected turnPanel tPanel;
	protected diePanel dPanel;
	protected guessPanel gPanel;
	protected guessResultPanel grPanel;
	
	public ControlPanel(Player currentPlayer) {
		board = Board.getInstance();
		this.currentPlayer = currentPlayer;
		players = board.getPlayers();
		
		setLayout(new GridLayout(2, 3));
		
		nextPlayer = new JButton("Next player");
		makeAccusation = new JButton("Make an accusation");
		
		tPanel = new turnPanel();
		
		dPanel = new diePanel();
		
		gPanel = new guessPanel();
		
		grPanel = new guessResultPanel();
		
		nextPlayer.addActionListener(new NextPlayerListener());
		
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
			// If it is the player's turn and they have not moved, do nothing
			if(currentPlayer.getPlayerType() == "Human" && !board.getPlayerHasMoved()) {
				return;
			}
			 
			currentPlayer = board.nextTurn();
			
			// Roll the dice
			Random rand = new Random();
			int diceRoll = rand.nextInt(6) + 1;
			board.calcTargets(currentPlayer.getLocation(), diceRoll);
			
			// Display targets and s
			if(currentPlayer.getPlayerType() == "Human") {
				board.setDrawTargets(true);
				board.setPlayerHasMoved(false);
			} else {
				// Make a suggestion if they are in a room
				if(currentPlayer.getLocation().isDoorway()) {
					currentPlayer.createSuggestion();
					String accusedPlayer = currentPlayer.getSuggestion().getPerson().getName();
					// Find the player that has been suggested and move them to the room
					for(Player i : board.getPlayers()) {
						if(i.getName().equals(accusedPlayer)) {
							i.setLocation(currentPlayer.getLocation());
						}
					}
					
					Card disproved = board.handleSuggestion(currentPlayer);
					// Show the card that was used to disprove
					if(disproved != null) {
						gPanel.setText(currentPlayer.getSuggestion().toString());
						grPanel.setText(disproved.getName());
					} if(disproved == null) {
						currentPlayer.flagSuggestion();
					}
					
					currentPlayer.setPrevRoom(currentPlayer.getLocation());
				}
				board.setDrawTargets(false);
				currentPlayer.makeMove(board.getTargets());
			}
			board.repaint();
			
			tPanel.setText(currentPlayer.getName());
			
			dPanel.setText(Integer.toString(diceRoll));
		}
	}
	
	public class diePanel extends JPanel {
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
		
		public void setText(String text) {
			roll.setText(text);
		}
	}
	
	public class guessPanel extends JPanel{
		private JLabel guessLabel;
		private JTextField guess;
		
		public guessPanel() {
			guessLabel = new JLabel("Guess Made");
			guess = new JTextField(20);
			guess.setEditable(false);
			add(guessLabel);
			add(guess);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		}
		
		public void setText(String text) {
			guess.setText(text);
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
		
		public void setText(String text) {
			response.setText(text);
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
		
		public void setText(String text) {
			turn.setText(text);
		}
	}
}
