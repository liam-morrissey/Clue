package clueGame;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
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
	// Initialization variables
	public final int MAX_BOARD_SIZE = 50;
	public final int NUM_PLAYERS = 6;
	public final int NUM_WEAPONS = 6;
	
	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<Card> deck;
	private Map<Character, String> legend;
	private int numRows;
	private int numCols;
	
	private String csvFile;
	private String legendFile;
	private String playerFile;
	private String weaponFile;
	
	private Solution theAnswer;
	
	private Player[] players = new Player[NUM_PLAYERS];
	private String[] weapons = new String[NUM_WEAPONS];

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		theInstance.initializeMemory();
		return theInstance;
	}

	public void initialize() {
		initializeMemory();
		
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			loadPlayerConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
		
	}

	private void initializeMemory() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		
		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		deck = new HashSet<Card>();
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

	public void setConfigFiles(String csvFile, String legendFile, String playerFile, String weaponFile) {
		this.csvFile = csvFile;
		this.legendFile = legendFile;
		this.playerFile = playerFile;
		this.weaponFile = weaponFile;
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
	
	public Player getPlayer(int i) {
		if(i<players.length && i>=0) return players[i];
		else return null;
	}
	
	public String getWeapon(int i) {
		if(i < weapons.length && i >= 0) return weapons[i];
		return null;
	}
	
	/**
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 * 
	 * Each line of the config file contains a weapon
	 */
	/*public void loadWeaponConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader file;
		
		String currentLine;
		file = new FileReader(weaponFile);
		Scanner in = new Scanner(file);
		
		int i = 0;
		while(in.hasNext()) {
			if(i == NUM_WEAPONS) throw new BadConfigFormatException("Too many weapons");
			currentLine = in.nextLine();
			weapons[i] = currentLine;
			i++;
		}
	}*/
	
	/**
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 * 
	 * Format is: Name, Color, row, column, Human or Computer
	 */
	public void loadPlayerConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader file;
		
		String currentLine;
		file = new FileReader(playerFile);
		Scanner in = new Scanner(file);
		
		// Loop through lines and add them to the array
		int i=0;
		while(in.hasNext()) {
			currentLine = in.nextLine();
			String arr[] = currentLine.split(",");
			String name = arr[0].trim();
			Color color = convertColor(arr[1]);
			//Add error catching for out of bounds later
			BoardCell boardCell = getCellAt(Integer.parseInt(arr[2].trim()),Integer.parseInt(arr[3].trim()));
			if(arr[4].trim().equalsIgnoreCase("Computer"))
			players[i] = new ComputerPlayer(name,color, boardCell);
			else if (arr[4].trim().equalsIgnoreCase("Human"))
			players[i] = new HumanPlayer(name,color, boardCell);
			else throw new BadConfigFormatException("Neither Computer nor Human Player");
			i++;
		}
		
		in.close();
	}
	
	
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		FileReader file;
		
		
		String currentLine;
		file = new FileReader(legendFile);
		Scanner in = new Scanner(file);
		
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
	
	public void selectAnswer() {}

	public Card handleSuggestion() {
		return null;
	}

	public boolean checkAccusation(Solution accusation) {
		return false;
	}
	
	//color converter from stack overflow
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
		}
	
}
