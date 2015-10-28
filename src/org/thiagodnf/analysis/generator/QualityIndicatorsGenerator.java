package org.thiagodnf.analysis.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.LoggerUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.encodings.solutionType.BinarySolutionType;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.JMException;

public class QualityIndicatorsGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(QualityIndicatorsGenerator.class.getName());
	
	public QualityIndicatorsGenerator(JFrame frame, File[] folders) {
		super(frame, folders);
	}
	
	protected Void doInBackground() throws Exception {
		
//		showMessage("Counting files");
		
		List<String> files = new ArrayList<String>();

//		for (File folder : folders) {
//			files.addAll(FilesUtils.getFiles(folder, "FUN_","ALL"));
//		}
		
//		this.totalOfFiles = files.size();
//		
//		logger.info(totalOfFiles + " has been found");
		
		logger.info("Sorting files");
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();		
		
		for (String filename : files) {

			String fullPath = FilenameUtils.getFullPath(filename);

			if (!map.containsKey(fullPath)) {
				map.put(fullPath, new ArrayList<String>());
			}

			map.get(fullPath).add(filename);
		}
		
		logger.info("Files were sorted");

//		updateMaximum(totalOfFiles);

		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue());
		}
		
		logger.info("Done");
		
		
		
		return null;
	}
	
	protected void generate(String folder, List<String> files) throws Exception {
		logger.info("Generating Quality Indicators for " + folder+". Finding a True Pareto-front file in the path");
		
		File parent = new File(folder).getParentFile().getParentFile();
		
		String approxTrueParetoFrontPath = parent.getAbsolutePath() + "/PFTRUEAPROX";
		
		if(new File(approxTrueParetoFrontPath).exists()){
			throw new Exception("You must to define a true Pareto-front before");
		}
		
		SolutionSet pftrue = SolutionSetUtils.getFromFile(approxTrueParetoFrontPath);
		
		if (pftrue.size() == 0) {
			throw new IllegalArgumentException("The Approximate True Pareto-front is empty");
		}
		
		int numberOfObjectives = pftrue.get(0).getNumberOfObjectives();

		Problem p = new FakeProblem(numberOfObjectives);
		
		QualityIndicator qi = new QualityIndicator(p, approxTrueParetoFrontPath);
		
		for(String file : files){
			generateQualityIndicators(qi, file);
			
//			updateProgress(processedFile++ + " from " + totalOfFiles);
		}
	}
		
	protected void generateQualityIndicators(QualityIndicator qi, String file) throws IOException{
		
		logger.info("Generating QI for file " + file);
		
		String filename = FilenameUtils.getBaseName(file);
		
		String fullPath = FilenameUtils.getFullPath(file);
		
		SolutionSet population = SolutionSetUtils.getFromFile(file);
		
		Map<String, Double> values = new HashMap<String, Double>();
		
		values.put("hypervolume", qi.getHypervolume(population));
		values.put("gd", qi.getGD(population));
		values.put("igd", qi.getIGD(population));
		values.put("spread", qi.getSpread(population));
		values.put("epsilon", qi.getEpsilon(population));
		values.put("generatedSolutions", Double.valueOf(population.size()));
		
		StringBuffer buffer = new StringBuffer();
		
		String time = getTimeFromFile(fullPath + "TIME_" + filename);

		if (!time.isEmpty()) {
			buffer.append("time=" + time + "\n");
		}
		
		population = SolutionSetUtils.removeRepeatedSolutions(population);
		
//		buffer.append("inParetoFront=" + SolutionSetUtils.getIntersection(pfk, population).size());
		
		File qiFile = new File(fullPath + "QI_"+ filename);
		
		FileUtils.writeStringToFile(qiFile, buffer.toString());
		
		logger.info("File " + qiFile + " saved successfully");
	}
	
	protected String getTimeFromFile(String filename) throws NumberFormatException {
		String time = "";
	
		try {
			time = FileUtils.readFileToString(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return time;
	}
		
	protected class FakeProblem extends Problem{
		
		private static final long serialVersionUID = -1352896728518075561L;

		public FakeProblem(int numberOfObjectives){
			super.numberOfVariables_ = 1;
			super.numberOfObjectives_ = numberOfObjectives;
			super.numberOfConstraints_ = 0;
			super.problemName_ = FakeProblem.class.getCanonicalName();
			super.solutionType_ = new BinarySolutionType(this);
			super.length_ = new int[numberOfVariables_];
		}

		@Override
		public void evaluate(Solution arg0) throws JMException {
	
		}		
	}
	
	@Override
	public String toString() {
		return "Running Quality Indicators Generator";
	}	
}

