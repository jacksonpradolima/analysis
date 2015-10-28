package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

import jmetal.core.SolutionSet;

/**
 * PFKnown Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public abstract class ParetoFrontGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(ParetoFrontGenerator.class.getName());
	
	public ParetoFrontGenerator(JFrame parent, File[] folder) {
		super(parent, null);
	}
	
	protected void generate(String folder, List<String> files, String filename, int totalOfFiles) throws Exception {
		
		logger.info("Generating for folder: " + folder);
		
		SolutionSet population = new SolutionSet(Integer.MAX_VALUE);
		
		for (String file : files) {
			updateNote(getCurrentProgress() + " from " + totalOfFiles);
			
			logger.info("Reading the fun file: " + file.replaceFirst(new File(file).getParent(),""));

			SolutionSet fun = SolutionSetUtils.getFromFile(file);

			logger.info(fun.size() + " found solutions. Joining all solutions");

			population = population.union(fun);

			logger.info(population.size() + " found solutions. Removing repeated solutions");

			population = SolutionSetUtils.removeRepeatedSolutions(population);

			logger.info(population.size() + " found solutions. Removing dominated solutions");

			population = SolutionSetUtils.removeDominatedSolutions(population);

			logger.info(population.size() + " found solutions.");

			updateProgress();
		}
		
		logger.info(population.size() + " found solutions. Saving the " + filename + " file");

		population.printObjectivesToFile(folder + filename);
	}
}

