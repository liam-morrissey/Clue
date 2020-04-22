package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Board;

public class GuessDialog extends JDialog implements ActionListener{
	public Board board = Board.getInstance();
	private PeopleGuessPanel pPanel;
	private RoomGuessPanel rPanel;
	private WeaponGuessPanel wPanel;	
	private JButton submit;
	private JButton cancel;
	private Solution sol;

	public GuessDialog(JFrame parent){
		super(parent, "Accusation", true);
		sol=null;
		pPanel = new PeopleGuessPanel();
		rPanel = new RoomGuessPanel();
		wPanel = new WeaponGuessPanel();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		setSize(700, 600);
		setLayout(new GridLayout(0, 2));
		getContentPane().add(new JLabel("Person:"));
		getContentPane().add(pPanel);
		getContentPane().add(new JLabel("Room:"));
		getContentPane().add(rPanel);
		getContentPane().add(new JLabel("Weapon:"));
		getContentPane().add(wPanel);
		getContentPane().add(submit);
		getContentPane().add(cancel);
		pack();
		setVisible(true);
	}

	public GuessDialog(JFrame parent, String room){
		super(parent, "Suggestion", true);
		sol=null;
		pPanel = new PeopleGuessPanel();
		rPanel = new RoomGuessPanel(room);
		wPanel = new WeaponGuessPanel();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		setSize(700, 600);
		setLayout(new GridLayout(0, 2));
		getContentPane().add(new JLabel("Person:"));
		getContentPane().add(pPanel);
		getContentPane().add(new JLabel("Room:"));
		getContentPane().add(rPanel);
		getContentPane().add(new JLabel("Weapon:"));
		getContentPane().add(wPanel);
		getContentPane().add(submit);
		getContentPane().add(cancel);
		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == submit) {
			Card person = board.getCard(pPanel.peopleGuess.getSelectedItem().toString());
			Card room = board.getCard(rPanel.roomGuess.getSelectedItem().toString());
			Card weapon = board.getCard(wPanel.weaponGuess.getSelectedItem().toString());
			sol = new Solution(person,room,weapon);
			dispose();
		}
		if(e.getSource() == cancel) {
			dispose();
		}
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



	public Solution getSolution() {
		return sol;
	}




}
