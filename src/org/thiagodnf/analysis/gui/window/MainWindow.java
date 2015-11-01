package org.thiagodnf.analysis.gui.window;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.thiagodnf.analysis.gui.action.DoAboutAction;
import org.thiagodnf.analysis.gui.action.DoFilterAction;
import org.thiagodnf.analysis.gui.action.DoOpenFoldersAction;
import org.thiagodnf.analysis.gui.action.DoPreferencesAction;
import org.thiagodnf.analysis.gui.action.DoRunGeneratorsAction;
import org.thiagodnf.analysis.gui.action.DoSearchAction;
import org.thiagodnf.analysis.gui.action.DoStatisticalTestAction;
import org.thiagodnf.analysis.gui.action.DoViewDetailsAction;
import org.thiagodnf.analysis.gui.component.FolderTree;
import org.thiagodnf.analysis.gui.component.ResultTable;
import org.thiagodnf.core.util.ImageUtils;

public class MainWindow extends JFrame {
	
	protected final static Logger logger = Logger.getLogger(MainWindow.class.getName()); 
	
	private static final long serialVersionUID = 7125089003272880135L;
	
	protected FolderTree tree;
	
	protected ResultTable table;
	
	protected File folder;
	
	private List<String> filter = new ArrayList<String>();
	
	public MainWindow() {
		//Settings
		setTitle("Analysis");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		// Start app with maximized window
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		// Default the app icon
		setIconImage(ImageUtils.getFromFile(this, "logo.png"));

		addToolBar();
		addComponents();		
	}
	
	protected void addToolBar(){
		// Create the toolbar
		JToolBar toolBar = new JToolBar();
		
		//The user cannot move the toolbar. The toolbar is fixed at top
		toolBar.setFloatable(false);
		
		toolBar.add(getNewToolBarButton("Open","folder.png", new DoOpenFoldersAction(this)));
		toolBar.addSeparator();

		toolBar.add(getNewToolBarButton("Generator", "design.png", new DoRunGeneratorsAction(this)));

		toolBar.add(getNewToolBarButton("View Details", "inbox.png", new DoViewDetailsAction(this)));
		toolBar.add(getNewToolBarButton("Statistical Test", "statistics.png", new DoStatisticalTestAction(this)));		
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("Search", "search.png", new DoSearchAction(this)));
		toolBar.add(getNewToolBarButton("Filter", "filter.png", new DoFilterAction(this)));
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("Settings", "gear.png", new DoPreferencesAction(this)));
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("About", "info.png", new DoAboutAction(this)));
		
		// Add toolbar at window
		add(toolBar, BorderLayout.NORTH);
	}
	
	protected void addComponents(){
		// Create all components
		this.tree = new FolderTree(this);		
		this.table = new ResultTable(this);
		
		int orientation = JSplitPane.HORIZONTAL_SPLIT;
		JScrollPane treeSp = new JScrollPane(tree);
		JScrollPane tableSp = new JScrollPane(table);
		
		JSplitPane splitPane = new JSplitPane(orientation, treeSp, tableSp);
		
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		
		getContentPane().add(splitPane);
	}
	
	public void openFolder() throws Exception {
		if (folder == null) {
			return;
		}
		
		tree.setModel(tree.getLoadedModel(folder));
	}
		
	protected JButton getNewToolBarButton(String name, String icon, AbstractAction action){
		JButton button = new JButton(new ImageIcon(ImageUtils.getFromFile(this, icon)));
		
		button.setText(name);
		button.addActionListener(action);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
	    button.setHorizontalTextPosition(SwingConstants.CENTER);

		return button;
	}
	
	public File getFolder() {
		return this.folder;
	}
	
	public void setFolder(File folder) {
		this.folder = folder;
	}

	public List<String> getFilter() {
		return filter;
	}
	
	public ResultTable getResultTable() {
		return this.table;
	}
}
