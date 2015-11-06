package org.thiagodnf.analysis.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.thiagodnf.analysis.gui.window.MessageBox;

public abstract class AsyncTask extends SwingWorker<Object, Object>{
	
	protected JFrame parent;
	
	protected ProgressMonitor monitor;
	
	protected int progress = 1;
	
	protected List<AsyncTask> pendingAsyncTask;
	
	public AsyncTask(JFrame parent) {
		super();
		
		this.parent = parent;
		this.pendingAsyncTask = new ArrayList<AsyncTask>();
		this.monitor = new ProgressMonitor(parent, toString(), "...", 0, 10);
		this.monitor.setMillisToDecideToPopup(0);
		this.monitor.setMillisToPopup(0);
	}
	
	public void updateNote(String note) {
		this.monitor.setNote(note);

		if (monitor.isCanceled() || isCancelled()) {
			throw new CancellationException();
		}
	}
	
	public void updateMaximum(int maximum) {
		this.monitor.setMaximum(maximum);

		this.progress = 1;

		if (monitor.isCanceled() || isCancelled()) {
			throw new CancellationException();
		}
	}
	
	public void updateProgress() {
		updateProgress(progress++);		
	}
	
	public void updateProgress(int progress) {
		this.monitor.setProgress(progress);

		if (monitor.isCanceled() || isCancelled()) {
			throw new CancellationException();
		}
	}
	
	public int getCurrentProgress(){
		return progress; 
	}
	
	public void beforeStarting() {
		updateProgress();
	}

	public void afterFinishing() {
		updateProgress(monitor.getMaximum());
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
	
	public abstract String toString();
}
