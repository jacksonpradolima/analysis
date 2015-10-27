package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.thiagodnf.analysis.generator.Generator;
import org.thiagodnf.analysis.gui.window.ChooseGeneratorsWindow;

public class RunGeneratorsAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public RunGeneratorsAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChooseGeneratorsWindow window = new ChooseGeneratorsWindow(parent);
		
		if (window.showOptionDialog() == JOptionPane.YES_OPTION) {
			
			final JFileChooser fc = new JFileChooser();

			fc.setCurrentDirectory(new File("."));
			
			fc.setMultiSelectionEnabled(true);
			
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
				
				final File[] folders = fc.getSelectedFiles();
				
				int nObjectives = 3;
				
				List<Generator> generators = window.getSelectedGenerators();
				
				if (generators.isEmpty()) {
					throw new IllegalArgumentException("You must to select at least one generator");
				}
				
				for (Generator generator : generators) {
					generator.run(parent, folders, nObjectives);
				}
			}
		}
	}
}
