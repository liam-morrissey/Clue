package clueGame;

import java.awt.Color;
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

	public Player(String name, Color color, BoardCell boardCell) {
		this.playerName = name;
		this.location = boardCell;
		this.color = color;
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
	
	public void addToSeen(Card delt) {
		seenCards.add(delt);
	}
	
	//disprove function
	public Card diproveSuggestion(Solution suggestion) {
		return null;
	}
}
