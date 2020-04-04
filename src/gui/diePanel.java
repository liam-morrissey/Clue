package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class diePanel extends JPanel {
	private JLabel rollLabel;
	private JTextField roll;
	
	public diePanel() {
		rollLabel = new JLabel("Roll");
		roll = new JTextField(5);
		
		add(rollLabel);
		add(roll);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Die"));
	}
}
