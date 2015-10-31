package org.thiagodnf.analysis.gui.window;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.gui.action.CloseTabAction;
import org.thiagodnf.analysis.gui.action.OpenFoldersAction;
import org.thiagodnf.analysis.gui.action.PreferencesAction;
import org.thiagodnf.analysis.gui.action.RunGeneratorsAction;
import org.thiagodnf.analysis.gui.action.SearchAction;
import org.thiagodnf.analysis.gui.action.ViewDetailsAction;
import org.thiagodnf.analysis.gui.component.ResultTab;
import org.thiagodnf.core.util.ImageUtils;

import com.google.common.base.Preconditions;

public class MainWindow extends JFrame {
	
	protected final static Logger logger = Logger.getLogger(MainWindow.class.getName()); 
	
	private static final long serialVersionUID = 7125089003272880135L;
	
	protected JTabbedPane tabbedPane;
	
	protected List<String> folders;
	
	private List<String> filter = new ArrayList<String>();
	
	public MainWindow() {
		this.folders = new ArrayList<String>();
		
		//Settings
		setTitle("Analysis");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		JPopupMenu.setDefaultLightWeightPopupEnabled( false );
		
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
		
		toolBar.add(getNewToolBarButton("Open","folder.png", new OpenFoldersAction(this)));
		toolBar.addSeparator();
//		toolBar.add(getNewToolBarButton("Stats", "statistics.png", new RunGeneratorsAction(this)));
		toolBar.add(getNewToolBarButton("Generator", "design.png", new RunGeneratorsAction(this)));
		toolBar.add(getNewToolBarButton("View Details", "inbox.png", new ViewDetailsAction(this)));
		toolBar.add(getNewToolBarButton("Statistical Test", "statistics.png", new RunGeneratorsAction(this)));
		
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("Search", "search.png", new SearchAction(this)));
		toolBar.add(getNewToolBarButton("Filter", "filter.png", new RunGeneratorsAction(this)));
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("Close Tab", "close.png", new CloseTabAction(this)));
		toolBar.addSeparator();
		toolBar.add(getNewToolBarButton("Settings", "gear.png", new PreferencesAction(this)));
		
		// Add toolbar at window
		add(toolBar, BorderLayout.NORTH);
	}
	
	protected void addComponents(){
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);		
		getContentPane().add(tabbedPane);
	}
	
	
	public void reloadFolder() throws IOException{
		Preconditions.checkArgument(!folders.isEmpty(), "You need to load a folder first");
				
		for (String folder : folders) {
			String name = FilenameUtils.getName(folder);
			
			if (!tabContains(folder)) {
				this.tabbedPane.addTab(name, new ResultTab(this, folder));				
			}else{
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					((ResultTab) tabbedPane.getComponent(i)).load();
				}
			}
		}
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
	
	protected JButton getNewToolBarButton(String name, String icon, AbstractAction action){
		JButton button = new JButton(new ImageIcon(ImageUtils.getFromFile(this, icon)));
		
		button.setText(name);
		button.addActionListener(action);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
	    button.setHorizontalTextPosition(SwingConstants.CENTER);

		return button;
	}
	
	public List<String> getFolders() {
		return this.folders;
	}

	public List<String> getFilter() {
		return filter;
	}
	
	public ResultTab getSelectedTab() {
		return (ResultTab) this.tabbedPane.getSelectedComponent();
	}
	
	public void removeSelectedTab(){
		if(tabbedPane.getTabCount() == 0){
			throw new IllegalArgumentException("You need to open a folder first");
		}
		
		this.folders.remove(tabbedPane.getSelectedIndex());
		this.tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());		
	}
}
