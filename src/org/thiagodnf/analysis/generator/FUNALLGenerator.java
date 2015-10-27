package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;
import org.thiagodnf.core.util.FilesUtils;

import jmetal.core.SolutionSet;

/**
 * FUNALL Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class FUNALLGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(FUNALLGenerator.class.getName());
	
	protected int processedFile = 1;
	
	protected int totalOfFiles;
	
	public FUNALLGenerator(JFrame parent) {
		super(parent);
	}
	
	protected Void doInBackground() throws Exception {
		
		logger.info("==================================================================");
		logger.info("Starting " + toString());
		logger.info("==================================================================");
		
		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, "FUN" + separator,"ALL"));
		}
		
		this.totalOfFiles = files.size();
		
		showMessage(totalOfFiles + " has been found");
		
		updateMaximum(totalOfFiles);
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		
		logger.info("Sorting files");
		
		int sortedFiles = 1;

		for (String filename : files) {
			
			String fullPath = FilenameUtils.getFullPath(filename);

			if (!map.containsKey(fullPath)) {
				map.put(fullPath, new ArrayList<String>());
			}

			map.get(fullPath).add(filename);

			updateProgress(sortedFiles++ + " from " + totalOfFiles);
		}
		
		logger.info("Files were sorted");
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue());
		}
		
		return null;
	}
	
	protected void generate(String folder, List<String> files) throws Exception {

		logger.info("Generating FUNALL for folder: " + folder);

		SolutionSet funAll = new SolutionSet(Integer.MAX_VALUE);

		for (String file : files) {
			logger.info("Reading the fun file: " + file);

			SolutionSet population = SolutionSetUtils.getFromFile(file);
			
			logger.info(population.size() + " found solutions. Joining all solutions");
					
			funAll = funAll.union(population);
			
			logger.info(funAll.size() + " found solutions. Removing repeated solutions");
			
			funAll = SolutionSetUtils.removeRepeatedSolutions(funAll);

			logger.info(funAll.size() + " found solutions. Removing dominated solutions");

			funAll = SolutionSetUtils.removeDominatedSolutions(funAll);
			
			logger.info(funAll.size() + " found solutions.");
			
			updateProgress(processedFile++ + " from " + totalOfFiles);
		}
		
		logger.info(funAll.size() + " found solutions. Saving the FUNALL file");
		
		funAll.printObjectivesToFile(folder + "/FUNALL");
	}
	
	public String toString() {
		return "FUNALL Generator";
	}
}

