package clueGame;

/**
 * @author Brandt Ross
 * @author Liam Morrissey
 * 
 * Each instance is a cell that holds values for the cells row and column
 */
public class BoardCell {
	private int row;
	private int column;
	// Is a string because some have two chars
	private String cellText;
	private DoorDirection dir;

	public BoardCell(int row, int column, String cellText) {
		this.row = row;
		this.column = column;
		this.cellText = cellText;
		
		if(cellText.length() == 2) {
			if(cellText.charAt(1) == 'D') {
				dir = DoorDirection.DOWN;
			} else if(cellText.charAt(1) == 'U') {
				dir = DoorDirection.UP;
			} else if(cellText.charAt(1) == 'L') {
				dir = DoorDirection.LEFT;
			} else if(cellText.charAt(1) == 'R') {
				dir = DoorDirection.RIGHT;
			} else {
				dir = DoorDirection.NONE;
			}
		} else {
			dir = DoorDirection.NONE;
		}
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	public boolean isDoorway() {
		return (dir != DoorDirection.NONE);
	}
	
	public boolean isWalkway() {
		if(cellText.charAt(0)=='W')return true;
		else return false;
	}

	public DoorDirection getDoorDirection() {
		return dir;
	}

	public char getInitial() {
		return cellText.charAt(0);
	}
	
	public String toString() {
		return "Row: " + row + " Col: " + column + " Dir: " + dir + " Text: " + cellText;
	}
}
