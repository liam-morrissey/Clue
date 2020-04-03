package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class ComputerPlayer extends Player {
	// suggestion used for suggestions and accusations
	private Solution suggestion;
	private BoardCell prevRoom;
	
	public String getPlayerType() {
		return "Computer";
	}

	public ComputerPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell moveTo = null;
		return moveTo;
	}
	
	public void makeAccusation() {
		return;
	}
	
	public void createSuggestion() {
		return;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	public void setPrevRoom(BoardCell p) {
		prevRoom = p;
	}
}
