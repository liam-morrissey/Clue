package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class ComputerPlayer extends Player {
	private BoardCell targetRoom = null, currentRoom = null;
	private Set<BoardCell> targetRoomList;
	
	public String getPlayerType() {
		return "Computer";
	}

	public ComputerPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
		targetRoomList = new HashSet<BoardCell>();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return targetRoom;
	}
	
	public void makeAccusation() {
		return;
	}
	
	public void createSuggestion() {
		return;
	}
	
	public Set<BoardCell> getTargetList(){
		return targetRoomList;
	}
}
