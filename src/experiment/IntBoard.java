package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public final int NUM_ROWS = 4;
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
	
	public void calcAdjacencies() {
		// Holds the cells adjacent to the current cell
		Set<BoardCell> tempAdj = new HashSet<BoardCell>();
		
		// Loops through all board spaces
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				tempAdj.clear();
				
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
				
				// System.out.println(board[i][j] + " " + tempAdj);
			}
		}
		
		/*for(BoardCell x : adjacencies.keySet()) {
			System.out.println(x + " " + adjacencies.get(x));
		}*/
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjacencies.get(cell);
	}
	
	// Recursive function to find where on the board the player can move to
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}

	public BoardCell getCell(int i, int j) {
		return board[i][j];
	}
}
