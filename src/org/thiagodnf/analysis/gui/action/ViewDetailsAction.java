package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.gui.window.MainWindow;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.gui.window.ViewDetailsWindow;
import org.thiagodnf.analysis.indicator.Indicator;
import org.thiagodnf.analysis.util.IndicatorUtils;

public class ViewDetailsAction extends AbstractAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	protected JFrame parent;
	
	public ViewDetailsAction(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow window = (MainWindow) parent;
		
		try{
			if (window.getResultTable().getSelectedRowCount() == 0) {
				throw new IllegalArgumentException("You must to select at least a line");
			}
			
			List<Indicator> indicators = IndicatorUtils.getIndicatorList();
			
			String[] names = new String[indicators.size()];
				
			for (int i = 0; i < indicators.size(); i++) {
				names[i] = indicators.get(i).getName();
			}
			
			String selectedIndicator = (String) JOptionPane.showInputDialog(parent,
					"Select the indicator", "View Details",
					JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

			if (selectedIndicator == null) {
				return;
			}
			
			Indicator indicator = IndicatorUtils.getIndicator(selectedIndicator);

			if (indicator == null) {
				throw new IllegalArgumentException("Indicator not found");
			}
			
			String folderName = window.getFolder().getAbsolutePath();
			
			List<String> files = window.getResultTable().getSelectedFolderFiles();
			
			ViewDetailsWindow w = new ViewDetailsWindow(parent, indicator, folderName, files);
			w.showOptionDialog();
		}catch(Exception ex){
			MessageBoxWindow.error(parent, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
