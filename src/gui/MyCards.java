package gui;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class MyCards extends JPanel {
	Set<Card> playerHand;
	
	public MyCards() {
		// Find the human player and retrieve their hand
		for(Player i : Board.getInstance().getPlayers()) {
			if(i.getPlayerType().equals("Human")) {
				playerHand = i.getCardsInHand();
			}
		}
		
		setLayout(new GridLayout(4,1));
		
		PeopleCard pCard = new PeopleCard();
		add(pCard);
		
		RoomCard rCard = new RoomCard();
		add(rCard);
		
		WeaponCard wCard = new WeaponCard();
		add(wCard);
		
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
	}
	
	private class PeopleCard extends JPanel {
		String toDisplay;
		
		public PeopleCard() {
			JTextArea peopleCards = new JTextArea(4, 10);
			
			toDisplay = "";
			for(Card i : playerHand) {
				if(i.getType() == CardType.PERSON) {
					toDisplay += ("\n" + i.getName());
				}
			}
			
			peopleCards.setText(toDisplay);
			
			peopleCards.setEnabled(false);
			
			add(peopleCards);
			
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
		}
	}
	
	private class RoomCard extends JPanel {
		String toDisplay;
		
		public RoomCard() {
			JTextArea roomCards = new JTextArea(4, 10);
			
			toDisplay = "";
			for(Card i : playerHand) {
				if(i.getType() == CardType.ROOM) {
					toDisplay += ("\n" + i.getName());
				}
			}
			
			roomCards.setText(toDisplay);
			
			roomCards.setEnabled(false);
			
			add(roomCards);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		}
	}
	
	private class WeaponCard extends JPanel {
		String toDisplay;
		
		public WeaponCard() {
			JTextArea weaponCards = new JTextArea(4, 10);
			
			toDisplay = "";
			for(Card i : playerHand) {
				if(i.getType() == CardType.WEAPON) {
					toDisplay += ("\n" + i.getName());
				}
			}
			
			weaponCards.setText(toDisplay);
			
			weaponCards.setEnabled(false);
			
			add(weaponCards);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		}
	}
}
