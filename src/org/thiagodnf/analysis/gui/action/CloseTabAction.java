package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.component.ResultTab;
import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class CloseTabAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public CloseTabAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		try{
			ResultTab tab = window.getSelectedTab();
			
			if(tab == null){
				throw new IllegalArgumentException("You need to open a folder first");
			}
			
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to close the current tab?","Warning", JOptionPane.YES_NO_OPTION);

			if (dialogResult == JOptionPane.YES_OPTION) {
				window.removeSelectedTab();
			}			
		}catch(Exception ex){
			MessageBoxWindow.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
