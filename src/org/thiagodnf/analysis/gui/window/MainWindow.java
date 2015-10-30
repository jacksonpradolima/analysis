package org.thiagodnf.analysis.gui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.gui.action.OpenFoldersAction;
import org.thiagodnf.analysis.gui.action.ReloadAction;
import org.thiagodnf.analysis.gui.action.RunGeneratorsAction;
import org.thiagodnf.analysis.gui.component.ResultTab;
import org.thiagodnf.core.util.ImageUtils;

import com.google.common.base.Preconditions;

public class MainWindow extends JFrame implements ActionListener{
	
	protected final static Logger logger = Logger.getLogger(MainWindow.class.getName()); 
	
	private static final long serialVersionUID = 7125089003272880135L;
	
	protected JTabbedPane tabbedPane;
	
	protected List<String> folders;
	
	private List<String> filter = new ArrayList<String>();
	
	public MainWindow() {
		this.folders = new ArrayList<String>();
		
		//Settings
		setTitle("GrES Experiments");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		JPopupMenu.setDefaultLightWeightPopupEnabled( false );
		
		// Default the app icon
		setIconImage(ImageUtils.getFromFile(this, "logo.png"));

		addMenus();
		addToolBar();
		addComponents();		
	}
	
	protected void addToolBar(){
		// Create the toolbar
		JToolBar toolBar = new JToolBar();
		
		//The user cannot move the toolbar. The toolbar is fixed at top
		toolBar.setFloatable(false);
		
		JTextField searchField = new JTextField(20);
		searchField.setActionCommand("search");
		
//		PromptSupport.setPrompt("Search", searchField);
//		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, searchField);
		
		searchField.addActionListener(this);
		searchField.setMinimumSize( new Dimension( 238, 300 ) );
        
		toolBar.add(getNewToolBarButton("folder.png", new OpenFoldersAction(this)));
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("statistics.png", "Run Statistical Test", "statistical-test"));
		toolBar.add(getNewToolBarButton("pencil.png", new RunGeneratorsAction(this)));
		toolBar.add(Box.createHorizontalGlue() );
		toolBar.add(searchField);
		
