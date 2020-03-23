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

//setters and getters
public String getPlayerName() {
	return playerName;
}
public void setPlayerName(String playerName) {
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


//disprove function
public Card diproveSuggestion(Solution suggestion) {
	return null;
}




}
