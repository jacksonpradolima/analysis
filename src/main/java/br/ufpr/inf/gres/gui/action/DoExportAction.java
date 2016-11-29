package br.ufpr.inf.gres.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.ufpr.inf.gres.gui.window.MainWindow;
import br.ufpr.inf.gres.gui.task.AsyncTask;
import br.ufpr.inf.gres.gui.task.ExportType;
import br.ufpr.inf.gres.gui.task.export.ExportFromFUNALLToGnuplotTask;
import br.ufpr.inf.gres.gui.task.export.ExportFromJTableToCsvTask;
import br.ufpr.inf.gres.gui.task.export.ExportFromJTableToLatexTableTask;
import br.ufpr.inf.gres.gui.task.export.ExportHVFromJTableToFriedmanLatexTableTask;
import br.ufpr.inf.gres.gui.task.export.ExportHVFromJTableToFriedmanTestTask;
import br.ufpr.inf.gres.gui.task.export.ExportHVFromJTableToKruskalWallisTestTask;

/**
 * This class is responsible for show a prompt where the user must to
 * choose which export operation him/her will be performs.
 * 
 * @author Thiago Nascimento
 * @version 1.1.0
 * @since 2015-11-03
 */
public class DoExportAction extends DoAction {

	private static final long serialVersionUID = -2332276187918581439L;
	
	public DoExportAction(JFrame parent) {
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		MainWindow window = (MainWindow) parent;
		
		if (window.getResultTable().getSelectedRowCount() == 0) {
			throw new IllegalArgumentException("You must to select at least a line");
		}
		
		List<String> options = new ArrayList<>();
		
		options.add(ExportType.FunAllGnuPlot.toString());
                options.add(ExportType.RowHVToFriedmanCSV.toString());
                options.add(ExportType.RowHVToFriedmanLatex.toString());
                options.add(ExportType.RowHVToKruskalWallisCSV.toString());
                options.add(ExportType.RowToCSV.toString());    
		options.add(ExportType.RowToLatex.toString());                            
		
		String[] names = new String[options.size()];
			
		for (int i = 0; i < options.size(); i++) {
			names[i] = options.get(i);
		}
		
		String selected = (String) JOptionPane.showInputDialog(parent, null, "Export", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

		if (selected.isEmpty()) {
			return;
		}
		
		List<String> folders = window.getResultTable().getSelectedFolderFiles();
		
		JFileChooser fc = new JFileChooser();
		
		if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			
			File outputFolder = fc.getSelectedFile();
			
			AsyncTask task = null;
			
                        ExportType exportType = ExportType.getEnum(selected);
                        
                        String folderName = window.getFolder().getAbsolutePath();
                        
                        List<String> files = window.getResultTable().getSelectedFolderFiles();
                        
                        switch(exportType){
                            case FunAllGnuPlot:
				task = new ExportFromFUNALLToGnuplotTask(parent, folders, outputFolder);
                                break;
                            case RowHVToFriedmanCSV:
				task = new ExportHVFromJTableToFriedmanTestTask(parent, outputFolder, window.getResultTable());
                                break;
                            case RowHVToFriedmanLatex:
				task = new ExportHVFromJTableToFriedmanLatexTableTask(parent, outputFolder, window.getResultTable());
                                break;
                            case RowHVToKruskalWallisCSV:
				task = new ExportHVFromJTableToKruskalWallisTestTask(parent, outputFolder, window.getResultTable(), folderName, files);
                                break;
                            case RowToCSV:
				task = new ExportFromJTableToCsvTask(parent, outputFolder, window.getResultTable());
                                break;
                            case RowToLatex:
				task = new ExportFromJTableToLatexTableTask(parent, outputFolder, window.getResultTable());
                                break;
			}
			
			task.execute();			
		}		
	}                
}
