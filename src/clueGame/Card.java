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

	public String getName() {
		return cardName;
	}

	public boolean equals(Card compTo) {
		return this.cardName.equals(compTo.cardName) && this.type == compTo.type;
	}

	public String toString() {
		return this.cardName + " " + this.type;
	}
}
