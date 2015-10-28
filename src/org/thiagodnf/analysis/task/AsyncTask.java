package org.thiagodnf.analysis.task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.thiagodnf.analysis.generator.Generator;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;

public abstract class AsyncTask extends SwingWorker<Object, Object> implements PropertyChangeListener {
	
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
		
		this.addPropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equalsIgnoreCase("canceled")) {
			throw new IllegalArgumentException("Canceled");
		}
	}
	
	public void updateNote(String note) {
		this.monitor.setNote(note);

		if (monitor.isCanceled() || isCancelled()) {
			firePropertyChange("canceled", null, null);
		}
	}
	
	public void updateMaximum(int maximum) {
		this.monitor.setMaximum(maximum);

		this.progress = 1;

		if (monitor.isCanceled() || isCancelled()) {
			firePropertyChange("canceled", null, null);
		}
	}
	
	public void updateProgress() {
		updateProgress(progress++);		
	}
	
	public void updateProgress(int progress) {
		this.monitor.setProgress(progress);

		if (monitor.isCanceled() || isCancelled()) {
			firePropertyChange("canceled", null, null);
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
		if (pendingAsyncTask.isEmpty()) {
			MessageBoxWindow.info(parent, "Done");
		} else {
			AsyncTask task = pendingAsyncTask.remove(0);
			task.setPendingAsyncTask(pendingAsyncTask);
			task.execute();
		}
	}

	public abstract String toString();

	public List<AsyncTask> getPendingAsyncTask() {
		return pendingAsyncTask;
	}

	public void setPendingAsyncTask(List<AsyncTask> pendingAsyncTask) {
		this.pendingAsyncTask = pendingAsyncTask;
	}
}
