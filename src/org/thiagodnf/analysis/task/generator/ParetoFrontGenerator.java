package org.thiagodnf.analysis.task.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;

import jmetal.core.SolutionSet;

import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

import com.google.common.base.Preconditions;

/**
 * PFKnown Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public abstract class ParetoFrontGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(ParetoFrontGenerator.class.getName());
	
	protected String inputFile;
	
	protected String outputFile;
	
	protected String ignoreFile;
	
	public ParetoFrontGenerator(JFrame parent, File[] folder, String inputFile, String outputFile, String ignoreFile) {
		super(parent, folder);
		
		Preconditions.checkNotNull(inputFile, "Input file cannot be null");
		Preconditions.checkNotNull(outputFile, "Output file cannot be null");
		Preconditions.checkArgument(!inputFile.isEmpty(), "Input file cannot be empty");
		Preconditions.checkArgument(!outputFile.isEmpty(), "Output file cannot be empty");
		
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.ignoreFile = ignoreFile;
	}
	
	protected void generate(String folder, List<String> files, String filename, int totalOfFiles) throws Exception {
		
		Preconditions.checkNotNull(folder, "Folder cannot be null");
		Preconditions.checkArgument(!folder.isEmpty(), "Folder cannot be empty");
		
		logger.info("Generating for folder: " + folder);
		
		SolutionSet population = new SolutionSet(Integer.MAX_VALUE);
		
		for (String file : files) {
			updateNote(getCurrentProgress() + " from " + totalOfFiles);
			
			logger.info("Reading the fun file: " + file);

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
	
	protected Void doInBackground() throws Exception {
		
		List<String> files = getFilesStartingWith(folders, inputFile, ignoreFile);
		
		logger.info(files.size() + " has been found");

		updateMaximum(files.size());

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String filename : files) {
			updateNote("Sorting files " + getCurrentProgress() + " from " + files.size());

			String key = sortingFilesBy(filename);

			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}

			map.get(key).add(filename);

			updateProgress();
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), outputFile, files.size());
		}
		
		afterFinishing();
		
		logger.info("Done");
		
		return null;
	}
	
	protected abstract String sortingFilesBy(String filename);
}

