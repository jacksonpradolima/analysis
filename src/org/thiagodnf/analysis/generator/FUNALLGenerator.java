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

/**
 * FUNALL Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class FUNALLGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(FUNALLGenerator.class.getName());
	
	public FUNALLGenerator(JFrame parent, File[] folders) {
		super(parent, folders);
	}
	
	protected Void doInBackground() throws Exception {
		
		List<String> files = getFilesStartingWith(folders, "FUN_", "ALL");
		
		logger.info(files.size() + " has been found");

		updateMaximum(files.size());

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String filename : files) {
			updateNote("Sorting files " + getCurrentProgress() + " from " + files.size());

			String fullPath = FilenameUtils.getFullPath(filename);

			if (!map.containsKey(fullPath)) {
				map.put(fullPath, new ArrayList<String>());
			}

			map.get(fullPath).add(filename);

			updateProgress();
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), "/FUNALL", files.size());
		}
		
		afterFinishing();
		
		logger.info("Done");
		
		return null;
	}
	
	@Override
	public String toString() {
		return "Running FUNALL Generator";
	}
}

