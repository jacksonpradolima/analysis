package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.gui.window.PreferencesWindow;

public class PreferencesAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public PreferencesAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PreferencesWindow preferencesWindow = new PreferencesWindow(parent);
		
		if (preferencesWindow.showOptionDialog() == JOptionPane.YES_OPTION) {
			preferencesWindow.save();
			
			try {
				((MainWindow) parent).reloadFolder();
			} catch (IOException ex) {
				MessageBoxWindow.error(parent, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
