package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
//Authors: Liam Morrissey and Brandt Ross
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.Scanner;

public class Board {
	// Array that holds all the cells in the board
	private BoardCell[][] board;
	// Map that has a key of a cell and a value that is the set of all adjacent tiles to that cell
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	// Set that contains all the cells that have been visited in the recursive call
	private Set<BoardCell> visited;
	// Set that contains the list of cells that can be reached
	private Set<BoardCell> targets;
	// Legend that says what each character is
	private Map<Character, String> legend;
	// Number of rows of the board
	private int numRows;
	// Number of columns of the board
	private int numCols;
	// Csv file for the board
	private String csvFile;
	// Lengend file for the board
	private String legendFile;

	private final int MAX_BOARD_SIZE = 50;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
		numRows = MAX_BOARD_SIZE;
		numCols = MAX_BOARD_SIZE;
		board = new BoardCell[numRows][numCols];

		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		calcAdjacencies();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * Will go through every cell on the board and create a set of adjacent tiles
	 * This set will be inserted into the map
	 */
	public void calcAdjacencies() {
		// Holds the cells adjacent to the current cell
		Set<BoardCell> tempAdj;

		// Loops through all board spaces
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				tempAdj = new HashSet<BoardCell>();

				// Check if the position above is valid
				if(i > 0) {
					tempAdj.add(board[i - 1][j]);
				}

				// Check if the position below is valid
				if(i < numRows - 1) {
					tempAdj.add(board[i + 1][j]);
				}

				// Check if the position to the left is valid
				if(j > 0) {
					tempAdj.add(board[i][j - 1]);
				}

				// Check if the position to the right is valid
				if(j < numCols - 1) {
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

	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

	public void setConfigFiles(String csvFile, String legendFile) {
		this.csvFile = csvFile;
		this.legendFile = legendFile;
	}

	public void initialize() {
		FileReader file;
		try {
			file = new FileReader(csvFile);
			Scanner in = new Scanner(file);
			String temp;
			// Keeps track of which row we are on
			int i = 0;
			// Goes through each value and initializes the board array with locations and its character
			while(in.hasNext()) {
				temp = in.next();
				String arr[] = temp.split(",");
				numCols = arr.length;
				for(int j = 0; j < arr.length; j++) {
					board[i][j] = new BoardCell(i, j, arr[j]);
				}
				i++;
			}
			numRows = i;
			
			file = new FileReader(legendFile);
			in = new Scanner(file);
			// Loop through lines and add them to the legend
			while(in.hasNext()) {
				temp = in.nextLine();
				String arr[] = temp.split(", ");
				legend.put(arr[0].charAt(0), arr[1]);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Files not found");
		}
		
		
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}
}
