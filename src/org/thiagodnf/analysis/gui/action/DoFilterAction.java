package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.thiagodnf.analysis.gui.window.MainWindow;

/**
 * This class is responsible for make a filter in the table results. To search
 * more then one term, the user must to use the ";" operator to separate the
 * terms.
 * 
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-11-03 
 */
public class DoFilterAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoFilterAction(JFrame parent){
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		MainWindow window = (MainWindow) parent;
		
		// Load the field with a initial value if it exists.
		String value = "";

		for (String term : window.getFilter()) {
			value += ";" + term;
		}

		value = value.replaceFirst(";", "");
		
		String term = JOptionPane.showInputDialog("Filter by", value);

		// User cancelled the prompt
		if (term == null) {
			return;
		}
		
		// Clear the filter before add a new search term
		window.getFilter().clear();
		
		String[] terms = term.split(";");

		for (String t : terms) {
			window.getFilter().add(t);
		}
		
		window.getResultTable().reload();		
	}
}
