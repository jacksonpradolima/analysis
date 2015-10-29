package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.core.util.FilesUtils;

import com.google.common.base.Preconditions;

public abstract class Generator extends AsyncTask {
	
	protected final static Logger logger = LoggerUtils.getLogger(Generator.class.getName());
	
	protected File[] folders;
	
	protected List<Generator> pendingGenerator;

	public Generator(JFrame parent, File[] folders) {
		super(parent);
		
		Preconditions.checkNotNull(folders, "You need to select at least a folder");
		Preconditions.checkArgument(folders.length != 0, "You need to select at least a folder");
		
		this.folders = folders;
		this.pendingGenerator = new ArrayList<Generator>();
	}
	
	public void setPendingGenerator(List<Generator> pendingGenerator) {
		this.pendingGenerator = pendingGenerator;
	}
	
	@Override
	protected void done() {
		if (pendingGenerator.isEmpty()) {
			if (!monitor.isCanceled() && !isCancelled()) {
				MessageBoxWindow.info(parent, "Done");
			} else {
				if (throwException != null) {
					MessageBoxWindow.error(parent, throwException.getMessage());
				}
			}
		} else {
			Generator gen = pendingGenerator.remove(0);

			gen.setPendingGenerator(pendingGenerator);

			gen.execute();
		}
	}
		
	protected List<String> getFilesStartingWith(File[] folders, String startWith, String ignore) {
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, startWith, ignore));
		}
		
		if (files.isEmpty()) {
			cancel(true);
			throwException = new IllegalArgumentException(startWith + " files have not been found");
		}

		return files;
	}
}
