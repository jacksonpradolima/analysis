package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.thiagodnf.analysis.gui.component.ResultTab;
import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class SearchAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public SearchAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		try{
			ResultTab tab = window.getSelectedTab();
			
			if(tab == null){
				throw new IllegalArgumentException("You need to open a folder");
			}
			
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
			
			tab.clearSelection();
			
			JTable table = tab.getJTable();
			
			ListSelectionModel model = table.getSelectionModel();
			
			model.clearSelection();
			
			for (int i = 0; i < table.getRowCount(); i++) {
				String path = (String) table.getModel().getValueAt(i, 1);
				
				if (path.contains(term)) {
					// Selects the line
					model.addSelectionInterval(i, i);
					// Check the box at the line
					table.getModel().setValueAt(new Boolean(true), i, 0);
				}
			}
		}catch(Exception ex){
			MessageBoxWindow.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
