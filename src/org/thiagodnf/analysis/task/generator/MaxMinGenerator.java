package org.thiagodnf.analysis.task.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.ParetoFrontUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

import com.google.common.base.Preconditions;

/**
 * This class finds all FUN files and generates the maximum and minimum values
 * for each objectives
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.1.0
 */
public class MaxMinGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(MaxMinGenerator.class.getName());
	
	public static final int DEFAULT = 1;
	
	public static final int MAXMIN = 2;
	
	public static final int NORMALIZED = 3;
	
	public MaxMinGenerator(JFrame parent, File[] folders) {
		super(parent, folders);
	}
	
	protected Void doInBackground() throws Exception {
		
		List<String> files = getFilesStartingWith(folders, "FUN_");
		
		logger.info(files.size() + " has been found");

		updateMaximum(files.size());

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String filename : files) {
			updateNote(getCurrentProgress() + " from " + monitor.getMaximum());

			File pf = ParetoFrontUtils.findParetoFront(filename);
			
			if (pf == null) {
				throw new IllegalArgumentException("The Approximate True Pareto-front not found.");
			}
			
			String key = pf.getAbsolutePath();
			
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}

			map.get(key).add(filename);

			updateProgress();
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue());
		}
		
		afterFinishing();
		
		logger.info("Done");
		
		return null;
	}
	
	protected void generate(String folder, List<String> files) throws Exception {
		Preconditions.checkNotNull(folder, "Folder cannot be null");
		Preconditions.checkArgument(!folder.isEmpty(), "Folder cannot be empty");
		
		logger.info("Generating for folder: " + folder);
		
		Solution maxValue = null;
		Solution minValue = null;
		
		String pathToPF = FilenameUtils.getFullPath(new File(folder).getAbsolutePath());
				
		for (String file : files) {
			updateNote(getCurrentProgress() + " from " + monitor.getMaximum());
			
			logger.info("Reading the fun file: " + file.replaceFirst(pathToPF, ""));

			SolutionSet fun = SolutionSetUtils.getFromFile(file);

			logger.info(fun.size() + " found solutions.");

			for (int i = 0; i < fun.size(); i++) {
				Solution s = fun.get(i);

				if (maxValue == null) {
					maxValue = SolutionSetUtils.copy(s);
				}

				if (minValue == null) {
					minValue = SolutionSetUtils.copy(s);
				}

				for (int j = 0; j < s.getNumberOfObjectives(); j++) {
					if (s.getObjective(j) > maxValue.getObjective(j)) {
						maxValue.setObjective(j, s.getObjective(j));
					}
					if (s.getObjective(j) < minValue.getObjective(j)) {
						minValue.setObjective(j, s.getObjective(j));
					}
				}
			}		

			updateProgress();
		}
	
		SolutionSet maxMinValues = new SolutionSet(2);

		maxMinValues.add(minValue);
		maxMinValues.add(maxValue);

		logger.info("Saving the MAXMIN file");

		maxMinValues.printObjectivesToFile(pathToPF + File.separator + "MAXMIN");
	}
	
	@Override
	public String toString() {
		return "Running Maximum and Minimum Generator";
	}	
}

