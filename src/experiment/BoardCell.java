package experiment;

public class BoardCell {
	private int row;
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
	
	@Override
	public String toString() {
		return "row: " + row + " col: " + column;
	}
}
