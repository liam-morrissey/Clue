package clueGame;
/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class Solution {
	//public instance vars
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution() {
		this.person = null;
		this.room = null;
		this.weapon = null;
	}
	
	public Card getPerson() {
		return person;
	}
	
	public Card getRoom() {
		return room;
	}
	
	public Card getWeapon() {
		return weapon;
	}
	
	public void setPerson(Card p) {
		person = p;
	}
	
	public void setRoom(Card r) {
		room = r;
	}

	public void setWeapon(Card w) {
		weapon = w;
	}
}
