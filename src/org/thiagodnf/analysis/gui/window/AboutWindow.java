package org.thiagodnf.analysis.gui.window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AboutWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	public AboutWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new JLabel("<html><b>Author</b></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("Thiago Nascimento"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><b>Website</b></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><a href=\"http://github.com/thiagodnf/analysis\">http://github.com/thiagodnf/analysis</a></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><b>Version</b></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("1.0.0"));
	}
		
	public int showOptionDialog(){
		Object[] options = { "Close"};

		// Show the indicator name at title
		String title = "About";
		
		int optionType = JOptionPane.OK_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}	
}
