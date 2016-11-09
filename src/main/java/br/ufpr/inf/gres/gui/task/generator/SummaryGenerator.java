package br.ufpr.inf.gres.gui.task.generator;

import br.ufpr.inf.gres.core.util.PropertiesUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import com.google.common.base.Preconditions;

public class SummaryGenerator extends Generator {

    public SummaryGenerator(JFrame parent, File[] folders) {
        super(parent, folders);
    }

    @Override
    public String toString() {
        return "Running Summary Generator";
    }

    @Override
    protected Object doInBackground() throws Exception {

        List<String> qiFiles = getFilesStartingWith(folders, "QI_", "ALL");

        List<String> files = new ArrayList<String>();

        for (String file : qiFiles) {
            if (!file.contains("PFKNOWN")) {
                files.add(file);
            }
        }

        logger.info(files.size() + " has been found");

        updateMaximum(files.size());

        Map<String, List<String>> map = new HashMap<String, List<String>>();

        for (String filename : files) {
            updateNote("Sorting files " + getCurrentProgress() + " from " + files.size());

            String key = new File(filename).getParent();

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }

            map.get(key).add(filename);

            updateProgress();
        }

        updateMaximum(files.size());

        for (Entry<String, List<String>> entry : map.entrySet()) {
            generate(entry.getKey(), entry.getValue(), files.size());
        }

        afterFinishing();

        logger.info("Done");

        return null;
    }

    protected void generate(String folder, List<String> files, int totalOfFiles) throws Exception {
        Preconditions.checkNotNull(folder, "Folder cannot be null");
        Preconditions.checkArgument(!folder.isEmpty(), "Folder cannot be empty");

        logger.info("Generating for folder: " + folder);

        List<String> indicators = new ArrayList<String>();

        // Get all indicator	
        for (String file : files) {
            Properties prop = PropertiesUtils.getFromFile(file);
            for (Entry<Object, Object> entry : prop.entrySet()) {
                if (!indicators.contains(entry.getKey())) {
                    indicators.add((String) entry.getKey());
                }
            }
        }

        Properties output = new Properties();

        for (String indicador : indicators) {

            DescriptiveStatistics stats = new DescriptiveStatistics();

            logger.info("Processing " + indicador + " metric");

            for (String file : files) {

                logger.info("Reading " + file + " file");

                Properties prop = PropertiesUtils.getFromFile(file);

                String value = prop.getProperty(indicador);

                if (value != null && !value.isEmpty()) {
                    stats.addValue(Double.valueOf(value));
                }
            }

            addMetric(output, indicador, stats);
        }

        PropertiesUtils.save(new File(folder + File.separator + "SUMMARY"), output);
    }

    protected void addMetric(Properties prop, String prefix, DescriptiveStatistics stats) {
        prop.setProperty(prefix + ".mean", String.valueOf(stats.getMean()));
        prop.setProperty(prefix + ".max", String.valueOf(stats.getMax()));
        prop.setProperty(prefix + ".min", String.valueOf(stats.getMin()));
        prop.setProperty(prefix + ".sd", String.valueOf(stats.getStandardDeviation()));
        prop.setProperty(prefix + ".sum", String.valueOf(stats.getSum()));
        prop.setProperty(prefix + ".n", String.valueOf(stats.getN()));
        prop.setProperty(prefix + ".variance", String.valueOf(stats.getVariance()));
    }
}
