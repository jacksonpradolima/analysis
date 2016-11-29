package br.ufpr.inf.gres.gui.task.export;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTable;

import org.apache.commons.io.FileUtils;
import br.ufpr.inf.gres.gui.component.ResultTable;
import br.ufpr.inf.gres.gui.task.AsyncTask;
import br.ufpr.inf.gres.core.util.LoggerUtils;

public class ExportFromJTableToCsvTask extends AsyncTask {

    protected static final Logger logger = LoggerUtils.getLogger(ExportFromJTableToCsvTask.class.getName());

    protected JTable inputTable;

    protected File outputFolder;

    public ExportFromJTableToCsvTask(JFrame parent, File outputFolder, JTable inputTable) {
        super(parent);

        this.inputTable = inputTable;
        this.outputFolder = outputFolder;
    }

    @Override
    protected Object doInBackground() throws Exception {

        logger.info("Exporting selected rows from jtable to csv table");

        List<Integer> selectedRows = ((ResultTable) inputTable).getMarkedRows();

        if (selectedRows.isEmpty()) {
            throw new IllegalArgumentException("You must select at least a row");
        }

        StringBuffer buffer = new StringBuffer();

        // Process the columns
        String header = "";
        String col = "";

        for (int i = 1; i < inputTable.getColumnCount(); i++) {
            col += inputTable.getColumnName(i);
            if (i + 1 != inputTable.getColumnCount()) {
                col += ";";
            }
        }
        
        buffer.append(col).append("\n");

        for (int i = 0; i < selectedRows.size(); i++) {

            for (int j = 1; j < inputTable.getColumnCount(); j++) {
                buffer.append(inputTable.getValueAt(selectedRows.get(i), j).toString());

                if (j + 1 != inputTable.getColumnCount()) {
                    buffer.append(" ; ");
                }
            }   
            
            buffer.append("\n");
        }
        
        FileUtils.writeStringToFile(outputFolder, buffer.toString());

        logger.info("Done");

        return null;
    }

    @Override
    public String toString() {
        return "Export From Table to CSV";
    }
}
