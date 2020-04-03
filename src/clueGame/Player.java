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

public class Player {
	//instance variables
	private String playerName;
	private BoardCell location;
	private Color color;
	private Set<Card> seenCards;
	
	//This is done for easy comparison/finding which cards to use in suggestions
	protected Set<Card> possiblePeople;
	protected Set<Card> possibleWeapons;
	protected Set<Card> possibleRooms;
	

	public Player(String name, Color color, BoardCell boardCell) {
		this.playerName = name;
		this.location = boardCell;
		this.color = color;
		seenCards = new HashSet<Card>();
		possiblePeople = new HashSet<Card>();
		possibleWeapons = new HashSet<Card>();
		possibleRooms = new HashSet<Card>();
	}

	//helper functions
	public void addPossibleCards(ArrayList<Card> list) {
		for(Card c: list) {
			// Skips the current card if it is in the player's hand
			if(seenCards.contains(c)) {
				continue;
			}
			
			switch(c.getType()) {
			case PERSON:
				possiblePeople.add(c);
				break;
			case WEAPON:
				possibleWeapons.add(c);
				break;
			case ROOM:
				possibleRooms.add(c);
				break;
			}
		}
	}
	
	public void removePossibleCard(Card c) {
		switch(c.getType()) {
		case PERSON:
			possiblePeople.remove(c);
			break;
		case WEAPON:
			possibleWeapons.remove(c);
			break;
		case ROOM:
			possibleRooms.remove(c);
			break;
		}
	}
	
	//setters and getters
	public String getName() {
		return playerName;
	}
	public void setName(String playerName) {
		this.playerName = playerName;
	}
	public BoardCell getLocation() {
		return location;
	}
	public void setLocation(BoardCell location) {
		this.location = location;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getPlayerType() {
		return "Player";
	}
	
	public Set<Card> showCards() {
		return seenCards;
	}
	public Card getCurrentRoomCard() {
		return Board.getInstance().getRoomCard(location.getInitial());
	} 
	
	
	public void addToSeen(Card delt, boolean initial) {
		seenCards.add(delt);
		
		// Remove the card from the possibilities if this is not the initial deal
		if(!initial)
			removePossibleCard(delt);
	}
	
	//disprove function
	public Card diproveSuggestion(Solution suggestion) {
		return null;
	}
}
