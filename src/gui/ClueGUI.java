package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGUI extends JFrame {
	public ClueGUI() {
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
	
	private JMenuItem displayDetective() {
		JMenuItem dispDetect = new JMenuItem("Display Detective Notes");
		return dispDetect;
	}
	
	public static void main(String[] args) {
		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
	}
}
