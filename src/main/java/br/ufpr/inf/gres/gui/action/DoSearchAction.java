package br.ufpr.inf.gres.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import br.ufpr.inf.gres.gui.window.MainWindow;
import br.ufpr.inf.gres.gui.window.MessageBox;

/**
 * Class responsible for to select rows on table
 * 
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-11-6
 */
public class DoSearchAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoSearchAction(JFrame parent){
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		MainWindow window = (MainWindow) parent;
		
		String term = null;
		
		while (term == null || term.isEmpty()) {
			term = JOptionPane.showInputDialog("Search by");

			if (term == null) {
				return;
			}

			if (term.isEmpty()) {
				MessageBox.warning(parent, "You must to define a term.");
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
	}
}
