package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class guessResultPanel extends JPanel {
	private JLabel responseLabel;
	private JTextField response;
	
	public guessResultPanel() {
		responseLabel = new JLabel("Response");
		response = new JTextField(10);
		response.setEditable(false);
		add(responseLabel);
		add(response);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
	}
}
