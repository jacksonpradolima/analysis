package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class OpenFoldersAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public OpenFoldersAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();

		fc.setCurrentDirectory(new File("."));
		
		// The user must to select a folder with all files
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		fc.setMultiSelectionEnabled(true);
		
		if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			// Folder that was selected by user
			File[] folders = fc.getSelectedFiles();
			
			for (File folder : folders) {
				String path = folder.getAbsolutePath();
				if (!((MainWindow) parent).getFolders().contains(path)) {
					((MainWindow) parent).getFolders().add(path);
				}
			}
			
			((MainWindow) parent).getFilter().clear();
			
			try {
				((MainWindow) parent).reloadFolder();
			} catch (IOException ex) {
				MessageBoxWindow.error(parent, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
