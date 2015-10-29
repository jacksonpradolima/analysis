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
 * Known Pareto-front Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class KnownParetoFrontGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(KnownParetoFrontGenerator.class.getName());
	
	public KnownParetoFrontGenerator(JFrame parent, File[] folders) {
		super(parent, folders);
	}	

	@Override
	protected Object doInBackground() throws Exception {
		List<String> files = getFilesStartingWith(folders, "FUNALL", null);

		logger.info(files.size() + " has been found");

		updateMaximum(files.size());

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String filename : files) {
			updateNote("Sorting files " + getCurrentProgress() + " from " + files.size());

			String fullPath = FilenameUtils.getFullPath(filename);
			
			String parent = new File(fullPath).getParentFile().getAbsolutePath();

			if (!map.containsKey(parent)) {
				map.put(parent, new ArrayList<String>());
			}

			map.get(parent).add(filename);

			updateProgress();
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), "/PFKNOWN", files.size());
		}
		
		afterFinishing();
		
		logger.info("Done");
		
		return null;
	}
	
	@Override
	public String toString() {
		return "Running Known Pareto-Front Generator";
	}
}

