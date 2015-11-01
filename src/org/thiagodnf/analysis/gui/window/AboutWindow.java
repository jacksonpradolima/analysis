package org.thiagodnf.analysis.gui.window;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.thiagodnf.core.util.ImageUtils;

public class AboutWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JLabel logo;
	
	protected JLabel website;
	
	protected String url = "http://github.com/thiagodnf/analysis";
	
	public AboutWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Image img = ImageUtils.getFromFile(parent, "logo.png");
		
		this.logo = new JLabel(new ImageIcon(img));
		this.logo.setAlignmentX(Component.TOP_ALIGNMENT);
		
		this.website = new JLabel("<html><a href=\"" + url + "\">" + url + "</a></html>");
		this.website.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(new URI(url));
					}
				} catch (URISyntaxException | IOException ex) {
					MessageBox.error(null, "Error in opening browser" + ":\n" + ex.getLocalizedMessage());
					ex.printStackTrace();
				}
			}
		});	
		
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
		
	public int showOptionDialog(){
		Object[] options = { "Close"};

		// Show the indicator name at title
		String title = "About Analysis";
		
		int optionType = JOptionPane.OK_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}	
}
