package org.thiagodnf.analysis.task.export;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTable;

import org.apache.commons.io.FileUtils;
import org.thiagodnf.analysis.gui.component.ResultTable;
import org.thiagodnf.analysis.task.AsyncTask;
import org.thiagodnf.analysis.util.LoggerUtils;

public class ExportFromJTableToLatexTableTask extends AsyncTask {

	protected static final Logger logger = LoggerUtils.getLogger(ExportFromJTableToLatexTableTask.class.getName());
	
	protected JTable inputTable;
	
	protected File outputFolder;
	
	public ExportFromJTableToLatexTableTask(JFrame parent, File outputFolder, JTable inputTable) {
		super(parent);

		this.inputTable = inputTable;
		this.outputFolder = outputFolder;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		logger.info("Exporting selected rows from jtable to latex table");
		
		List<Integer> selectedRows = ((ResultTable) inputTable).getMarkedRows();
		
		if (selectedRows.isEmpty()) {
			throw new IllegalArgumentException("You must select at least a row");
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("\\begin{table}[!htb]").append("\n");
		buffer.append("\\renewcommand{\\arraystretch}{1.5}").append("\n");
		buffer.append("\\caption{Your caption}").append("\n");
		buffer.append("\\label{table:your-label}").append("\n");
		buffer.append("\\centering").append("\n");
		
		// Process the columns
		
		String header = "";
		String col = "";
		
		for (int i = 1; i < inputTable.getColumnCount(); i++) {
			header += "c";
			col += "\\textbf{"+inputTable.getColumnName(i)+"}";
			if (i + 1 != inputTable.getColumnCount()) {
				header += " | ";
				col += " & ";
			}
		}
		
		buffer.append("\\begin{tabular}{").append(header).append("}").append("\n");
		buffer.append("\\toprule").append("\n");
		buffer.append(col).append("\\\\").append("\n");
		buffer.append("\\midrule").append("\n");
				
		
		for (int i = 0; i < selectedRows.size(); i++) {

			for (int j = 1; j < inputTable.getColumnCount(); j++) {
				buffer.append(inputTable.getValueAt(selectedRows.get(i), j).toString().replace("_", "\\_"));
				
				if (j + 1 != inputTable.getColumnCount()) {
					buffer.append(" & ");
				}
			}
			
			buffer.append("\\\\").append("\n");
			buffer.append("\\midrule").append("\n");			
		}
		
		buffer.append("\\bottomrule").append("\n");
		
		
//		for(Integer row : selectedRows){
//			buffer.append(row);
//			
//					\begin{tabular}{ c | c | c | c}
//						\toprule		
//						
//						\textbf{Parameter} & \textbf{MOEA/D-UCB} & \textbf{MOEA/D-RAND} & \textbf{MOEA/D-DRA}\\ 
//						
//						\midrule
//						C & 5.0 & --- & --- \\ \midrule 
//						WS & 25\% of pop. size & --- & --- \\ \midrule
//						$\delta$ & 0.9 & 0.9 & 0.9 \\ \midrule 
//						W & 10\% of pop. size & 10\% of pop. size & 10\% of pop. size \\ \midrule 
//						$n_r$ & 1\% of pop. size & 1\% of pop. size  & 1\% of pop. size  \\ \midrule
//						$D$ & 1.0 & --- & ---\\ \midrule
//						Selection Operator & UCB & Random & Random\\ \midrule
//						Crossover Operator & --- & --- & Uniform \\ \midrule
//						Mutation Operator & --- & --- & Bit Flip \\  \midrule
//						Prob. Crossover & --- & --- & 100\% \\ \midrule
//						Prob. Mutation & --- & --- & 1\% \\ \midrule
//						Fitness & Tchebycheff & Tchebycheff & Tchebycheff\\ 
//
//						\bottomrule
//		}
		
		buffer.append("\\end{tabular}").append("\n");
		buffer.append("\\end{table}").append("\n");
	
		FileUtils.writeStringToFile(outputFolder, buffer.toString());
		
		logger.info("Done");
		
		return null;
	}
	
	@Override
	public String toString() {
		return "Export From Table to Latex Table";
	}
}
