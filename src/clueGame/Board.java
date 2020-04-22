package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
//Authors: Liam Morrissey and Brandt Ross
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.ControlPanel;
import gui.GuessDialog;

import java.util.Scanner;

/**
 * 
 * @author Brandt Ross
 * @author Liam Morrissey
 *
 */
public class Board extends JPanel{
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_PLAYERS = 6;

	// Initialization variables
	public static final int MAX_BOARD_SIZE = 50;

	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private ArrayList<Card> deck;
	private Map<Character, String> legend;
	private Map<Character, Card> cardLegend;
	private int numRows;
	private int numCols;

	private String csvFile;
	private String legendFile;
	private String playerFile;
	private String weaponFile;

	private Boolean drawTargets = false;
	private boolean playerMoved = true;

	private Solution theAnswer;

	private ArrayList<String> weapons = new ArrayList<String>();
	private ArrayList<Player> players = new ArrayList<Player>();

	private int playerTurn;

	private ControlPanel cPanel;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		initializeMemory();

		calcAdjacencies();

		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
		addMouseListener(new MListener());
		dealDeck();

	}

	private void initializeMemory() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		cardLegend = new HashMap<Character, Card>();
		deck = new ArrayList<Card>();
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
		visited.clear();
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
		if(i<players.size() && i>=0) return players.get(i);
		else return null;
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public void setFirstTurn(int turn) {
		//because a next turn action triggers the start of the game, the turn needs to be -1
		playerTurn = (turn-1)%players.size();
	}

	//function to move to next turn
	public Player nextTurn() {
		playerTurn = ++playerTurn%players.size();
		return players.get(playerTurn);
	}

	public void setPlayerHasMoved(boolean b) {
		playerMoved = b;
	}

	public boolean getPlayerHasMoved() {
		return playerMoved;
	}

	public ArrayList<String> getWeaponList() {
		return weapons;
	}

	public String getWeapon(int i) {
		if(i < weapons.size() && i >= 0) return weapons.get(i);
		return null;
	}

	public boolean inDeck(Card in) {
		boolean contain = false;

		for(Card i : deck) {
			if(i.toString().equals(in.toString()))
				contain = true;
		}

		return contain;
	}

	public ArrayList<Card> getDeck(){
		return deck;
	}

	public Card getRoomCard(char c) {
		return cardLegend.get(c);
	}

	public String getRoomName(char c) {
		return legend.get(c);
	}

	public Card getCard(String s) {
		for(Card c: deck) {
			if(c.getName()==s)
				return c;
		}
		return null;
	}

	/**
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 * 
	 * Each line of the config file contains a weapon
	 */
	public void loadWeaponConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader file;

		String currentLine;
		file = new FileReader(weaponFile);
		Scanner in = new Scanner(file);

		int i = 0;
		while(in.hasNext()) {
			if(i == NUM_WEAPONS) throw new BadConfigFormatException("Too many weapons");
			currentLine = in.nextLine();
			weapons.add(currentLine);
			deck.add(new Card(currentLine, CardType.WEAPON));
			i++;
		}
	}

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
				players.add(new ComputerPlayer(name,color, boardCell));
			else if (arr[4].trim().equalsIgnoreCase("Human"))
				players.add(new HumanPlayer(name,color, boardCell));
			else throw new BadConfigFormatException("Neither Computer nor Human Player");
			deck.add(new Card(name, CardType.PERSON));
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
			Card room = null;
			if(arr.length <3) throw new BadConfigFormatException();
			if(!arr[2].equals("Card") && !arr[2].equals("Other"))throw new BadConfigFormatException("Invalid Type of Room");
			if(arr[2].equals("Card")) {
				room = new Card(arr[1], CardType.ROOM);
				deck.add(room);
			}
			legend.put(arr[0].charAt(0), arr[1]); 
			cardLegend.put(arr[0].charAt(0),room);
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

	public Card handleSuggestion(Player p) {
		Solution currentSuggestion = p.getSuggestion();
		int playerNum = players.indexOf(p);
		for(int i=1; i<players.size();i++) {
			Player currentPlayer = players.get((playerNum+i)%players.size()); //Player that is checking to disprove suggestion
			if(currentPlayer.disproveSuggestion(currentSuggestion)!= null) return currentPlayer.disproveSuggestion(currentSuggestion);
		}
		return null;
	}

	public boolean checkAccusation(Solution accusation) {
		return theAnswer.getPerson() == accusation.getPerson() && theAnswer.getRoom() == accusation.getRoom() && theAnswer.getWeapon() == accusation.getWeapon();
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

	public Solution getSolution() {
		return theAnswer;
	}

	/**
	 * Shuffles the deck and then deals it to the players
	 */
	private void dealDeck() {
		Collections.shuffle(deck);
		theAnswer = new Solution();

		// Populate the possible sets
		for(Player currPlayer : players) {
			currPlayer.addPossibleCards(deck);
		}

		int dealTo = 0;
		for(Card i : deck) {
			// These will deal the first Person Room and Weapon to the solution then will deal the rest to the players
			if(theAnswer.getPerson() == null && i.getType() == CardType.PERSON) {
				theAnswer.setPerson(i);
				continue;
			} 

			if(theAnswer.getRoom() == null && i.getType() == CardType.ROOM) {
				theAnswer.setRoom(i);
				continue;
			}

			if(theAnswer.getWeapon() == null && i.getType() == CardType.WEAPON) {
				theAnswer.setWeapon(i);
				continue;
			}

			if(dealTo == NUM_PLAYERS)
				dealTo = 0;
			players.get(dealTo).addToHand(i);
			dealTo++;
		}
	}

	public void setDrawTargets(Boolean set) {
		drawTargets = set;
	}

	/**
	 * This iterates through the boardcells and players and calls their draw functions
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0; i< numRows;i++) {
			for(int j=0; j< numCols; j++) {
				BoardCell cell = board[i][j];
				if(drawTargets && targets.contains(cell)) {
					cell.draw(g, Color.CYAN);
				} else {
					cell.draw(g, Color.YELLOW);
				}
			}
		}
		for(Player p : players) {
			p.draw(g);
		}
	}

	private class MListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			Player cPlayer = players.get(playerTurn);
			Board b = Board.getInstance();
			if(cPlayer.getPlayerType() != "Human" || playerMoved) {
				return;
			}
			else {
				for(BoardCell cell: targets) {
					if(cell.containsClick(event.getX(),event.getY())) {
						cPlayer.setLocation(cell);
						playerMoved = true;
						repaint();
						if(cell.isDoorway()) {
							GuessDialog gDialog= new GuessDialog((JFrame) b.getTopLevelAncestor(),b.getRoomName(cell.getInitial()));
							cPlayer.setSuggestion(gDialog.getSolution());
							if(cPlayer.getSuggestion()!=null) {
								cPanel.updateDisprove(handleSuggestion(cPlayer));
							}
						}
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Invalid location selected. Try again.", "Alert" ,JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}

	public void setControl(ControlPanel contPanel) {
		cPanel = contPanel;		
	}
}

