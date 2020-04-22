package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

/**
 * Creates the detective notes dialog with 6 JPanels
 * People Panel- Panel that lists the people with checkboxes next to their names
 * People Guess Panel- Panel that shows current guess for person based off a drop down list of names
 * Room Panel- Panel that lists the rooms with checkboxes next to their names
 * Room Guess Panel- Panel that shows current guess for the room based off a drop down list of names
 * Weapon Panel- Panel that lists the weapon with checkboxes next to their names
 * Weapon Guess Panel- Panel that shows current guess for the weapon based off a drop down list of names
 */
public class DetectiveNotes extends JDialog {
	private Board board;

	public DetectiveNotes() {
		board = Board.getInstance();

		setTitle("Detective Notes");
		setSize(700, 600);
		setLayout(new GridLayout(0, 2));
		setVisible(true);

		PeoplePanel pPanel = new PeoplePanel();
		add(pPanel);

		PeopleGuessPanel pgPanel = new PeopleGuessPanel();
		add(pgPanel);

		RoomPanel rPanel = new RoomPanel();
		add(rPanel);

		RoomGuessPanel rgPanel = new RoomGuessPanel();
		add(rgPanel);

		WeaponPanel wPanel = new WeaponPanel();
		add(wPanel);

		WeaponGuessPanel wgPanel = new WeaponGuessPanel();
		add(wgPanel);
	}

	private class PeoplePanel extends JPanel {
		ArrayList<Player> players;

		public PeoplePanel() {
			setLayout(new GridLayout(0, 2));

			players = board.getPlayers();
			for(Player i : players) {
				add(new JCheckBox(i.getName()));
			}

			setBorder(new TitledBorder(new EtchedBorder(), "People"));
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

	private class RoomPanel extends JPanel {
		ArrayList<Card> deck;

		public RoomPanel() {
			setLayout(new GridLayout(0, 2));

			deck = board.getDeck();

			for(Card i : deck) {
				if(i.getType() == CardType.ROOM) {
					add(new JCheckBox(i.getName()));
				}
			}

			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		}
	}

	private class RoomGuessPanel extends JPanel {
		JComboBox<String> roomGuess;
		ArrayList<Card> deck;

		public RoomGuessPanel() {
			roomGuess = new JComboBox<String>();
			setLayout(new BorderLayout());

			deck = board.getDeck();
			for(Card i : deck) {
				if(i.getType() == CardType.ROOM) {
					roomGuess.addItem(i.getName());
				}
			}

			add(roomGuess);

			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
	}

	private class WeaponPanel extends JPanel {
		ArrayList<String> weapons;

		public WeaponPanel() {
			setLayout(new GridLayout(0, 2));

			weapons = board.getWeaponList();
			for(String i : weapons) {
				add(new JCheckBox(i));
			}

			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
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
