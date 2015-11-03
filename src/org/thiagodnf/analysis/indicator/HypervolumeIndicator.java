package org.thiagodnf.analysis.indicator;

import java.io.File;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.Hypervolume;
import jmetal.qualityIndicator.QualityIndicator;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.ParetoFrontUtils;
import org.thiagodnf.analysis.util.SettingsUtils;
import org.thiagodnf.analysis.util.SolutionSetUtils;

public class HypervolumeIndicator extends Indicator{

	public jmetal.qualityIndicator.util.MetricsUtil utils_;
	
	public static final String DEFAULT_JMETAL = "Using Default JMetal";
	
	public static final String MAX_MIN_GENERATED_VALUES = "Using Max and Min Generated Values";
	
	public static final String ZERO_AND_ONE_VALUES = "Using 0.0 and 1.01 values";
	
	
	public HypervolumeIndicator() {
		super("Hypervolume", "hypervolume");
		utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		
		int nObjectives = SolutionSetUtils.getNumberOfObjectives(paretoFront);

		double[][] truePareto = paretoFront.writeObjectivesToMatrix();
		double[][] pop = population.writeObjectivesToMatrix();
		
		double[][] values = getMaxAndMinValues(file, truePareto, nObjectives);
		
		// STEP 1. Obtain the maximum and minimum values of the Pareto front
		double[] minimumValues = values[0];
		double[] maximumValues = values[1];
		
		return hypervolume(pop, truePareto, nObjectives,maximumValues, minimumValues);
	}
	
	public double hypervolume(double[][] paretoFront, double[][] paretoTrueFront, int numberOfObjectives, double[] maximumValues, double[] minimumValues) {

		// STEP 2. Get the normalized front
		double[][] normalizedFront = utils_.getNormalizedFront(paretoFront, maximumValues, minimumValues);

		// STEP 3. Inverse the pareto front. This is needed because of the
		// original
		// metric by Zitzler is for maximization problems
		double[][] invertedFront = utils_.invertedFront(normalizedFront);

		// STEP4. The hypervolumen (control is passed to java version of Zitzler
		// code)
		return new Hypervolume().calculateHypervolume(invertedFront, invertedFront.length, numberOfObjectives);
	} // hypervolume

	public double[][] getMaxAndMinValues(String file, double[][] truePareto, int nObjectives){
		File paretoFront = ParetoFrontUtils.findParetoFront(new File(file));
		
		String fullPath = FilenameUtils.getFullPath(paretoFront.getAbsolutePath());
		
		String maxMinPath = fullPath + File.separator + "MAXMIN";
		
		SolutionSet maxMinValues = SolutionSetUtils.getFromFile(maxMinPath);
		
		SolutionSet normalizedValues = new SolutionSet(2);
		normalizedValues.add(generateValues(nObjectives, 0.00));
		normalizedValues.add(generateValues(nObjectives, 1.01));
		
		double[][] tmp = null;
		
		if (SettingsUtils.getNormalizeHypervolume().equalsIgnoreCase(DEFAULT_JMETAL)) {
			tmp = truePareto;
		} else if (SettingsUtils.getNormalizeHypervolume().equalsIgnoreCase(MAX_MIN_GENERATED_VALUES)) {
			tmp = maxMinValues.writeObjectivesToMatrix();
		} else if (SettingsUtils.getNormalizeHypervolume().equalsIgnoreCase(ZERO_AND_ONE_VALUES)) {
			tmp = normalizedValues.writeObjectivesToMatrix();
		}	
		
		double[][] result = new double[2][];

		result[0] = utils_.getMinimumValues(tmp, nObjectives);
		result[1] = utils_.getMaximumValues(tmp, nObjectives);

		return result;
	}
	
	protected Solution generateValues(int nObjectives, double value){
		Solution s = new Solution(nObjectives);
		
		for (int i = 0; i < nObjectives; i++) {
			s.setObjective(i, value);
		}
		
		return s;
	}	
}
