package clueGame;

import java.awt.Color;
import java.util.Set;

/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class ComputerPlayer extends Player {

	public String getPlayerType() {
		return "Computer";
	}

	public ComputerPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
	}

	//skeleton functions
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		return;
	}
	
	public void createSuggestion() {
		return;
	}
}
