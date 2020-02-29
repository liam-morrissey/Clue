package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * 
 * @author Brandt Ross
 * @author Liam Morrissey
 *
 */
public class SpecificAdjTest {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Get the only instance of board
		board = Board.getInstance();
		// Set the config file names
		board.setConfigFiles("clueBoard.csv", "roomLegend.txt");		
		// Load the config files
		board.initialize();
	}
	
	// Adjacency test for a cell that is adjacent only to walkways
	// Cells in this test are light yellow
	@Test
	public void walkwayAdj() {
		Set<BoardCell> testSet = board.getAdjList(4, 4);
		assertEquals(4, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(4, 3)));
		assertTrue(testSet.contains(board.getCellAt(3, 4)));
		assertTrue(testSet.contains(board.getCellAt(4, 5)));
		assertTrue(testSet.contains(board.getCellAt(5, 4)));
	}
	
	// Adjacency test for a cell that is in a room
	// Cells in this test are white
	@Test
	public void inRoomAdj() {
		Set<BoardCell> testSet = board.getAdjList(0, 1);
		assertEquals(0, testSet.size());
		
		testSet = board.getAdjList(1, 10);
		assertEquals(0, testSet.size());
	}
	
	// Adjacency test for a cell that is on the edge
	// Cells in this test are green
	@Test
	public void edgeAdj() {
		Set<BoardCell> testSet = board.getAdjList(4, 0);
		assertEquals(3, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(3, 0)));
		assertTrue(testSet.contains(board.getCellAt(5, 0)));
		assertTrue(testSet.contains(board.getCellAt(4, 1)));
		
		testSet = board.getAdjList(19, 5);
		assertEquals(3, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(19, 4)));
		assertTrue(testSet.contains(board.getCellAt(19, 6)));
		assertTrue(testSet.contains(board.getCellAt(18, 5)));
	}
	
	// Adjacency test for a cell that is next to a room cell that isn't a door
	// Cells in this test are orange
	@Test
	public void nextToRoomAdj() {
		Set<BoardCell> testSet = board.getAdjList(4, 14);
		assertEquals(2, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(5, 14)));
		assertTrue(testSet.contains(board.getCellAt(4, 15)));
		
		testSet = board.getAdjList(6, 17);
		assertEquals(2, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(6, 16)));
		assertTrue(testSet.contains(board.getCellAt(6, 18)));
		assertTrue(testSet.contains(board.getCellAt(5, 17)));
	}
	
	// Adjacency test for a cell that is next to a door
	// Cells tested are magenta
	@Test
	public void doorAdj() {
		// Tests a down door
		Set<BoardCell> testSet = board.getAdjList(3, 3);
		assertEquals(4, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(2, 3)));
		
		// Tests an up door
		testSet = board.getAdjList(5, 1);
		assertEquals(4, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(6, 1)));
		
		// Tests a right door
		testSet = board.getAdjList(17, 4);
		assertEquals(4, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(17, 3)));
		
		// Tests a left door
		testSet = board.getAdjList(17, 6);
		assertEquals(4, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(17, 7)));
		
		// Test that the corner door can't be entered from the wrong way
		testSet = board.getAdjList(16, 3);
		assertEquals(2, testSet.size());
		assertFalse(testSet.contains(board.getCellAt(17, 3)));
	}
	
	// Adjacency test for cells that are doors
	// Cells for this test are black with white letters
	@Test
	public void areDoorAdj() {
		// Tests a down door
		Set<BoardCell> testSet = board.getAdjList(5, 10);
		assertEquals(1, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(6, 10)));
		
		// Tests an up door
		testSet = board.getAdjList(14, 10);
		assertEquals(1, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(13, 10)));
		
		// Tests a right door
		testSet = board.getAdjList(0, 4);
		assertEquals(1, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(0, 5)));
		
		// Tests a left door
		testSet = board.getAdjList(9, 16);
		assertEquals(1, testSet.size());
		assertTrue(testSet.contains(board.getCellAt(9, 15)));
	}
	
	// Target test for cells that are on walkways with pathlength of 1
	@Test
	public void walkwayTargetsOneStep() {
		board.calcTargets(5, 6, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
	}
	
	// Target test for cells that are on walkways with pathlength of 2
	public void walkwayTargetsTwoStep() {
		board.calcTargets(7, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(9, 14)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		assertTrue(targets.contains(board.getCellAt(6, 13)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));
	}
	
	// Target test for cells that are on walkways with pathlength of 3
	public void walkwayTargetsThreeStep() {
		board.calcTargets(10, 14, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 14)));
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(12, 13)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(9, 14)));
		assertTrue(targets.contains(board.getCellAt(10, 15)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		assertTrue(targets.contains(board.getCellAt(10, 13)));
	}
	
	// Target test for cells that are on walkways with pathlength of 4
	public void walkwayTargetsFourStep() {
		board.calcTargets(13, 15, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 15)));
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(13, 19)));
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 11)));
		assertTrue(targets.contains(board.getCellAt(11, 13)));
		assertTrue(targets.contains(board.getCellAt(10, 14)));
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(12, 16)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(15, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));
	}
	
	// Target test for cells that allow for room entrance
	@Test
	public void toRoomTargets() {
		board.calcTargets(8, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(8, 16)));
		
		board.calcTargets(12, 19, 2);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(14, 19)));
	}
	
	// Target test for cells that are outside a room the player is in
	@Test
	public void fromRoomTargets() {
		board.calcTargets(18, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		
		board.calcTargets(18, 7, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(19, 6)));
	}
}
