package org.thiagodnf.analysis.gui.window;

import java.awt.Image;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXHyperlink;
import org.thiagodnf.analysis.gui.action.DoOpenURLAction;
import org.thiagodnf.core.util.ImageUtils;

/**
 * This class is a window used to shows informations about this tool
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class AboutWindow extends DialogWindow {

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected String url = "http://github.com/thiagodnf/analysis";
	
	public AboutWindow(JFrame parent) {
		super(parent, "About Analysis");
		
		Image img = ImageUtils.getFromFile(parent, "logo.png");
		
		JLabel logo = new JLabel(new ImageIcon(img));
		JButton website = new JXHyperlink(new DoOpenURLAction(parent, url));  
		
		add(logo);
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><b>Author</b></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("Thiago Nascimento"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><b>Website</b></html>"));
		add(Box.createVerticalStrut(10));
		add(website);
		add(Box.createVerticalStrut(10));
		add(new JLabel("<html><b>Version</b></html>"));
		add(Box.createVerticalStrut(10));
		add(new JLabel("1.0.0"));
	}
}
