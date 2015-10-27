package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import com.google.common.base.Preconditions;

@SuppressWarnings("rawtypes")
public abstract class Generator extends SwingWorker {
	
	protected final static Logger logger = Logger.getLogger(Generator.class.getName());
	
	protected ProgressMonitor monitor;
	
	protected int progress;
	
	protected File[] folders;
	
	public void run(JFrame parent, File[] folders, int numberOfObjectives) {
		Preconditions.checkArgument(numberOfObjectives > 0, "Number of Objectives cannot be less than zero");

		this.folders = folders;
		
		monitor = new ProgressMonitor(parent, "Running " + this, "...", 0, 10);
		monitor.setMillisToDecideToPopup(0);
		monitor.setMillisToPopup(0);
		
		execute();		
	}
	
	public void updateMaximum(int maximum) {
		monitor.setMaximum(maximum);
		
		this.progress = 1;

		if (monitor.isCanceled()) {
			throw new IllegalArgumentException("Canceled");
		}
	}
		
	public void updateProgress(int progress){
		monitor.setProgress(progress);
		
		if (monitor.isCanceled()) {
			throw new IllegalArgumentException("Canceled");
		}
	}
	
	public void updateProgress(String note) {
		updateProgress(note, this.progress++);
	}
	
	public void updateProgress(String note, int progress){
		updateProgress(progress);
		updateNote(note);
	}
	
	public void showMessage(String note){
		updateMaximum(10000);
		updateProgress(note);
	}
	
	public void hideMessage(){
		updateMaximum(monitor.getMaximum());
	}
	
	public void updateNote(String note){
		monitor.setNote(note);
		
		logger.info(note);
		
		if (monitor.isCanceled()) {
			throw new IllegalArgumentException("Canceled");
		}
	}
}
