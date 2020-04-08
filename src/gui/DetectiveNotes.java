package gui;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(400, 800);
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
		public PeoplePanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
		}
	}
	
	private class PeopleGuessPanel extends JPanel {
		public PeopleGuessPanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		}
	}
	
	private class RoomPanel extends JPanel {
		public RoomPanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		}
	}
	
	private class RoomGuessPanel extends JPanel {
		public RoomGuessPanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
	}
	
	private class WeaponPanel extends JPanel {
		public WeaponPanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		}
	}
	
	private class WeaponGuessPanel extends JPanel {
		public WeaponGuessPanel() {
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		}
	}
}
