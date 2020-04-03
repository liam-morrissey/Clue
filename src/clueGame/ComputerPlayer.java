package clueGame;

import java.awt.Color;
import java.util.ArrayList;
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
	// suggestion used for suggestions and accusations
	private Solution suggestion;
	private BoardCell prevRoom;
	
	public String getPlayerType() {
		return "Computer";
	}

	public ComputerPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
		suggestion = new Solution();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell moveTo = null;
		return moveTo;
	}
	
	public void makeAccusation() {
		return;
	}
	

	public void createSuggestion() {
		possiblePeople.add(getCurrentRoomCard());
		possibleWeapons.add(getCurrentRoomCard());
		suggestion.setPerson(randCardSelector(possiblePeople));
		suggestion.setRoom(getCurrentRoomCard());
		suggestion.setWeapon(randCardSelector(possibleWeapons));
	}
	
	private Card randCardSelector(Set<Card> set) {
		Random rand = new Random();
		int randNum = rand.nextInt(set.size());
		int count = 0;
		for(Card c: possiblePeople) {
			if(count == randNum)
				return c;
			count++;
		}
		return null;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	public void setPrevRoom(BoardCell p) {
		prevRoom = p;
	}
}
