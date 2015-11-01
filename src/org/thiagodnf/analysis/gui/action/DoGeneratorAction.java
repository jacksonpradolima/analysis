package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.window.ChooseGeneratorsWindow;
import org.thiagodnf.analysis.task.generator.Generator;
import org.thiagodnf.analysis.task.generator.GeneratorFactory;

public class DoGeneratorAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public DoGeneratorAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChooseGeneratorsWindow window = new ChooseGeneratorsWindow(parent);
		
		if (window.showOptionDialog() == JOptionPane.YES_OPTION) {
			
			List<String> generatorNames = window.getSelectedGenerators();

			if (generatorNames.isEmpty()) {
				throw new IllegalArgumentException("You must to select at least one generator");
			}
			
			final JFileChooser fc = new JFileChooser();

			fc.setCurrentDirectory(new File("."));
			
			fc.setMultiSelectionEnabled(true);
			
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {

				final File[] folders = fc.getSelectedFiles();

				List<Generator> generators = new ArrayList<Generator>();
				
				for (String genName : generatorNames) {
					generators.add(GeneratorFactory.getGenerator(parent, genName, folders));
				}
				
				Generator generator = generators.remove(0);
				
				generator.setPendingGenerator(generators);
				
				generator.execute();					
			}
		}
	}
}
