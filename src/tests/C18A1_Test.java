package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import clueGame.Board;
import clueGame.Player;
import org.junit.BeforeClass;
import org.junit.Test;

public class C18A1_Test {
	static Board board;
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("clueBoard.csv", "roomLegend.txt", "player.txt");
		board.initialize();
	}
	
	
	//Test loadPlayerConfig
	@Test
	public void testLoadPlayer1() {
		assertEquals(board.getPlayer(0).getName(), "Mr. Green");
		assertEquals(board.getPlayer(0).getColor(), Color.green);
		assertEquals(board.getPlayer(0).getPlayerType(), "Computer");
		assertEquals(board.getPlayer(0).getLocation(), board.getCellAt(0,1 ));
	}
	@Test
	public void testLoadPlayer3() {
		assertEquals(board.getPlayer(2).getName(), "Mr. White");
		assertEquals(board.getPlayer(2).getColor(), Color.white);
		assertEquals(board.getPlayer(2).getPlayerType(), "Human");
		assertEquals(board.getPlayer(2).getLocation(), board.getCellAt(3,4 ));
	}
	@Test
	public void testLoadPlayerFinal() {
		assertEquals(board.getPlayer(5).getName(), "Mrs. Scarlet");
		assertEquals(board.getPlayer(5).getColor(), Color.red);
		assertEquals(board.getPlayer(5).getPlayerType(), "Computer");
		assertEquals(board.getPlayer(5).getLocation(), board.getCellAt(5,6 ));
	
	}
	
	
	//Test Loading the Deck
	

}
