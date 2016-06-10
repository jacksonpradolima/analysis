package br.ufpr.inf.cbiogres.action;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import br.ufpr.inf.cbiogres.window.DetailsWindow;
import br.ufpr.inf.cbiogres.window.MainWindow;
import br.ufpr.inf.cbiogres.indicator.Indicator;
import br.ufpr.inf.cbiogres.util.IndicatorUtils;

/**
 * Details Action
 * 
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-11-03
 *
 */
public class DoDetailsAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoDetailsAction(JFrame parent){
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		MainWindow window = (MainWindow) parent;
		
		if (window.getResultTable().getSelectedRowCount() == 0) {
			throw new IllegalArgumentException("You must to select at least a line");
		}
		
		List<Indicator> indicators = IndicatorUtils.getIndicatorList();
		
		String[] names = new String[indicators.size()];
			
		for (int i = 0; i < indicators.size(); i++) {
			names[i] = indicators.get(i).getName();
		}
		
		String selectedIndicator = (String) JOptionPane.showInputDialog(parent, "Select the indicator", "Details",	JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

		if (selectedIndicator == null) {
			return;
		}
		
		Indicator indicator = IndicatorUtils.getIndicator(selectedIndicator);

		if (indicator == null) {
			throw new IllegalArgumentException("Indicator not found");
		}
		
		String folderName = window.getFolder().getAbsolutePath();
		
		List<String> files = window.getResultTable().getSelectedFolderFiles();
		
		DetailsWindow w = new DetailsWindow(parent, indicator, folderName, files);
		w.showOptionDialog();		
	}
}
