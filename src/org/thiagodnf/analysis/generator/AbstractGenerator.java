package org.thiagodnf.analysis.generator;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

import jmetal.core.SolutionSet;

public abstract class AbstractGenerator extends GeneratorWorker {
	
	protected final static Logger logger = LoggerUtils.getLogger(AbstractGenerator.class.getName());
	
	protected int processedFile = 1;
	
	protected int totalOfFiles;
	
	public AbstractGenerator(JFrame parent) {
		super(parent);
	}
	
	protected void generate(String folder, List<String> files, String filename) throws Exception {
		
		logger.info("Generating for folder: " + folder);
		
		SolutionSet population = new SolutionSet(Integer.MAX_VALUE);
		
		for (String file : files) {
			logger.info("Reading the fun file: " + file);

			SolutionSet fun = SolutionSetUtils.getFromFile(file);

			logger.info(fun.size() + " solutions have been found. Joining all solutions");

			population = population.union(fun);

			logger.info(population.size() + " found solutions. Removing repeated solutions");

			population = SolutionSetUtils.removeRepeatedSolutions(population);

			logger.info(population.size() + " found solutions. Removing dominated solutions");

			population = SolutionSetUtils.removeDominatedSolutions(population);

			logger.info(population.size() + " found solutions.");

			updateProgress(processedFile++ + " from " + totalOfFiles);
		}
		
		logger.info(population.size() + " found solutions. Saving the " + filename + " file");

		population.printObjectivesToFile(folder + filename);
	}
}
