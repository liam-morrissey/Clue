package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.BoardCell;

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
		ComputerPlayer testPlayer = new ComputerPlayer(null, null, board.getCellAt(5, 6));
		BoardCell targetCell = null;
		Set<BoardCell> targetCellSet = new HashSet<BoardCell>();
		
		// Tests that the target selected can be reached when all targets are walkways and that more than one cell is being visited
		board.calcTargets(board.getCellAt(5, 6), 2);
		for(int i = 0; i < 20; i++) {
			targetCell = testPlayer.pickLocation(board.getTargets());
			assertTrue(board.getTargets().contains(targetCell));
			targetCellSet.add(targetCell);
		}
		assertTrue(targetCellSet.size() > 1);
		
		// Tests that the player will only go into the doorway
		board.calcTargets(board.getCellAt(4, 3), 2);
		for(int i = 0; i < 20; i++) {
			targetCell = testPlayer.pickLocation(board.getTargets());
			assertEquals(targetCell, board.getCellAt(2, 3));
		}
		
		// Tests that the player will treat all rooms equally when the room was previously visited
		board.calcTargets(board.getCellAt(4, 3), 2);
		testPlayer.setPrevRoom(board.getCellAt(2, 3));
		targetCellSet.clear();
		for(int i = 0; i < 20; i++) {
			targetCell = testPlayer.pickLocation(board.getTargets());
			assertTrue(board.getTargets().contains(targetCell));
			targetCellSet.add(targetCell);
		}
		assertTrue(targetCellSet.size() > 1);
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
	@Test
	public void testCreateSuggestion1() {
		ComputerPlayer cp = new ComputerPlayer("cpu", Color.GREEN, board.getCellAt(17,3));
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		possibleCards.add(new Card("Knife", CardType.WEAPON));
		possibleCards.add(new Card("Mrs. Scarlet", CardType.PERSON));
		cp.addPossibleCards(possibleCards);
		cp.createSuggestion();
		assertTrue("Bedroom".equals(cp.getSuggestion().getRoom().getName()));
		assertTrue("Knife".equals(cp.getSuggestion().getWeapon().getName()));
		assertTrue("Mrs. Scarlet".equals(cp.getSuggestion().getPerson().getName()));
	}
	
	//Create suggestion for multiple people and weapons
	@Test
	public void testCreateSuggestion2() {
		ComputerPlayer cp = new ComputerPlayer("cpu", Color.GREEN, board.getCellAt(17,3));
		// Populate a fake deck of seen and unseen cards
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		possibleCards.add(new Card("Knife", CardType.WEAPON));
		possibleCards.add(new Card("Mrs. Scarlet", CardType.PERSON));
		possibleCards.add(new Card("Candlestick", CardType.WEAPON));
		possibleCards.add(new Card("Someone", CardType.PERSON));
		cp.addToSeen(new Card("Revolver", CardType.WEAPON), true);
		cp.addToSeen(new Card("Lead Pipe", CardType.WEAPON), true);
		cp.addToSeen(new Card("Mr. Green", CardType.PERSON), true);
		cp.addToSeen(new Card("Mr. Orange", CardType.PERSON), true);
		cp.addPossibleCards(possibleCards);
		
		cp.createSuggestion();
		System.out.println(cp.getSuggestion());
		assertEquals("Bedroom", cp.getSuggestion().getRoom().getName());
		assertTrue(!cp.showCards().contains(cp.getSuggestion().getWeapon().getName()));
		assertTrue(!cp.showCards().contains(cp.getSuggestion().getPerson().getName()));
	}
}
