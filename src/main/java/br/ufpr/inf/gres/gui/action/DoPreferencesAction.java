package br.ufpr.inf.gres.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.ufpr.inf.gres.gui.window.MainWindow;
import br.ufpr.inf.gres.gui.window.PreferencesWindow;
import br.ufpr.inf.gres.core.util.SettingsUtils;

public class DoPreferencesAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoPreferencesAction(JFrame parent) {
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		PreferencesWindow preferencesWindow = new PreferencesWindow(parent);
		
		if (preferencesWindow.showOptionDialog() == JOptionPane.YES_OPTION) {
			// Save the user preferences on pc
			preferencesWindow.save();

			if (userChangedTheLookAndFeel()) {
				UIManager.setLookAndFeel(SettingsUtils.getLookAndFeel());

				// Update look and feel
				SwingUtilities.updateComponentTreeUI(parent);
				parent.pack();
			}

			((MainWindow) parent).getResultTable().reload();
		}
	}
	
	protected boolean userChangedTheLookAndFeel() {
		String current = UIManager.getLookAndFeel().toString();
		String saved = SettingsUtils.getLookAndFeel();
		
		return !current.contains(saved);
	}
}
