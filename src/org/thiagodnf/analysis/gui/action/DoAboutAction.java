package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.AboutWindow;

public class DoAboutAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoAboutAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AboutWindow w = new AboutWindow(parent);
		w.showOptionDialog();		
	}
}
