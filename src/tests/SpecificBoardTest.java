package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * @author Brandt Ross
 * @author Liam Morrissey
 *
 * This test class tests that the board is created properly
 */
public class SpecificBoardTest {
	// Constants used for testing the file was loaded correctly at a glance
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 21;
	
	// Creates the board to be tested
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("clueBoard.csv", "roomLegend.txt");
		board.initialize();
	}
	
	// Tests that the correct number of doorways have been loaded (there are 20)
	@Test
	public void testNumDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		}
		Assert.assertEquals(20, numDoors);
	}
	
	@Test
	public void testDimensions() {
		// The board should have 20 rows and 21 columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());	
	}
	
	@Test
	public void testDoors() {
		BoardCell room = board.getCellAt(0, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(2, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(9, 16);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(14, 10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces aren't doors
		room = board.getCellAt(1, 1);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(5, 6);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testLegend() {
		// Get the map of initial => room
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// Test that a few rooms got loaded correctly
		assertEquals("Bedroom", legend.get('B'));
		assertEquals("Shower Room", legend.get('S'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Recreation Room", legend.get('R'));
		assertEquals("Family Room", legend.get('F'));
	}
	
	// Tests that a bunch of rooms are labeled as what they should be
	@Test
	public void testRoomNames() {
		assertEquals('S', board.getCellAt(1, 1).getInitial());
		assertEquals('L', board.getCellAt(8, 2).getInitial());
		assertEquals('R', board.getCellAt(13, 2).getInitial());
		assertEquals('B', board.getCellAt(18, 0).getInitial());
		assertEquals('H', board.getCellAt(1, 10).getInitial());
		assertEquals('X', board.getCellAt(10, 9).getInitial());
		assertEquals('F', board.getCellAt(19, 7).getInitial());
		assertEquals('T', board.getCellAt(0, 19).getInitial());
		assertEquals('D', board.getCellAt(8, 18).getInitial());
		assertEquals('K', board.getCellAt(19, 20).getInitial());
		assertEquals('W', board.getCellAt(19, 16).getInitial());
	}
}
