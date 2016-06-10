package br.ufpr.inf.cbiogres.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import br.ufpr.inf.cbiogres.window.ChooseGeneratorsWindow;
import br.ufpr.inf.cbiogres.task.generator.Generator;
import br.ufpr.inf.cbiogres.task.generator.GeneratorFactory;

/**
 * This class is responsible for get events when the generate button was clicked
 * 
 * @author Thiago Nascimento
 * @since 2015-11-06
 * @version 1.0.0
 */
public class DoGeneratorAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoGeneratorAction(JFrame parent){
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
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
