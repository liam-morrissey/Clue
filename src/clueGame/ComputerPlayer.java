package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class ComputerPlayer extends Player {
	
	//private BoardCell prevRoom;
	private char prevRoom;
	private Boolean makeSuggestion = true;
	
	public String getPlayerType() {
		return "Computer";
	}

	public ComputerPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
		suggestion = new Solution();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell moveTo = null;
		
		// Find if one of the spaces is a doorway and enter it if it is
		for(BoardCell testRoom : targets) {
			if(testRoom.isDoorway() && testRoom.getInitial() != prevRoom)
				return testRoom;
		}
		
		// Gets a random room
		Random rand = new Random();
		int randNum = rand.nextInt(targets.size());
		int i = 0;
		for(BoardCell randRoom : targets) {
			if(i == randNum)
				return randRoom;
			i++;
		}
		
		return moveTo;
	}
	
	public void makeAccusation() {
		Board board = Board.getInstance();
		if(board.checkAccusation(suggestion)) {
			JOptionPane.showMessageDialog(null, getName() + " has solved with the solution " + suggestion);
		}
		return;
	}
	
	@Override
	public void createSuggestion() {
		if(!makeSuggestion) {
			makeAccusation();
		}
		suggestion.setPerson(randCardSelector(possiblePeople));
		suggestion.setRoom(getCurrentRoomCard());
		suggestion.setWeapon(randCardSelector(possibleWeapons));
	}
	
	private Card randCardSelector(Set<Card> set) {
		Random rand = new Random();
		int randNum = rand.nextInt(set.size());
		int count = 0;
		for(Card c: set) {
			if(count == randNum)
				return c;
			count++;
		}
		return null;
	}
	
	@Override
	public void setPrevRoom(BoardCell p) {
		prevRoom = p.getInitial();
	}
	
	//This function moves the computer player based on the result of the pickLocation function
	@Override
	public void makeMove(Set<BoardCell> set) {
		this.setLocation(pickLocation(set));
	}
	
	@Override
	public void flagSuggestion() {
		// Return if the room card is in their hand
		if(getCardsInHand().contains(getCurrentRoomCard())) {
			return;
		}
		makeSuggestion = false;
	}
}
