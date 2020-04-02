package clueGame;
/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class Card {

	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public CardType getType() {
		return type;
	}
	
	public boolean equals() {
		return false;
	}
	
	public String toString() {
		return this.cardName + " " + this.type;
	}
}
