package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
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
	
	public Solution createSuggestion() {
		Card suggestPerson = randCardSelector(possiblePeople);
		Card suggestWeapon = randCardSelector(possibleWeapons);
		return new Solution(suggestPerson,getCurrentRoomCard(),suggestWeapon);
	}
	
	private Card randCardSelector(Set<Card> set) {
		int rand = new Random().nextInt(set.size());
		int count = 0;
		for(Card c: possiblePeople) {
			if(count == rand)
				return c;
			count++;
		}
		return null;
	}
	
	public Set<BoardCell> getTargetList(){
		return targetRoomList;
	}
}
