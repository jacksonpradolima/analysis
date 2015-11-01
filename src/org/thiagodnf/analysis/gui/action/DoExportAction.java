package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBox;
import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.task.ExportFromFUNALLToGnuplotTask;

public class DoExportAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoExportAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		try{
			if (window.getResultTable().getSelectedRowCount() == 0) {
				throw new IllegalArgumentException("You must to select at least a line");
			}
			
			List<String> options = new ArrayList<String>();
			
			options.add("FUNALL Files to Gnuplot");
			
			String[] names = new String[options.size()];
				
			for (int i = 0; i < options.size(); i++) {
				names[i] = options.get(i);
			}
			
			String selected = (String) JOptionPane.showInputDialog(parent,
					null, "Export",
					JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

			if (selected == null) {
				return;
			}
			
			List<String> folders = window.getResultTable().getSelectedFolderFiles();
			
			JFileChooser fc = new JFileChooser();
			
			// The dialog has a filter
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
				File outputFolder = fc.getSelectedFile();
				
				if(selected.equalsIgnoreCase("FUNALL Files to Gnuplot")){
					AsyncTask task = new ExportFromFUNALLToGnuplotTask(parent, folders, outputFolder);
					task.execute();
				}
			}			
		}catch(Exception ex){
			MessageBox.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
