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
import br.ufpr.inf.gres.util.comparator.AlphanumComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExportHVFromJTableToFriedmanLatexTableTask extends AsyncTask {

    protected static final Logger logger = LoggerUtils.getLogger(ExportHVFromJTableToFriedmanLatexTableTask.class.getName());

    protected JTable inputTable;

    protected File outputFolder;

    public ExportHVFromJTableToFriedmanLatexTableTask(JFrame parent, File outputFolder, JTable inputTable) {
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

        // Process the columns        
        String col = "";

        for (int i = 1; i < inputTable.getColumnCount(); i++) {
            col += inputTable.getColumnName(i);
            if (i + 1 != inputTable.getColumnCount()) {
                col += ";";
            }
        }

        if (inputTable.getValueAt(selectedRows.get(0), 1).toString().split("/").length < 3) {
            throw new IllegalArgumentException("You must select the row in instance level");
        }

        // Find the HV column position in the window
        ArrayList<String> cols = new ArrayList<>(Arrays.asList(col.split(";")));
        int indexColHV = cols.indexOf("Hypervolume") + 1;

        ArrayList<TableValues> tableValues = new ArrayList<>();

        for (int i = 0; i < selectedRows.size(); i++) {
            String[] path = inputTable.getValueAt(selectedRows.get(i), 1).toString().replace("-", "").split("/");
            String[] HV = inputTable.getValueAt(selectedRows.get(i), indexColHV).toString().split(" ");

            tableValues.add(new TableValues(path[1], path[2], path[3], HV[0], HV[1]));
        }

        ArrayList<String> instances = new ArrayList<>(new HashSet<String>(tableValues.stream().map(m -> m.Instance).collect(Collectors.toList())));
        ArrayList<String> descriptions = new ArrayList<>(new HashSet<String>(tableValues.stream().map(m -> m.getDescription()).collect(Collectors.toList())));
        Collections.sort(instances, new AlphanumComparator());
        Collections.sort(descriptions, new AlphanumComparator());

        StringBuffer buffer = new StringBuffer();

        buffer.append("\\begin{table}[!htb]").append("\n");
        buffer.append("\\renewcommand{\\arraystretch}{1.5}").append("\n");
        buffer.append("\\centering").append("\n");
        buffer.append("\\caption{Your caption}").append("\n");
        buffer.append("\\label{table:your-label}").append("\n");        

        String header = "";
        String defineCol = "";

        for (String description : descriptions) {
            description = description.replace("_", "\\_");
            header += " & \\textbf{" + description + "}";
            defineCol += defineCol.isEmpty() ? "c" : "|c";
        }

        buffer.append("\\begin{tabular}{").append(defineCol).append("}").append("\n");
        buffer.append("\\toprule").append("\n");
        buffer.append("\\multirow{2}{*}{\\textbf{System}} & \\multicolumn{").append(descriptions.size()).append("}{c}{\\textbf{Algorithms}}\\\\").append("\n");;
        buffer.append(header).append("\\\\").append("\n");
        buffer.append("\\midrule").append("\n");

        for (int i = 0; i < instances.size(); i++) {
            String instance = instances.get(i);
            buffer.append("\\multirow{2}{*}{").append(instance).append("}");

            // Append the HV
            for (String description : descriptions) {
                buffer.append(" & ");
                Optional<TableValues> tableValue = tableValues.stream().filter(p -> p.Instance.equals(instance) && p.getDescription().equals(description)).findFirst();

                if (tableValue.isPresent()) {
                    buffer.append(tableValue.get().Hypervolume);
                } else {
                    buffer.append("0.0");
                }
            }
            
            buffer.append("\\\\").append("\n");

            buffer.append("         ").append("\n");

            // Append the SD
            for (String description : descriptions) {
                buffer.append(" & ");
                Optional<TableValues> tableValue = tableValues.stream().filter(p -> p.Instance.equals(instance) && p.getDescription().equals(description)).findFirst();

                if (tableValue.isPresent()) {
                    buffer.append(tableValue.get().StandardDeviation);
                } else {
                    buffer.append("(0.0)");
                }
            }
            buffer.append("\\\\").append("\n");
            if (i + 1 < instances.size()) {
                buffer.append("\\midrule").append("\n");
            }
        }

        buffer.append("\\bottomrule").append("\n");
        buffer.append("\\end{tabular}").append("\n");
        buffer.append("\\end{table}").append("\n");

        FileUtils.writeStringToFile(outputFolder, buffer.toString());

        logger.info("Done");

        return null;
    }

    @Override
    public String toString() {
        return "HV from Selected Rows to input data for Friedman in Latex Table";
    }

    private class TableValues {

        private String Instance;
        private String Algorithm;
        private String Configuration;
        private String Hypervolume;
        private String StandardDeviation;

        public TableValues(String Instance, String Algorithm, String Configuration, String Hypervolume, String StandardDeviation) {
            this.Instance = Instance;
            this.Algorithm = Algorithm;
            this.Configuration = Configuration;
            this.Hypervolume = Hypervolume;
            this.StandardDeviation = StandardDeviation;
        }

        /**
         * @return the Algorithm
         */
        public String getAlgorithm() {
            return Algorithm;
        }

        /**
         * @return the Configuration
         */
        public String getConfiguration() {
            return Configuration;
        }

        /**
         * @return the Hypervolume
         */
        public String getHypervolume() {
            return Hypervolume;
        }

        /**
         * @return the Instance
         */
        public String getInstance() {
            return Instance;
        }

        /**
         * @return the StandardDeviation
         */
        public String getStandardDeviation() {
            return StandardDeviation;
        }

        /**
         * @return the Algorithm + Configuration
         */
        public String getDescription() {
            return Algorithm + "_" + Configuration;
        }

        @Override
        public String toString() {
            return Instance + "_" + getDescription();
        }
    }

}
