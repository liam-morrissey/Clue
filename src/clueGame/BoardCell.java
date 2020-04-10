package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
	
	//graphics vars
	int x;
	int y;
	int width;
	int height;

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
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public boolean isDoorway() {
		return (dir != DoorDirection.NONE);
	}
	
	public boolean isWalkway() {
		if(cellText.charAt(0)=='W') {
			return true;
		}
		return false;
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
	
	//helper function to set dimensions
	private void updateDimensions() {
		Board board = Board.getInstance();
		Dimension boardDim = board.getSize();
		width = boardDim.width / board.getNumColumns();
		height = boardDim.height / board.getNumRows();
		x = width * column;
		y = height * row;
	}

	//Function called in paintComponent to draw object
	public void draw(Graphics g) {
		updateDimensions();
		if(isWalkway()) {
			//drawing for walkways
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
		}
		else {
			//Drawing for rooms
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
			if(cellText.length()>1) {
				g.setColor(Color.BLUE);
				switch(cellText.charAt(1)) {
				case 'U':
					g.fillRect(x, y, width, height/10);
					break;
				case 'D':
					g.fillRect(x, y+height, width, -height/10);
					break;
				case 'L':
					g.fillRect(x, y, width/10, height);
					break;
				case 'R':
					g.fillRect(x+width, y, -width/10, height);
					break;
				case 'N':
					g.drawString(Board.getInstance().getRoomName(cellText.charAt(0)), x, y);
					break;
				}
			}
		}
		
	}

	
}
