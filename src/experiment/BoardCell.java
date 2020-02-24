package experiment;

/*
 * @author Brandt Ross
 * @author Liam Morrissey
 * 
 * Each instance is a cell that holds values for the cells row and column
 */
public class BoardCell {
	// Stores the row of the cell
	private int row;
	// Stores the column of the cell
	private int column;

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}
}
