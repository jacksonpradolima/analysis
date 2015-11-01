package org.thiagodnf.analysis.gui.window;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogWindow extends JPanel {

	private static final long serialVersionUID = -6639393252255135584L;
	
	protected JFrame parent;
	
	protected String title;

	public DialogWindow(JFrame parent, String title) {
		this.parent = parent;
		this.title = title;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public int showOptionDialog(){
		Object[] options = { "Close" };

		int optionType = JOptionPane.OK_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}	
}
