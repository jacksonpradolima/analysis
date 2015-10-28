package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;
import org.thiagodnf.core.util.FilesUtils;

import jmetal.core.SolutionSet;

public abstract class Generator extends AsyncTask {
	
	protected final static Logger logger = LoggerUtils.getLogger(Generator.class.getName());
	
	protected File[] folders;

	public Generator(JFrame parent, File[] folders) {
		super(parent);
		this.folders = folders;
	}
	
	protected List<String> getFilesStartingWith(File[] folders, String startWith, String ignore) {
		updateMaximum(folders.length);

		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, startWith, ignore));
		}

		return files;
	}
}
