package clueGame;

import java.awt.Color;

/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color, BoardCell boardCell) {
		super(name, color, boardCell);
	}

	public String getPlayerType() {
		return "Human";
	}
}
