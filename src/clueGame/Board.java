package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
//Authors: Liam Morrissey and Brandt Ross
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.Scanner;

/**
 * 
 * @author Brandt Ross
 * @author Liam Morrissey
 *
 */
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
	private int numRows;
	private int numCols;
	// Csv file for the board
	private String csvFile;
	private String legendFile;

	private final int MAX_BOARD_SIZE = 50;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {
		//numRows = MAX_BOARD_SIZE;
		//numCols = MAX_BOARD_SIZE;
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
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
				BoardCell currentCell = board[i][j];
				tempAdj = new HashSet<BoardCell>();
				
				//If the boardcell is neither a doorway nor a walkway
				if(!currentCell.isDoorway() && !currentCell.isWalkway()) {
					adjacencies.put(currentCell,tempAdj);
				}
				
				//If the board cell is a doorway, only add the boardcell in direction the door opens
				else if(currentCell.isDoorway()) {
					switch(currentCell.getDoorDirection()) {
					case UP:
						tempAdj.add(board[i-1][j]);
						adjacencies.put(currentCell,tempAdj);
						break;
					case DOWN:
						tempAdj.add(board[i+1][j]);
						adjacencies.put(currentCell,tempAdj);
						break;
					case LEFT:
						tempAdj.add(board[i][j-1]);
						adjacencies.put(currentCell,tempAdj);
						break;
					case RIGHT:
						tempAdj.add(board[i][j+1]);
						adjacencies.put(currentCell,tempAdj);
						break;	
					}
				}
				
				else {
					// Check if the position above is valid
					if(i > 0 && (board[i-1][j].isWalkway() || board[i-1][j].getDoorDirection() == DoorDirection.DOWN)) {
						tempAdj.add(board[i - 1][j]);
					}
	
					// Check if the position below is valid
					if(i < numRows - 1 && (board[i+1][j].isWalkway() || board[i+1][j].getDoorDirection() == DoorDirection.UP)) {
						tempAdj.add(board[i + 1][j]);
					}
	
					// Check if the position to the left is valid
					if(j > 0 && (board[i][j-1].isWalkway() || board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)) {
						tempAdj.add(board[i][j - 1]);
					}
	
					// Check if the position to the right is valid
					if(j < numCols - 1 && (board[i][j+1].isWalkway() || board[i][j+1].getDoorDirection() == DoorDirection.LEFT)) {
						tempAdj.add(board[i][j + 1]);
					}
	
					adjacencies.put(currentCell, tempAdj);
				}
			}
		}
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjacencies.get(cell);
	}

	
	// Some tests require calcTargets to take in a boardcell and some take in 3 ints
	public void calcTargets(int startI, int startJ, int pathLength) {
		calcTargets(board[startI][startJ], pathLength);
	}
	
	// Sets up for recursive call to find targets
	public void calcTargets(BoardCell startCell, int pathLength) {
		//makes sure previous target selection is cleared
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	// Recursive function that finds targets
	private void findAllTargets(BoardCell startCell, int pathLength) {
		if(getAdjList(startCell) == null) {
			return;
		}
		
		// Loops through adjacent cells
		for(BoardCell adjCell : getAdjList(startCell)) {
			if(!visited.contains(adjCell)) {
				visited.add(adjCell);
				
				// Adds the adjacent cell to targets if the path length is 1 otherwise, recursively call find all targets with adjacent cells
				if(pathLength == 1) {
					targets.add(adjCell);
				}else if(adjCell.isDoorway()){
					targets.add(adjCell);
				}else {
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
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
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
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		FileReader file;
		
		file = new FileReader(csvFile);
		Scanner in = new Scanner(file);
		String currentLine;
		file = new FileReader(legendFile);
		in = new Scanner(file);
		
		// Loop through lines and add them to the legend
		while(in.hasNext()) {
			currentLine = in.nextLine();
			String arr[] = currentLine.split(", ");
			if(arr.length <3) throw new BadConfigFormatException();
			if(!arr[2].equals("Card") && !arr[2].equals("Other"))throw new BadConfigFormatException("Invalid Type of Room");
			legend.put(arr[0].charAt(0), arr[1]); 
		}
		
		in.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader file;
		
		file = new FileReader(csvFile);
		Scanner in = new Scanner(file);
		String currLine;
		
		// Keeps track of which row we are on
		int i = 0;
		
		// Goes through each value and initializes the board array with locations and its character
		currLine = in.next();
		String arr[] = currLine.split(",");
		numCols = arr.length;
		
		while(in.hasNext()) {
			for(int j = 0; j < arr.length; j++) {
				if(!legend.containsKey(arr[j].charAt(0))) throw new BadConfigFormatException("Board element not in legend");
				board[i][j] = new BoardCell(i, j, arr[j]);
			}
			
			currLine = in.next();
			arr = currLine.split(",");
			if(arr.length != numCols) throw new BadConfigFormatException("Incorrect number of Columns");
			i++;
		}
		
		// Reads in the last row
		for(int j = 0; j < arr.length; j++) {
			if(!legend.containsKey(arr[j].charAt(0))) throw new BadConfigFormatException("Board element not in legend");
			board[i][j] = new BoardCell(i, j, arr[j]);
		}
		
		//There was an off by one error with the number of rows
		i++;
		numRows = i;
		in.close();
	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjacencies.get(board[i][j]);
	}
}
