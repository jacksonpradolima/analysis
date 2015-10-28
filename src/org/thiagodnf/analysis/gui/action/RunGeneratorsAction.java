package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.generator.Generator;
import org.thiagodnf.analysis.generator.GeneratorFactory;
import org.thiagodnf.analysis.gui.window.ChooseGeneratorsWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public class RunGeneratorsAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public RunGeneratorsAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			ChooseGeneratorsWindow window = new ChooseGeneratorsWindow(parent);
			
			if (window.showOptionDialog() == JOptionPane.YES_OPTION) {
				
				List<String> generators = window.getSelectedGenerators();

				if (generators.isEmpty()) {
					throw new IllegalArgumentException("You must to select at least one generator");
				}
				
				final JFileChooser fc = new JFileChooser();
	
				fc.setCurrentDirectory(new File("."));
				
				fc.setMultiSelectionEnabled(true);
				
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {

					final File[] folders = fc.getSelectedFiles();

					for (String genName : generators) {
						Generator generator = GeneratorFactory.getGenerator(parent, genName, folders);
					//generator.setPendingAsyncTask(generators);
						generator.execute();
					}
				}
			}
		} catch (Exception ex) {
			MessageBoxWindow.info(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
