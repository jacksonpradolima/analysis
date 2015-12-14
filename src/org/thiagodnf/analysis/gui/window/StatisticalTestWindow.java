package org.thiagodnf.analysis.gui.window;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.thiagodnf.analysis.indicator.Indicator;
import org.thiagodnf.analysis.util.ProcessUtils;
import org.thiagodnf.core.util.FilesUtils;
import org.thiagodnf.core.util.PropertiesUtils;
import org.thiagodnf.core.util.StringUtils;

public class StatisticalTestWindow extends JPanel {

    private static final long serialVersionUID = -2819220375520561750L;

    protected JFrame parent;

    protected JTable table;

    protected Indicator indicator;

    protected List<String> files;

    protected String folderName;

    public StatisticalTestWindow(JFrame parent, Indicator indicator, String folderName, List<String> files) throws IOException {
        this.files = files;
        this.parent = parent;
        this.folderName = folderName;
        this.indicator = indicator;

        int countId = 0;

        Map<String, String> dataMap = new LinkedHashMap<String, String>();
        Map<String, String> idMap = new LinkedHashMap<String, String>();
        Map<String, String> totalMap = new LinkedHashMap<String, String>();

        for (String file : files) {

            String basePath = FilenameUtils.getFullPath(file);

            List<String> qiFiles = FilesUtils.getFiles(new File(basePath), "QI_FUN_");

            String id = "data_" + countId++;

            StringBuffer buffer = new StringBuffer("c(");

            int total = 0;

            for (String qiFile : qiFiles) {
                Properties prop = PropertiesUtils.getFromFile(qiFile);

                buffer.append(prop.get(indicator.getKey()));

                buffer.append(",");

                total++;
            }

            buffer.deleteCharAt(buffer.toString().lastIndexOf(","));
            buffer.append(")");

            idMap.put(basePath, id);
            totalMap.put(id, String.valueOf(total));
            dataMap.put(id, buffer.toString());
        }

        try {
            run(dataMap, totalMap, idMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getId(Map<String, String> idMap) {
        StringBuffer buffer = new StringBuffer();

        for (Entry<String, String> entry : idMap.entrySet()) {
            buffer.append(entry.getValue() + " = " + StringUtils.replaceFirst(entry.getKey(), folderName, "") + "\n");
        }

        return buffer.toString();
    }

    public String getData(Map<String, String> dataMap) {

        StringBuffer buffer = new StringBuffer();

        for (Entry<String, String> entry : dataMap.entrySet()) {
            buffer.append(entry.getKey() + " <- " + entry.getValue() + "\n");
        }

        return buffer.toString();
    }

    public String getValueField(Map<String, String> dataMap) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Value <- c(");

        for (Entry<String, String> entry : dataMap.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append(",");
        }

        buffer.deleteCharAt(buffer.toString().lastIndexOf(","));
        buffer.append(")\n");

        return buffer.toString();
    }

    public String getGroupField(Map<String, String> dataMap, Map<String, String> totalMap) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Group <- factor(c(");

        for (Entry<String, String> entry : dataMap.entrySet()) {
            buffer.append("rep(\"" + entry.getKey() + "\"," + totalMap.get(entry.getKey()) + ")");
            buffer.append(",");
        }

        buffer.deleteCharAt(buffer.toString().lastIndexOf(","));
        buffer.append("))\n");

        return buffer.toString();
    }

    public void run(Map<String, String> dataMap, Map<String, String> totalMap, Map<String, String> idMap) throws IOException, InterruptedException {
        StringBuffer buffer = new StringBuffer();

        buffer.append("library(effsize)\n");
        buffer.append("library(pgirmess)\n");       
        buffer.append("library(stats)\n");       
        buffer.append("library(graphics)\n");       
        buffer.append("library(grDevices)\n");       
        buffer.append("library(utils)\n");       
        buffer.append("library(datasets)\n");       
        buffer.append("library(methods)\n");       
        buffer.append("library(base)\n");  
        buffer.append("library(PMCMR)\n"); 

        buffer.append(getData(dataMap));

        buffer.append(getValueField(dataMap));
        buffer.append(getGroupField(dataMap, totalMap));

        buffer.append("data <- data.frame(Group, Value)\n");

//        if (dataMap.size() == 2) {
//            buffer.append("print(\"============================================\") \n");
//            buffer.append("print(\"   Mann-Whitney (Wilcox)                    \") \n");
//            buffer.append("print(\"============================================\") \n");
//            buffer.append("wilcox.test(Value ~ Group, data=data) \n");
//        } else {
            buffer.append("print(\"============================================\") \n");
            buffer.append("print(\"============ Kruskal Wallis ================\") \n");
            buffer.append("print(\"============================================\") \n");
            buffer.append("kruskal.test(Value ~ Group, data=data) \n");                        
            buffer.append("print(\"============================================\") \n");
            //buffer.append("print(\"Generating boxplot graph how file plot.png \") \n");
            //buffer.append("png(\"plot.png\") \n");            
            //buffer.append("boxplot(Value ~ Group, data=data, main=\"Group Comparison\", ylab=\"Value\") \n");                        
            //buffer.append("dev.off() \n");                        
            buffer.append("print(\"===== Post-Hoc test for Kruskal Wallis =====\") \n");
            buffer.append("print(\"============================================\") \n");             
            buffer.append("with(data, {posthoc.kruskal.nemenyi.test(Value, Group, \"Tukey\")\n}) \n");                   
            buffer.append("print(\"============================================\") \n");
            buffer.append("kruskalmc(Value ~ Group, probs=0.05, data=data,group=FALSE) \n\n");
            buffer.append("print(\"============================================\") \n");            
            buffer.append("pairwise.wilcox.test(Value, Group, p.adj=\"bonferroni\", exact=F) \n");                                   
//        }

        buffer.append("print(\"============================================\") \n");
        buffer.append("print(\"== Effect Size using Vargha and Delaney A ==\") \n");
        buffer.append("print(\"============================================\") \n");

        for (Entry<String, String> entryOne : dataMap.entrySet()) {
            String one = entryOne.getKey();
            for (Entry<String, String> entryTwo : dataMap.entrySet()) {
                String two = entryTwo.getKey();
                if (!one.equalsIgnoreCase(two)) {
                    buffer.append("print(\"" + one + "," + two + "\") \n");
                    buffer.append("VD.A(" + one + "," + two + ")\n");
                }
            }
        }

        String result = "";

        if (SystemUtils.IS_OS_WINDOWS) {
            result = ProcessUtils.run(buffer.toString(), "C:\\Program Files\\R\\R-3.2.2\\bin\\Rscript.exe", "--slave -f");
        } else {
            result = ProcessUtils.run(buffer.toString(), "R", "--slave -f");
        }

        JTextArea resultTextArea = new JTextArea(30, 50);
        JTextArea sourceTextArea = new JTextArea(30, 50);

        resultTextArea.setText(getId(idMap) + "\n" + result);
        sourceTextArea.setText(buffer.toString());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Result", new JScrollPane(resultTextArea));
        tabbedPane.addTab("Source", new JScrollPane(sourceTextArea));

        add(tabbedPane);
    }

    public int showOptionDialog() {
        Object[] options = {"Close"};

        // Show the indicator name at title
        String title = "Statistical Test";

        int optionType = JOptionPane.OK_OPTION;
        int messageType = JOptionPane.PLAIN_MESSAGE;

        return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
    }
}
