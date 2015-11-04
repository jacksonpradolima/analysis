package org.thiagodnf.analysis.gui.component;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBox;

public class FolderTree extends JTree implements TreeSelectionListener{

	private static final long serialVersionUID = 1387208553052811920L;
	
	protected JFrame parent;
	
	protected boolean isLoadingTree;
	
	public FolderTree(JFrame parent){
		this.parent = parent;
		this.isLoadingTree = false;
		
		// Start the component empty
		setModel(null);
		
		// Define minimum size
		setMinimumSize(new Dimension(100, 100));
		
		addTreeSelectionListener(this);
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//Ignore this event when the user open a new folder
		if(isLoadingTree) return;
				
		DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		
		// Read the depth using the parent
		List<String> levels = new ArrayList<String>();
		
		while(curNode != null){
			levels.add(levels.size(), curNode.toString());
			curNode = (DefaultMutableTreeNode) curNode.getParent();
		}
		
		levels.remove(levels.size()-1);
		
		// Invert the level list
		Collections.reverse(levels);
		
		String directory = ((MainWindow) parent).getFolder().getAbsolutePath();
		
		for (String level : levels) {
			directory += File.separator + level;
		}
		
		try {
			((MainWindow) parent).getResultTable().load(new File(directory));
		} catch (IOException ex) {
			MessageBox.error(parent, ex);
		}
	}
	
	public DefaultTreeModel getLoadedModel(File folder) {
		return new DefaultTreeModel(addNodes(null, folder));
	}

	/**
	 * Add nodes from under "dir" into curTop. Highly recursive.
	 */
	protected DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
		
		String curPath = dir.getPath();

		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(FilenameUtils.getName(curPath));

		// should only be null at root
		if (curTop != null) {
			curTop.add(curDir);
		}

		String[] files = dir.list();

		for (String file : files) {
			String newPath = curPath + File.separator + file;

			if (new File(newPath).isDirectory()) {
				addNodes(curDir, new File(newPath));
			}
		}

		return curDir;
	}

	public void setLoadingTree() {
		this.isLoadingTree = true;		
	}

	public void setTreeLoaded() {
		this.isLoadingTree = false;		
	}
}