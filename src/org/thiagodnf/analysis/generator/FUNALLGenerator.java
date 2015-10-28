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
import org.thiagodnf.core.util.FilesUtils;

/**
 * FUNALL Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class FUNALLGenerator extends AbstractGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(FUNALLGenerator.class.getName());
	
	public FUNALLGenerator(JFrame parent) {
		super(parent);
	}
	
	protected Void doInBackground() throws Exception {
		
		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, "FUN_","ALL"));
		}
		
		this.totalOfFiles = files.size();
		
		logger.info(totalOfFiles + " has been found");
		
		logger.info("Sorting files");
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();		
		
		for (String filename : files) {
			
			String fullPath = FilenameUtils.getFullPath(filename);

			if (!map.containsKey(fullPath)) {
				map.put(fullPath, new ArrayList<String>());
			}

			map.get(fullPath).add(filename);			
		}
		
		logger.info("Files were sorted");

		updateMaximum(totalOfFiles);

		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), "/FUNALL");
		}
		
		logger.info("Done");
		
		return null;
	}
	
	@Override
	public String toString() {
		return "FUNALL Generator";
	}
}

