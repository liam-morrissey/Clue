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
import clueGame.HumanPlayer;
import clueGame.Player;
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
		// Create a test player and a fake hand
		ComputerPlayer cp = new ComputerPlayer("cpu", Color.GREEN, board.getCellAt(17,3));
		cp.addToHand(new Card("Revolver", CardType.WEAPON));
		cp.addToHand(new Card("Mr. Green", CardType.PERSON));
		cp.addToHand(new Card("Bedroom", CardType.ROOM));
		
		// Create a suggestion that the player has all the cards for and tests random selection
		Solution testSuggestion = new Solution(new Card("Mr. Green", CardType.PERSON), 
											   new Card("Bedroom", CardType.ROOM),
											   new Card("Revolver", CardType.WEAPON));
		Set<Card> disproven = new HashSet<Card>();
		for(int i = 0; i < 20; i++) {
			disproven.add(cp.disproveSuggestion(testSuggestion));
		}
		assertTrue(disproven.size() > 1);
		
		// Create a suggestion that the player has one of and tests that only that card is selected
		testSuggestion = new Solution(new Card("x", CardType.PERSON), 
									  new Card("Bedroom", CardType.ROOM),
									  new Card("x", CardType.WEAPON));
		disproven.clear();
		for(int i = 0; i < 20; i++) {
			disproven.add(cp.disproveSuggestion(testSuggestion));
		}
		assertTrue(disproven.size() == 1);
		
		// Create a suggestion that the player has none one of and tests that null is returned
		testSuggestion = new Solution(new Card("x", CardType.PERSON), 
									  new Card("x", CardType.ROOM),
									  new Card("x", CardType.WEAPON));
		for(int i = 0; i < 20; i++) {
			assertEquals(cp.disproveSuggestion(testSuggestion), null);
		}
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
		cp.addToHand(new Card("Revolver", CardType.WEAPON));
		cp.addToHand(new Card("Lead Pipe", CardType.WEAPON));
		cp.addToHand(new Card("Mr. Green", CardType.PERSON));
		cp.addToHand(new Card("Mr. Orange", CardType.PERSON));
		cp.addPossibleCards(possibleCards);
		
		cp.createSuggestion();
		assertEquals("Bedroom", cp.getSuggestion().getRoom().getName());
		assertTrue(!cp.showSeenCards().contains(cp.getSuggestion().getWeapon().getName()));
		assertTrue(!cp.showSeenCards().contains(cp.getSuggestion().getPerson().getName()));
	}
	
	@Test
	public void testHandleSuggestion() {
		Player cp1 = new ComputerPlayer("cpu1", Color.GREEN, board.getCellAt(17,3));
		Player cp2 = new ComputerPlayer("cpu2", Color.WHITE, board.getCellAt(15,3));
		Player hum = new HumanPlayer("human", Color.BLUE, board.getCellAt(1,3));
		ArrayList<Player> players = board.getPlayers();
		players.clear();
		players.add(cp1);
		players.add(cp2);
		players.add(hum);
		ArrayList<Card> deck = board.getDeck();
		deck.clear();
		deck.add(new Card("Mrs. Scarlet", CardType.PERSON)); //0
		deck.add(new Card("Someone", CardType.PERSON)); //1
		deck.add(new Card("SomeoneElse", CardType.PERSON));//2
		deck.add(new Card("SomeoneDifferent", CardType.PERSON));//3
		
		deck.add(new Card("Candlestick", CardType.WEAPON));//4
		deck.add(new Card("Pointy object", CardType.WEAPON));//5
		deck.add(new Card("Mace", CardType.WEAPON));//6
		deck.add(new Card("Gun", CardType.WEAPON));//7
		
		deck.add(new Card("Bedroom", CardType.ROOM));//8
		deck.add(new Card("Study", CardType.ROOM));//9
		deck.add(new Card("Bathroom", CardType.ROOM));//10
		deck.add(new Card("Kitchen", CardType.ROOM));//11
		
		cp1.addPossibleCards(deck);
		cp2.addPossibleCards(deck);
		hum.addPossibleCards(deck);
		
		cp1.addToHand(deck.get(1));
		cp1.addToHand(deck.get(5));
		cp1.addToHand(deck.get(9));
		
		cp2.addToHand(deck.get(2));
		cp2.addToHand(deck.get(6));
		cp2.addToHand(deck.get(10));
		
		hum.addToHand(deck.get(3));
		hum.addToHand(deck.get(7));
		hum.addToHand(deck.get(11));
		
		
		
		
		//suggestion no one can disprove
		cp1.setSuggestion(new Solution(deck.get(0),deck.get(4),deck.get(8)));
		assertEquals(null, board.handleSuggestion(cp1));
		//suggestion that accusing player can disprove
		cp1.setSuggestion(new Solution(deck.get(1),deck.get(4),deck.get(8)));
		assertEquals(null, board.handleSuggestion(cp1));
		//suggestion that only human can disprove returns disproving card
		cp1.setSuggestion(new Solution(deck.get(3),deck.get(4),deck.get(8)));
		assertEquals(hum.disproveSuggestion(cp1.getSuggestion()),board.handleSuggestion(cp1));
		//two players can disprove, selects the correct one (human has the other card)
		cp1.setSuggestion(new Solution(deck.get(2),deck.get(7),deck.get(8)));
		assertEquals(cp2.disproveSuggestion(cp1.getSuggestion()),board.handleSuggestion(cp1));
		
		
	}
}
