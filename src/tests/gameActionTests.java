package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	static Board board;
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("clueBoard.csv", "roomLegend.txt", "player.txt", "weapons.txt");
		board.initialize();
	}
	
	@Test
	public void testSelectTargetLocation() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAccusationTest() {
		Solution answer = board.getSolution();
		// It won't matter the name of the wrong cards inputted
		Solution correct = new Solution(answer.getPerson(), answer.getRoom(), answer.getWeapon());
		Solution wrongPerson = new Solution(new Card("", CardType.PERSON), answer.getRoom(), answer.getWeapon());
		Solution wrongWeapon = new Solution(answer.getPerson(), answer.getRoom(), new Card("", CardType.WEAPON));
		Solution wrongRoom = new Solution(answer.getPerson(), new Card("", CardType.ROOM), answer.getWeapon());
		
		assertTrue(board.checkAccusation(correct));
		assertTrue(board.checkAccusation(wrongPerson));
		assertTrue(board.checkAccusation(wrongWeapon));
		assertTrue(board.checkAccusation(wrongRoom));
	}
	
	@Test
	public void testDisproveSuggestion() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCreateSuggestion() {
		fail("Not yet implemented");
	}
}
