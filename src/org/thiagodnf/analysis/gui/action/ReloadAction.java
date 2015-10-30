package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class ReloadAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public ReloadAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			((MainWindow) parent).reloadFolder();
		} catch (IOException e1) {
			MessageBoxWindow.error(parent, e1.getMessage());
			e1.printStackTrace();
		}
	}
}
