package org.thiagodnf.analysis.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.thiagodnf.core.util.FilesUtils;

public class LoadFilesThatStartingWithTask extends AsyncTask {

	protected File[] folders;
	
	protected String startWith;
	
	public LoadFilesThatStartingWithTask(JFrame parent, File[] folders, String startWith) {
		super(parent);
		
		this.folders = folders;
		this.startWith = startWith;
	}

	@Override
	protected Object doInBackground() throws Exception {
		return getFiles();
	}
	
	public List<String> getFiles(){
		
		updateMaximum(folders.length);
		
		List<String> files = new ArrayList<String>();

		beforeStarting();
		
		for (File folder : folders) {
			updateNote(folder.getAbsolutePath());

			files.addAll(FilesUtils.getFiles(folder, startWith));

			updateProgress();			
		}
		
		return files;
	}

	@Override
	public String toString() {
		return "Loading files";
	}
}
