package experiment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard() {
		super();
		calcAdjacencies();
		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}

	public BoardCell getCell(int i, int j) {
		return null;
	}
}
