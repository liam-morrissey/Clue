package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * @author Brandt Ross
 * @author Liam Morrissey
 * 
 * This class handles the board and contains functions that handles calculating where a player can go
 */
public class IntBoard {
	// Array that holds all the cells in the board
	private BoardCell[][] board;
	// Map that has a key of a cell and a value that is the set of all adjacent tiles to that cell
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	// Set that contains all the cells that have been visited in the recursive call
	private Set<BoardCell> visited;
	// Set that contains the list of cells that can be reached
	private Set<BoardCell> targets;

	// Number of rows of the board
	public final int NUM_ROWS = 4;
	// Number of columns of the board
	public final int NUM_COLS = 4;

	public IntBoard() {
		super();
		board = new BoardCell[NUM_ROWS][NUM_COLS];

		// Initializes the board so that every cell has a column and row number
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new BoardCell(i, j);
			}
		}

		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();
	}

	/*
	 * Will go through every cell on the board and create a set of adjacent tiles
	 * This set will be inserted into the map
	 */
	public void calcAdjacencies() {
		// Holds the cells adjacent to the current cell
		Set<BoardCell> tempAdj;

		// Loops through all board spaces
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				tempAdj = new HashSet<BoardCell>();

				// Check if the position above is valid
				if(i > 0) {
					tempAdj.add(board[i - 1][j]);
				}

				// Check if the position below is valid
				if(i < NUM_ROWS - 1) {
					tempAdj.add(board[i + 1][j]);
				}

				// Check if the position to the left is valid
				if(j > 0) {
					tempAdj.add(board[i][j - 1]);
				}

				// Check if the position to the right is valid
				if(j < NUM_COLS - 1) {
					tempAdj.add(board[i][j + 1]);
				}

				adjacencies.put(board[i][j], tempAdj);
			}
		}
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjacencies.get(cell);
	}

	// Sets up for recursive call to find targets
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	// Recursive function that finds targets
	private void findAllTargets(BoardCell startCell, int pathLength) {
		// Loops through adjacent cells
		for(BoardCell adjCell : getAdjList(startCell)) {
			if(!visited.contains(adjCell)) {
				visited.add(adjCell);
				// Adds the adjacent cell to targets if the path length is 1 otherwise, recursively call find all targets with adjacent cells
				if(pathLength == 1) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public BoardCell getCell(int i, int j) {
		return board[i][j];
	}
}
