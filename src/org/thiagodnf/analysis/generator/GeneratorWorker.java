package org.thiagodnf.analysis.generator;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.util.LoggerUtils;

@SuppressWarnings("rawtypes")
public abstract class GeneratorWorker extends SwingWorker{

	protected final static Logger logger = LoggerUtils.getLogger(GeneratorWorker.class.getName());
	
	protected ProgressMonitor monitor;
	
	protected int progress;
	
	protected JFrame parent;
	
	protected File[] folders;
	
	protected List<AbstractGenerator> generators;
	
	public GeneratorWorker(JFrame parent) {
		this.parent = parent;
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
	
	public void showMessage(String note) {
		showMessage(note, 10000);
	}

	public void showMessage(String note, int maximum) {
		updateMaximum(maximum);
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
	
	@Override
	protected void done() {
		if (generators.isEmpty()) {
			MessageBoxWindow.info(parent, "Done");
		} else {
			AbstractGenerator generator = generators.remove(0);

			generator.setGenerators(generators);

			generator.run(folders);
		}		
	}
		
	public void setGenerators(List<AbstractGenerator> generators) {
		this.generators = generators;		
	}
	
	public void run(File[] folders) {
		this.folders = folders;
		
		monitor = new ProgressMonitor(parent, "Running " + this, "...", 0, 10);
		monitor.setMillisToDecideToPopup(0);
		monitor.setMillisToPopup(0);
		
		logger.info("==================================================================");
		logger.info("Starting " + toString());
		logger.info("==================================================================");
		
		execute();		
	}
	
}
