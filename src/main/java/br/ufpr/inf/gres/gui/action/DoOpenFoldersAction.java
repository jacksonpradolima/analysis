package br.ufpr.inf.gres.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import br.ufpr.inf.gres.gui.window.MainWindow;

/**
 * This class is responsible for open a folder that was selected by user
 *  
 * @author Thiago Nascimento
 * @since 2015-11-03
 * @version 1.0.0
 *
 */
public class DoOpenFoldersAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoOpenFoldersAction(JFrame parent){
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		final JFileChooser fc = new JFileChooser();

		//fc.setCurrentDirectory(new File("."));

		// The user must to select a folder with all files
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			// Folder that was selected by user
			((MainWindow) parent).setFolder(fc.getSelectedFile());

			// New folder open must to clear the filter
			((MainWindow) parent).getFilter().clear();

			((MainWindow) parent).openFolder();
		}
	}
}
