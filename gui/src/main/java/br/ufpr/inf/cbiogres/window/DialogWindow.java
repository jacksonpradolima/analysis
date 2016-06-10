package br.ufpr.inf.cbiogres.window;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogWindow extends JPanel {

	private static final long serialVersionUID = -6639393252255135584L;
	
	protected JFrame parent;
	
	protected String title;
	
	protected int optionType;

	public DialogWindow(JFrame parent, String title) {
		this(parent, title, JOptionPane.OK_OPTION);
	}
	
	public DialogWindow(JFrame parent, String title, int optionType) {
		this.parent = parent;
		this.title = title;
		this.optionType = optionType;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public int showOptionDialog(){
		Object[] options = null;
		
		if (optionType == JOptionPane.OK_OPTION) {
			options = new Object[] { "Close" };
		} else if (optionType == JOptionPane.OK_CANCEL_OPTION) {
			options = new Object[] { "Save", "Cancel" };
		} else {
			options = new Object[] {};
		}
		
		int messageType = JOptionPane.PLAIN_MESSAGE;
		
		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}
}
