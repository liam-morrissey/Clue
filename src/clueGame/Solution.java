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
	
	public Solution(Card p, Card r, Card w) {
		this.person = p;
		this.room = r;
		this.weapon = w;
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
	
	public String toString() {
		return "Person: " + person.getName() + " Room: " + room.getName() + " Weapon: " + weapon.getName();
	}
}
