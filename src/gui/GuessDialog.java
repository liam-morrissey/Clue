package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Board;

public class GuessDialog {
	public Board board = Board.getInstance();
	public GuessDialog(JFrame parent){
	JDialog frame = new JDialog(parent, "Accusation", true);
	frame.setSize(700, 600);
	frame.setLayout(new GridLayout(0, 2));
	frame.getContentPane().add(new JLabel("Person:"));
	frame.getContentPane().add(new PeopleGuessPanel());
	frame.getContentPane().add(new JLabel("Room:"));
	frame.getContentPane().add(new RoomGuessPanel());
	frame.getContentPane().add(new JLabel("Weapon:"));
	frame.getContentPane().add(new WeaponGuessPanel());
	frame.getContentPane().add(new JButton("Submit"));
	frame.getContentPane().add(new JButton("Cancel"));
	frame.pack();
	frame.setVisible(true);
	}
	
	public GuessDialog(JFrame parent, String room){
		JDialog frame = new JDialog(parent, "Suggestion", true);
		frame.setSize(700, 600);
		frame.setLayout(new GridLayout(0, 2));
		frame.getContentPane().add(new JLabel("Person:"));
		frame.getContentPane().add(new PeopleGuessPanel());
		frame.getContentPane().add(new JLabel("Room:"));
		frame.getContentPane().add(new RoomGuessPanel(room));
		frame.getContentPane().add(new JLabel("Weapon:"));
		frame.getContentPane().add(new WeaponGuessPanel());
		frame.getContentPane().add(new JButton("Submit"));
		frame.getContentPane().add(new JButton("Cancel"));
		frame.pack();
		frame.setVisible(true);
		}
	
	private class PeopleGuessPanel extends JPanel {
		JComboBox<String> peopleGuess;
		ArrayList<Player> players;
		
		public PeopleGuessPanel() {
			peopleGuess = new JComboBox<String>();
			setLayout(new BorderLayout());
			
			players = board.getPlayers();
			for(Player i : players) {
				peopleGuess.addItem(i.getName());
			}
			
			add(peopleGuess);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		}
	}
	
	private class RoomGuessPanel extends JPanel {
		JComboBox<String> roomGuess;
		ArrayList<Card> deck;
		
		public RoomGuessPanel() {
			roomGuess = new JComboBox<String>();
			setLayout(new BorderLayout());
			insertToRoom(null);			
			add(roomGuess);
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
		public RoomGuessPanel(String roomName) {
			roomGuess = new JComboBox<String>();
			setLayout(new BorderLayout());
			insertToRoom(roomName);			
			add(roomGuess);
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
		
		
		private void insertToRoom(String roomName) {
			deck = board.getDeck();
			for(Card i : deck) {
				if(i.getType() == CardType.ROOM) {
					if(roomName == null) 
					roomGuess.addItem(i.getName());
					else if(roomName == i.getName()) {
						roomGuess.addItem(i.getName());
						break;
					}
				}
			}
		}
	}
	
	private class WeaponGuessPanel extends JPanel {
		JComboBox<String> weaponGuess;
		ArrayList<String> weapons;
		
		public WeaponGuessPanel() {
			weaponGuess = new JComboBox<String>();
			setLayout(new BorderLayout());
			
			weapons = board.getWeaponList();
			for(String i : weapons) {
				weaponGuess.addItem(i);
			}
			
			add(weaponGuess);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		}
	}
	
	
	
	
}
