package org.thiagodnf.analysis.task.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.gui.window.MessageBox;
import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;
import org.thiagodnf.core.util.FilesUtils;

public class ExportFromFUNALLToGnuplotTask extends AsyncTask {

	protected static final Logger logger = LoggerUtils.getLogger(ExportFromFUNALLToGnuplotTask.class.getName());
	
	protected List<String> folders;
	
	protected File outputFolder;
	
	public ExportFromFUNALLToGnuplotTask(JFrame parent, List<String> folders, File outputFolder) {
		super(parent);		
		
		this.folders = folders;
		this.outputFolder = outputFolder;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		logger.info("Loading FUNALL files");
		
		List<String> files = new ArrayList<String>();
		
		for (String folder : folders) {
			File fullpath = new File(FilenameUtils.getFullPath(folder));
			files.addAll(FilesUtils.getFiles(fullpath, "FUNALL"));
		}
		
		logger.info(files.size()+" files were found. Generating GNUPLOT file");
		
		updateMaximum(files.size());
		
		int numberOfObjectives = -1;
		
		for(String file : files){
			SolutionSet population = SolutionSetUtils.getFromFile(new File(file));
			
			int nObjectives = SolutionSetUtils.getNumberOfObjectives(population);
			
			if(numberOfObjectives == -1){
				numberOfObjectives = nObjectives;
			}else if(numberOfObjectives != nObjectives){
				throw new IllegalArgumentException("Number of objectives cannot be different");
			}			
		}
		
		StringBuffer buffer = new StringBuffer();
		
		if (numberOfObjectives == 2) {
			buffer.append("plot ");
		} else if (numberOfObjectives == 3) {
			buffer.append("splot ");
		} else {
			throw new IllegalArgumentException("Number of objectives should be two or three");
		}	
		
		for (String file : files) {
			buffer.append("'-' title \"" + file + "\", ");
		}
		
		buffer.append("\n");
		
		for(String file : files){
			logger.info("Processing " + file);
			
			SolutionSet population = SolutionSetUtils.getFromFile(new File(file));
			
			for (int i = 0; i < population.size(); i++) {
				Solution s = population.get(i);

				for (int j = 0; j < s.getNumberOfObjectives(); j++) {
					buffer.append(s.getObjective(j));
					
					if (j + 1 != s.getNumberOfObjectives()) {
						buffer.append(" ");
					}
				}
				buffer.append("\n");
			}
			
			buffer.append("EOF \n");
			
			updateProgress();
		}
		
		logger.info("Saving the file");
	
		FileUtils.writeStringToFile(outputFolder, buffer.toString());
		
		logger.info("Done");
		
		return null;
	}
	
	@Override
	protected void done() {
		try {
			get();
			MessageBox.info(parent, "Done");			
		} catch (Exception e) {
			if (!(e instanceof CancellationException)) {
				e.getCause().printStackTrace();
				String msg = String.format("Unexpected problem: %s", e.getCause().toString());
				MessageBox.error(parent, msg);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Export FUNALL Files to GNUPLOT File";
	}
}
