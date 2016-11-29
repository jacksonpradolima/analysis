package br.ufpr.inf.gres.gui.window;

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
import br.ufpr.inf.gres.core.indicator.Indicator;
import br.ufpr.inf.gres.core.util.ProcessUtils;
import br.ufpr.inf.gres.core.util.FilesUtils;
import br.ufpr.inf.gres.core.util.PropertiesUtils;
import br.ufpr.inf.gres.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticalTestWindow extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(StatisticalTestWindow.class);

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

        Map<String, String> dataMap = new LinkedHashMap<>();
        Map<String, String> idMap = new LinkedHashMap<>();
        Map<String, String> totalMap = new LinkedHashMap<>();

        for (String file : files) {

            String basePath = FilenameUtils.getFullPath(file);

            List<String> qiFiles = FilesUtils.getFiles(new File(basePath), "QI_FUN_");

            String id = "data_" + countId++;

            StringBuilder buffer = new StringBuilder("c(");

            int total = 0;

            for (String qiFile : qiFiles) {
                Properties prop = PropertiesUtils.getFromFile(qiFile);

                buffer.append(Double.parseDouble(prop.get(indicator.getKey()).toString()));

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
        } catch (IOException | InterruptedException ex) {
            log.error("Error to run the statistical test.", ex);
        }
    }

    public String getId(Map<String, String> idMap) {
        StringBuilder buffer = new StringBuilder();

        for (Entry<String, String> entry : idMap.entrySet()) {
            buffer.append(entry.getValue()).append(" = ").append(StringUtils.replaceFirst(entry.getKey(), folderName, "")).append("\n");
        }

        return buffer.toString();
    }

    public String getData(Map<String, String> dataMap) {

        StringBuilder buffer = new StringBuilder();

        for (Entry<String, String> entry : dataMap.entrySet()) {
            buffer.append(entry.getKey()).append(" <- ").append(entry.getValue()).append("\n");
        }

        return buffer.toString();
    }

    public String getValueField(Map<String, String> dataMap) {
        StringBuilder buffer = new StringBuilder();

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
        StringBuilder buffer = new StringBuilder();

        buffer.append("Group <- factor(c(");

        for (Entry<String, String> entry : dataMap.entrySet()) {
            buffer.append("rep(\"").append(entry.getKey()).append("\",").append(totalMap.get(entry.getKey())).append(")");
            buffer.append(",");
        }

        buffer.deleteCharAt(buffer.toString().lastIndexOf(","));
        buffer.append("))\n");

        return buffer.toString();
    }

    public String getRequiredPackages(String packageName) {
        return String.format("if(!require(%1$s))\n"
                           + "{\n"
                                + "print(\"You are missing the package '%1$s', we will now try to install it...\")\n"
                                + "install.packages(\"%1$s\")\n"
                                + "library(%1$s)\n"
                            + "}\n", packageName);
    }

    public void run(Map<String, String> dataMap, Map<String, String> totalMap, Map<String, String> idMap) throws IOException, InterruptedException {
        StringBuilder buffer = new StringBuilder();

        buffer.append(getRequiredPackages("effsize"));
        buffer.append(getRequiredPackages("pgirmess"));
        buffer.append(getRequiredPackages("stats"));
        //buffer.append(getRequiredPackages("graphics"));
        //buffer.append(getRequiredPackages("grDevices"));
        buffer.append(getRequiredPackages("utils"));
        buffer.append(getRequiredPackages("datasets"));
        buffer.append(getRequiredPackages("methods"));
        buffer.append(getRequiredPackages("base"));
        buffer.append(getRequiredPackages("PMCMR"));        
        //buffer.append(getRequiredPackages("scmamp"));
        //buffer.append(getRequiredPackages("nortest"));
        
        buffer.append(kruskalTestWithPostHocFunction());
        
        buffer.append(getData(dataMap));

        String value = getValueField(dataMap);        
        buffer.append(value);        
        buffer.append(getGroupField(dataMap, totalMap));

        buffer.append("data <- data.frame(Group, Value)\n");

        //Check if the data is a normal distribution. The most widely used test for normality is the Shapiro-Wilks test. 
//        buffer.append("print(\"============ Shapiro-Wilks test ================\") \n");        
//        buffer.append("shapiro.test(Value) \n");  
       
        if (dataMap.size() == 2) {
            buffer.append("print(\"============================================\") \n");
            buffer.append("print(\"   Mann-Whitney (Wilcox)                    \") \n");
            buffer.append("print(\"============================================\") \n");
            buffer.append("wilcox.test(Value ~ Group, data=data) \n");
        } else {
            buffer.append("print(\"============ Kruskal Wallis ================\") \n");
            buffer.append("kruskal.test.with.post.hoc(Value, Group, data)\n");   
            
            buffer.append("print(\"============ Wilcox rank sum ================\") \n");
            buffer.append("pairwise.wilcox.test(Value, Group, p.adj=\"bonferroni\", exact=F) \n");
        }
                    
        buffer.append("print(\"====== Effect Size using Vargha and Delaney A ======\") \n");

        for (Entry<String, String> entryOne : dataMap.entrySet()) {
            String one = entryOne.getKey();
            for (Entry<String, String> entryTwo : dataMap.entrySet()) {
                String two = entryTwo.getKey();
                if (!one.equalsIgnoreCase(two)) {                    
                    buffer.append("paste(\"" + one + "," + two + "=\", VD.A(" + one + "," + two + ")$estimate, sep=\"\") \n");                    
                }
            }
        }
        
        String result = "";

        if (SystemUtils.IS_OS_WINDOWS) {
            // TODO: Must be created a configuration file and configuration window
            result = ProcessUtils.run(buffer.toString(), "C:\\Program Files\\R\\R-3.3.0\\bin\\Rscript.exe", "--slave -f");
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
    
    public String kruskalTestWithPostHocFunction(){
        return                
        "kruskal.test.with.post.hoc <- function(value, group, data, alpha = 0.05, notch = FALSE, omm = FALSE)\n" +
        "{\n" +                
        "  options(scipen=999)\n" +        
        "  pre.results <- kruskal.test(value ~ group, data=data) \n" +        
        "  \n" +
        "  chi_squared <- round(pre.results$statistic, 3)\n" +
        "  p.value <- round(pre.results$p.value, 6)\n" +
        "  degree.freed <- pre.results$parameter\n" +        
        "  \n" +        
        "  print(pre.results)\n" +
        "  \n" +
        "  # If the Kruskal–Wallis test is significant, a post-hoc analysis can be performed \n" +
        "  # to determine which levels of the independent variable differ from each other level.  \n" +
        "  if(p.value < alpha){\n" +                
        "    print(\"=== POST-HOC\")\n" +
        "    print(\"Nemenyi test for multiple comparisons\")\n" +
        "    posthoc <- posthoc.kruskal.nemenyi.test(value, group, \"Tukey\")\n" +        
        "    print(posthoc)\n" +
        "    \n" +
//        "    ## LaTeX formated: Significances highlighted in bold\n" +
//        "    ## Pay attention! Read from left to right the comparations. See the TRUE values!!!\n" +
//        "    no.diff <- post.results$p.value < alpha\n" +
//        "    no.diff[is.na(no.diff)] <- FALSE\n" +
//        "    writeTabular(table=post.results$p.value, format='f', bold=no.diff,hrule=0,vrule=0)\n" +
//        "    \n" +
        "    print(\"Kruskal MC - Multiple comparison test between treatments or treatments versus control after Kruskal-Wallis test\")\n" +
        "    ### Multiple comparison test between treatments or treatments versus control after Kruskal-Wallis test ###\n" +
        "    # This test helps determining which groups are different with pairwise comparisons adjusted \n" +
        "    # appropriately for multiple comparisons. Those pairs of groups which have observed differences \n" +
        "    # higher than a critical value are considered statistically different at a given significance level.\n" +
        "    kruskalmc(value ~ group, probs=alpha, data=data, cont=NULL) \n" +                        
        "  }\n" +
        "  else{\n" +
        "    print(\"The results where not significant. There is no need for a post-hoc test.\")\n" +        
        "  }\n" +        
        "}\n";        
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