		// Add toolbar at window
		add(toolBar, BorderLayout.NORTH);
	}
	
	protected void addComponents(){
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
	}
	
	protected void addMenus(){
		// Edit Menu
		JMenu editMenu = new JMenu("Edit");
		
		editMenu.add(getNewMenuItem("Copy", "copy", "Copy the selected rows to clipboard"));
		editMenu.addSeparator();
		editMenu.add(getNewMenuItem("Select All", "select-all", "Select all rows in a tab"));
		editMenu.add(getNewMenuItem("Clear Selection", "clear-selection", "Unselect the rows in a tab"));
		
		// Filter Menu
		JMenu filterMenu = new JMenu("Filter");
		filterMenu.add(getNewMenuItem("Filter By Selected Rows", "filter", "Filter the rows"));
		filterMenu.addSeparator();
		filterMenu.add(getNewMenuItem("Filter By Best Algorithm", "filter-best-settings", "Filter By Best Algorithm"));
		
		// Edit Menu
		JMenu runMenu = new JMenu("Run");
		
		runMenu.add(getNewMenuItem("Generators", new RunGeneratorsAction(this)));
		runMenu.addSeparator();
		runMenu.add(getNewMenuItem("Statistical Test", "statistical-test", "Run Statistical Test"));
		
		// File Menu
		
		JMenu fileMenu = new JMenu("File");
		
		JMenu exportMenu = new JMenu("Export");
		
		exportMenu.add(getNewMenuItem("CSV", "export-to-csv", "CSV File"));
		exportMenu.add(getNewMenuItem("Gnuplot", "export-to-gnuplot", "Gnuplot File"));
		//exportMenu.add(getNewMenuItem("Metric", "export-to-metric", "Metric File"));
		
		JMenuItem closeMenuItem = new JMenuItem("Close");
		closeMenuItem.setActionCommand("close");
		closeMenuItem.addActionListener(this);
		
		fileMenu.add(getNewMenuItem("Open", new OpenFoldersAction(this)));
		fileMenu.addSeparator();
		fileMenu.add(exportMenu);
		fileMenu.addSeparator();
		fileMenu.add(getNewMenuItem("Reload", new ReloadAction(this)));
		fileMenu.addSeparator();
		fileMenu.add(closeMenuItem);
				
		//Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
				
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(filterMenu);
		menuBar.add(runMenu);
		
		setJMenuBar(menuBar);		
	}
	
	public void reloadFolder() throws IOException{
		Preconditions.checkArgument(!folders.isEmpty(), "You need to load a folder first");
		
		List<String> files = new ArrayList<String>();
		
//		for (String folder : currentFolders) {
//			files.addAll(FilesUtils.getFiles(folder.getAbsoluteFile(), "SUMMARY"));
//		}
		
		System.out.println(files);
		
//		if (files.isEmpty()) {
//			throw new IllegalArgumentException("No SUMMARY file was found");
//		}
		
		for (String folder : folders) {
			String name = FilenameUtils.getName(folder);
			
			if (!tabContains(folder)) {
				this.tabbedPane.addTab(name, new ResultTab(this, folder));
				
			}
		}
//		
//		Map<String, Properties> map = new HashMap<String, Properties>();
//		
//		for (String filename : files) {
//			map.put(filename, PropertiesUtils.getFromFile(filename));
//		}
//		
//		logger.info(files.size() + " files was loaded");
//
//		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
//			getTabAt(i).load(filter, map);
//		}
	}
	
	protected boolean tabContains(String name) {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			ResultTab resultTab = (ResultTab) tabbedPane.getComponent(i);
			if (resultTab.getFolderName().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}
	
	protected JMenuItem getNewMenuItem(String title, String actionCommand, String tooltip) {
		JMenuItem menuItem = new JMenuItem(title);

		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(this);
		menuItem.setToolTipText(tooltip);

		return menuItem;
	}
	
	protected JMenuItem getNewMenuItem(String title, AbstractAction action) {
		JMenuItem menuItem = new JMenuItem(title);

		menuItem.addActionListener(action);
		
		return menuItem;
	}
	
	protected JButton getNewToolBarButton(String icon, String tooltip, String actionCommand){
		JButton button = new JButton(new ImageIcon(ImageUtils.getFromFile(this, icon)));
		
		button.setToolTipText(tooltip);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		
		return button;
	}
	
	protected JButton getNewToolBarButton(String icon, AbstractAction action){
		JButton button = new JButton(new ImageIcon(ImageUtils.getFromFile(this, icon)));
		
		button.addActionListener(action);
		
		return button;
	}
	
	public List<String> getFolders() {
		return this.folders;
	}

	public List<String> getFilter() {
		return filter;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
//		try {
//			if (event.getActionCommand().equalsIgnoreCase("open")) {
//				new OpenListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("filter")){
//				new FilterListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("export-to-csv")){
//				new ExportToCSVListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("export-to-gnuplot")){
//				new ExportToGnuplotListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("clear-selection")){
//				new ClearSelectionListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("select-all")) {
//				new SelectAllListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("close")) {
//				new CloseAppListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("search")) {
//				new SearchListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("reload")) {
//				new ReloadListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("generators")) {
//				new GeneratorsListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("statistical-test")) {
//				new StatisticalTestListener(this).execute(event);
//			} else if (event.getActionCommand().equalsIgnoreCase("filter-best-settings")) {
//				new FilterBestSettingsByAlgorithmListener(this).execute(event);
//			}
//		} catch (Exception ex) {
//			AlertDialog.error(this, ex.getMessage());
//			ex.printStackTrace();
//		}
	}
}
