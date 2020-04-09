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

public class Player {
	//instance variables
	private String playerName;
	private BoardCell location;
	private Color color;
	private Set<Card> seenCards; //this is a set of the cards the player has seen
	private Set<Card> cardsInHand; //This is a set of the cards initially delt
	
	//This is done for easy comparison/finding which cards to use in suggestions
	protected Set<Card> possiblePeople;
	protected Set<Card> possibleWeapons;
	protected Set<Card> possibleRooms;
	
	// suggestion used for suggestions and accusations
	protected Solution suggestion;
	

	public Player(String name, Color color, BoardCell boardCell) {
		this.playerName = name;
		this.location = boardCell;
		this.color = color;
		seenCards = new HashSet<Card>();
		cardsInHand = new HashSet<Card>();
		possiblePeople = new HashSet<Card>();
		possibleWeapons = new HashSet<Card>();
		possibleRooms = new HashSet<Card>();
	}

	//helper function: adds cards that are possible solutions into sets
	public void addPossibleCards(ArrayList<Card> list) {
		for(Card c: list) {
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
	
	//removes a seen card from the correct set
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
	public Set<Card> getCardsInHand() {
		return cardsInHand;
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
	
	public Set<Card> showSeenCards() {
		return seenCards;
	}
	public Card getCurrentRoomCard() {
		return Board.getInstance().getRoomCard(location.getInitial());
	} 

	public Solution getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(Solution s) {
		suggestion = s;
	}
	
	
	//adds a card to seen cards and removes it from possible set
	public void addToSeen(Card delt) {
		seenCards.add(delt);			
		removePossibleCard(delt);
	}
	
	//disprove function
	public Card disproveSuggestion(Solution suggestion) {
		// Array of cards that can be used to disprove the suggestion
		ArrayList<Card> disprove = new ArrayList<Card>();
		
		// Loop through cardsInHand and find all that disprove the suggestion
		for(Card possible : cardsInHand) {
			if(suggestion.getPerson().equals(possible)) {
				disprove.add(possible);
				continue;
			} else if(suggestion.getRoom().equals(possible)) {
				disprove.add(possible);
				continue;
			} else if(suggestion.getWeapon().equals(possible)) {
				disprove.add(possible);
				continue;
			}
		}
		
		// Return null if no cards disprove, otherwise return a random card that disproves the suggestion
		if(disprove.size() == 0)
			return null;
		else {
			Random rand = new Random();
			return disprove.get(rand.nextInt(disprove.size()));
		}
	}
	
	//function to add to hand and seen set
	public void addToHand(Card delt) {
		cardsInHand.add(delt);
		addToSeen(delt);
	}

}
