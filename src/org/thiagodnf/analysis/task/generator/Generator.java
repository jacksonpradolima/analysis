package org.thiagodnf.analysis.task.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.gui.window.MainWindow;
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
		try {
			get();

			if (pendingGenerator.isEmpty()) {
				MessageBoxWindow.info(parent, "Done");
				((MainWindow) parent).getResultTable().reload();
			} else {
				Generator gen = pendingGenerator.remove(0);
				gen.setPendingGenerator(pendingGenerator);
				gen.execute();
			}
		} catch (Exception e) {
			if (!(e instanceof CancellationException)) {
				e.getCause().printStackTrace();
				String msg = String.format("Unexpected problem: %s", e.getCause().toString());
				MessageBoxWindow.error(parent, msg);
			}
		}
	}
		
	protected List<String> getFilesStartingWith(File[] folders, String startWith) {
		return getFilesStartingWith(folders, startWith, null);
	}
	
	protected List<String> getFilesStartingWith(File[] folders, String startWith, String ignore) {
		List<String> files = new ArrayList<String>();

		for (File folder : folders) {
			files.addAll(FilesUtils.getFiles(folder, startWith, ignore));
		}
		
		if (files.isEmpty()) {
			throw new IllegalArgumentException(startWith + " files have not been found");
		}

		return files;
	}
}
