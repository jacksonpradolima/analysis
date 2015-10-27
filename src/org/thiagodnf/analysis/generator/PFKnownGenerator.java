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

public class PFKnownGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(PFKnownGenerator.class.getName());
	
	protected int processedFile = 1;
	
	protected int totalOfFiles;
	
	public PFKnownGenerator(JFrame frame) {
		super(frame);
	}
	
	protected Void doInBackground() throws Exception {
		
		logger.info("==================================================================");
		logger.info("Starting " + toString());
		logger.info("==================================================================");
		
		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, "FUNALL"));
		}
		
		this.totalOfFiles = files.size();

		showMessage(totalOfFiles + " has been found");
		
		updateMaximum(totalOfFiles);
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		
		logger.info("Sorting files");
		
		int sortedFiles = 1;

		for (String filename : files) {
			
			String fullPath = FilenameUtils.getFullPath(filename);
			
			String parent = new File(fullPath).getParentFile().getAbsolutePath();

			if (!map.containsKey(parent)) {
				map.put(parent, new ArrayList<String>());
			}

			map.get(parent).add(filename);

			updateProgress(sortedFiles++ + " from " + totalOfFiles);
		}
		
		logger.info("Files were sorted");
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue());
		}
		
		logger.info("Done");
		
		return null;
	}
	
	protected void generate(String folder, List<String> files) throws Exception {
		SolutionSet pfknown = new SolutionSet(Integer.MAX_VALUE);
		
		for (String filename : files) {
			logger.info("Reading the fun file: " + filename);

			SolutionSet fun = SolutionSetUtils.getFromFile(filename);

			logger.info(fun.size() + " solutions have been found. Joining the solutions");

			pfknown = pfknown.union(fun);

			logger.info(pfknown.size() + " found solutions. Removing repeated solutions");

			pfknown = SolutionSetUtils.removeRepeatedSolutions(pfknown);

			logger.info(pfknown.size() + "found solutions. Removing dominated solutions");

			pfknown = SolutionSetUtils.removeDominatedSolutions(pfknown);

			updateProgress(processedFile++ + " from " + totalOfFiles);
		}

		pfknown.printObjectivesToFile(folder + "/PFKNOWN");
	}

	@Override
	public String toString() {
		return "PF Known Generator";
	}
}

