package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	static Board board;
	ComputerPlayer cp;
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
		assertTrue(!board.checkAccusation(wrongPerson));
		assertTrue(!board.checkAccusation(wrongWeapon));
		assertTrue(!board.checkAccusation(wrongRoom));
	}
	
	@Test
	public void testDisproveSuggestion() {
		fail("Not yet implemented");
	}
	
	
	//Create suggestion for one weapon test and one person
	@Before
	public void createSuggestionSetup1() {
		cp = new ComputerPlayer("cpu", Color.GREEN, board.getCellAt(17,3));
	}
	@Test
	public void testCreateSuggestion1() {
		assertEquals("Bedroom", cp.getRoom());
		assertEquals("Knife", cp.createSuggestion().getWeapon());
		assertEquals("Mrs. Scarlet", cp.createSuggestion().getPerson());
		
	}
	
	//Create suggestion for multiple people and weapons
		@Before
		public void createSuggestionSetup2() {
			cp = new ComputerPlayer("cpu", Color.GREEN, board.getCellAt(17,3));
		}
		@Test
		public void testCreateSuggestion2() {
			assertEquals("Bedroom", cp.getRoom());
			assertTrue(!cp.showCards().contains( cp.createSuggestion().getWeapon()));
			assertTrue(!cp.showCards().contains(cp.createSuggestion().getPerson()));
			
		}
}
