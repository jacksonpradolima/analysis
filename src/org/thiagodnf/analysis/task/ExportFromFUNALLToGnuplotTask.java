package org.thiagodnf.analysis.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import javax.swing.JFrame;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBox;
import org.thiagodnf.analysis.util.SolutionSetUtils;
import org.thiagodnf.core.util.FilesUtils;

public class ExportFromFUNALLToGnuplotTask extends AsyncTask {

	protected List<String> folders;
	
	protected File outputFolder;
	
	public ExportFromFUNALLToGnuplotTask(JFrame parent, List<String> folders, File outputFolder) {
		super(parent);		
		this.folders = folders;
		this.outputFolder = outputFolder;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		List<String> files = new ArrayList<String>();
		
		for (String folder : folders) {
			File fullpath = new File(FilenameUtils.getFullPath(folder));
			files.addAll(FilesUtils.getFiles(fullpath, "FUNALL"));
		}
		
		StringBuffer buffer = new StringBuffer("plot ");
		
		for (String file : files) {
			String dir = ((MainWindow) parent).getResultTable().getDirectory().getAbsolutePath();
			String filename = file.replaceAll("FUNALL", "").replaceAll(dir, "");
			buffer.append("'-' title \"" + filename + "\", ");
		}
		
		buffer.append("\n");
		
		for(String file : files){
			
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
		}
	
		FileUtils.writeStringToFile(outputFolder, buffer.toString());
		
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
		return "Loading files";
	}
}
