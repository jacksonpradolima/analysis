package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.gui.window.PreferencesWindow;
import org.thiagodnf.analysis.util.SettingsUtils;

public class DoPreferencesAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoPreferencesAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PreferencesWindow preferencesWindow = new PreferencesWindow(parent);
		
		if (preferencesWindow.showOptionDialog() == JOptionPane.YES_OPTION) {
			// Save the user preferences on pc 
			preferencesWindow.save();
			
			try {
				if (userChangedTheLookAndFeel()) {
					UIManager.setLookAndFeel(SettingsUtils.getLookAndFeel());

					// Update look and feel
					SwingUtilities.updateComponentTreeUI(parent);
					parent.pack();
				}
			
				((MainWindow) parent).getResultTable().reload();
			} catch (Exception ex) {
				MessageBoxWindow.error(parent, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	protected boolean userChangedTheLookAndFeel() {
		String current = UIManager.getLookAndFeel().toString();
		String saved = SettingsUtils.getLookAndFeel();
		
		return !current.contains(saved);
	}
}
