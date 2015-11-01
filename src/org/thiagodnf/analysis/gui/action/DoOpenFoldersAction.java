package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBox;

public class DoOpenFoldersAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoOpenFoldersAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();

		fc.setCurrentDirectory(new File("."));
		
		// The user must to select a folder with all files
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			// Folder that was selected by user
			File selectedFolder = fc.getSelectedFile();
			
			((MainWindow) parent).setFolder(selectedFolder);
			
			((MainWindow) parent).getFilter().clear();
			
			try {
				((MainWindow) parent).openFolder();
			} catch (Exception ex) {
				MessageBox.error(parent, ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
