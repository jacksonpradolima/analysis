package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.task.ExportFromFUNALLToGnuplotTask;

/**
 * This class is responsible for show a prompt where the user must to
 * choose which export operation him/her will be performs.
 * 
 * @author Thiago Nascimento
 * @version 1.1.0
 * @since 2015-11-03
 */
public class DoExportAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoExportAction(JFrame parent) {
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		MainWindow window = (MainWindow) parent;
		
		if (window.getResultTable().getSelectedRowCount() == 0) {
			throw new IllegalArgumentException("You must to select at least a line");
		}
		
		List<String> options = new ArrayList<String>();
		
		options.add("FUNALL Files to Gnuplot");
		
		String[] names = new String[options.size()];
			
		for (int i = 0; i < options.size(); i++) {
			names[i] = options.get(i);
		}
		
		String selected = (String) JOptionPane.showInputDialog(parent, null, "Export", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

		if (selected == null) {
			return;
		}
		
		List<String> folders = window.getResultTable().getSelectedFolderFiles();
		
		JFileChooser fc = new JFileChooser();
		
		if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File outputFolder = fc.getSelectedFile();
			
			if(selected.equalsIgnoreCase("FUNALL Files to Gnuplot")){
				AsyncTask task = new ExportFromFUNALLToGnuplotTask(parent, folders, outputFolder);
				task.execute();
			}
		}		
	}
}
