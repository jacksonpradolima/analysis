package org.thiagodnf.analysis.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.core.util.FilesUtils;

public class FUNALLGenerator extends Generator {
	
	protected final static Logger logger = Logger.getLogger(FUNALLGenerator.class.getName());
	
	protected Void doInBackground() throws Exception {
		
		logger.info("==================================================================");
		logger.info("Starting " + FUNALLGenerator.class.getSimpleName());
		logger.info("==================================================================");
		
		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, "FUN_"));
		}
		
		hideMessage();
		
		updateMaximum(files.size());
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		
		int currentFile = 1;

		for (String filename : files) {
			String fullPath = FilenameUtils.getFullPath(filename);

			if (!map.containsKey(fullPath)) {
				map.put(fullPath, new ArrayList<String>());
			}

			map.get(fullPath).add(filename);

			updateProgress(++currentFile + " from " + files.size());
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generateFunAll(entry.getKey(), entry.getValue());
		}
		
		
		System.out.println(map);
		
//		logger.info("Finding a known Pareto front file in the path");
//		
//		logger.info("Generating the FUN_ALL for all FUN files");
//		
//		for(File folder : folders){			
//			
//			List<String> files = FilesUtils.getFiles(folder, "FUN_");
//			
//			Map<String,List<String>> map = new HashMap<String, List<String>>();
//			
//			for (String filename : files) {
//				String fullPath = FilenameUtils.getFullPath(filename);
//	
//				if (!map.containsKey(fullPath)) {
//					map.put(fullPath, new ArrayList<String>());
//				}
//	
//				map.get(fullPath).add(filename);
//			}
//			
//			updateMaximum(map.size());
//			
//			for (Entry<String, List<String>> entry : map.entrySet()) {
//				generateFunAll(entry.getKey(), entry.getValue());
//			}
//		}
		
		logger.info("Done");
		
		return null;
	}
	
	protected void generateFunAll(String folder, List<String> files) throws IOException{
		
		logger.info("Generating FUNALL for folder: " + folder);
//		
//		SolutionSet all = new SolutionSet(Integer.MAX_VALUE);
//		
//		for(String file : files){
//			
//			logger.info("Reading the fun file: " + file);
//			
//			SolutionSet population = SolutionSetUtils.getFromFile(file, numberOfObjectives);
//			
//			logger.info(population.size() + " solutions have been found");
//			
//			all = all.union(population);
//		}
//		
//		logger.info(all.size() + " found solutions");
//		
//		logger.info("Removing repeated solutions");
//		
//		all = SolutionSetUtils.removeRepeatedSolutions(all);
//		
//		logger.info("After remove repeated solutions, the Solution Set contains " + all.size() + " solutions");
//		
//		logger.info("Removing dominated solutions");
//		
//		all = SolutionSetUtils.removeDominatedSolutions(all);
//		
//		logger.info(all.size() + " solutions are in known Pareto front");
//		
//		logger.info("Saving known Pareto front in a file");
//
//		all.printObjectivesToFile(fullPath + "/FUNALL");
//		
//		updateProgress("Processing files", ++progress);
	}	
	
	public String toString() {
		return "FUNALL Generator";
	}
}

