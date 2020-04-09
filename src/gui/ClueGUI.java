package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import clueGame.Board;

public class ClueGUI extends JFrame {
	private Board board;
	
	public ClueGUI() {
		// Initialize the board and load the necessary files
		board = Board.getInstance();
		board.setConfigFiles("clueBoard.csv", "roomLegend.txt", "player.txt", "weapons.txt");
		board.initialize();
		
		setSize(new Dimension(800, 600));
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ControlPanel cPanel = new ControlPanel();
		add(cPanel, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	/**
	 * Creates the file drop down in the menu bar
	 */
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(displayDetective());
		menu.add(createFileExitItem());
		return menu;
	}
	
	/**
	 * Create the exit option that will exit the program when clicked
	 */
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(e -> System.exit(0));
		return item;
	}
	
	/**
	 * Create the displayDetective option that will display the detective notes when clicked
	 */
	private JMenuItem displayDetective() {
		JMenuItem dispDetect = new JMenuItem("Display Detective Notes");
		dispDetect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DetectiveNotes notesDialog = new DetectiveNotes();
			}
		});
		return dispDetect;
	}
	
	public static void main(String[] args) {
		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
	}
}
