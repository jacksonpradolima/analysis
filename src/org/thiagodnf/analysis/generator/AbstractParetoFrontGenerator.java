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
 * PFKnown Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public abstract class AbstractParetoFrontGenerator extends AbstractGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(AbstractParetoFrontGenerator.class.getName());
	
	public AbstractParetoFrontGenerator(JFrame parent) {
		super(parent);
	}
	
	protected Void doInBackground() throws Exception {
		
		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, getFilename()));
		}
		
		this.totalOfFiles = files.size();

		logger.info(totalOfFiles + " has been found");
		
		updateMaximum(totalOfFiles);
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		
		logger.info("Sorting files");
		
		for (String filename : files) {

			String fullPath = FilenameUtils.getFullPath(filename);

			String parent = new File(fullPath).getParentFile().getAbsolutePath();

			if (!map.containsKey(parent)) {
				map.put(parent, new ArrayList<String>());
			}

			map.get(parent).add(filename);
		}
		
		logger.info("Files were sorted");
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), getOutputFilename());
		}
		
		logger.info("Done");
		
		return null;
	}
	
	public abstract String getFilename();
	
	public abstract String getOutputFilename();
}

