package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class DoSearchAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoSearchAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		try{
			String term = null;
			
			while (term == null || term.isEmpty()) {
				term = JOptionPane.showInputDialog("Search by");
	
				if (term == null) {
					return;
				}
	
				if (term.isEmpty()) {
					MessageBoxWindow.warning(parent, "You must to define a term.");
				}
			}
			
			if (window.getResultTable().getRowCount() == 0) {
				throw new IllegalArgumentException("The result table is empty");
			}
			
			window.getResultTable().clearSelection();
			
			ListSelectionModel model = window.getResultTable().getSelectionModel();
			
			model.clearSelection();
			
			for (int i = 0; i < window.getResultTable().getRowCount(); i++) {
				String path = (String) window.getResultTable().getModel().getValueAt(i, 1);
				
				if (path.contains(term)) {
					// Selects the line
					model.addSelectionInterval(i, i);
					// Check the box at the line
					window.getResultTable().getModel().setValueAt(new Boolean(true), i, 0);
				}
			}
			
			// When selects the rows, define focus to table for the user can
			// copy the table's content 
			window.getResultTable().requestFocus();
		}catch(Exception ex){
			MessageBoxWindow.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
