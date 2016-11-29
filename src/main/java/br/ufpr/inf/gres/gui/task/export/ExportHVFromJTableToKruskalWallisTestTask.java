package br.ufpr.inf.gres.gui.task.export;

import br.ufpr.inf.gres.core.util.FilesUtils;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTable;

import org.apache.commons.io.FileUtils;
import br.ufpr.inf.gres.gui.component.ResultTable;
import br.ufpr.inf.gres.gui.task.AsyncTask;
import br.ufpr.inf.gres.core.util.LoggerUtils;
import br.ufpr.inf.gres.core.util.PropertiesUtils;
import br.ufpr.inf.gres.core.util.StringUtils;
import br.ufpr.inf.gres.util.comparator.AlphanumComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;

public class ExportHVFromJTableToKruskalWallisTestTask extends AsyncTask {
    
    protected static final Logger logger = LoggerUtils.getLogger(ExportHVFromJTableToKruskalWallisTestTask.class.getName());
    
    protected JTable inputTable;
    
    protected File outputFolder;
    
    protected String folderName;
    
    protected List<String> files;
    
    public ExportHVFromJTableToKruskalWallisTestTask(JFrame parent, File outputFolder, JTable inputTable, String folderName, List<String> files) {
        super(parent);
        
        this.inputTable = inputTable;
        this.outputFolder = outputFolder;
        this.folderName = folderName;
        this.files = files;
    }
    
    @Override
    protected Object doInBackground() throws Exception {
        
        logger.info("Exporting selected rows from jtable to csv table");
        
        StringBuilder buffer = new StringBuilder();               
        buffer.append("Group;Value").append("\n");
        
        
        for (String file : files) {

            String basePath = FilenameUtils.getFullPath(file);

            List<String> qiFiles = FilesUtils.getFiles(new File(basePath), "QI_FUN_");
            
            String group = StringUtils.replaceFirst(basePath, folderName, "").replace("/", "_");
                                    
            for (String qiFile : qiFiles) {
                Properties prop = PropertiesUtils.getFromFile(qiFile);

                buffer.append(group).append(";").append(Double.parseDouble(prop.get("hypervolume").toString())).append("\n");;
            }
        }
        
        FileUtils.writeStringToFile(outputFolder, buffer.toString());
        
        logger.info("Done");
        
        return null;
    }
    
    @Override
    public String toString() {
        return "HV from Selected Rows to input data for Kruskal-Wallis";
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
