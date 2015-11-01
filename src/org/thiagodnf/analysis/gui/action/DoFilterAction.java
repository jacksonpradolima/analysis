package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class DoFilterAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoFilterAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		String initialValue = "";

		for (int i = 0; i < window.getFilter().size(); i++) {
			initialValue += window.getFilter().get(i);
			if (i + 1 != window.getFilter().size()) {
				initialValue += ";";
			}
		}
		
		try{
			String term = JOptionPane.showInputDialog("Filter by", initialValue);

			if (term == null) {
				return;
			}
			
			// Remove before add new term
			window.getFilter().clear();
			
			String[] terms = term.split(";");

			for (String t : terms) {
				window.getFilter().add(t);
			}
			
			window.getResultTable().reload();
		}catch(Exception ex){
			MessageBoxWindow.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
