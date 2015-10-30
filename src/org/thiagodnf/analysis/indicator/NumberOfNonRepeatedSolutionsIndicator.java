package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

import org.thiagodnf.analysis.util.SolutionSetUtils;

public class NumberOfNonRepeatedSolutionsIndicator extends Indicator {

	public NumberOfNonRepeatedSolutionsIndicator(QualityIndicator qi) {
		super(qi);
	}

	@Override
	public double execute(String file, SolutionSet population) {
		SolutionSet nonRepeatedpopulation = SolutionSetUtils.removeRepeatedSolutions(population);
		return nonRepeatedpopulation.size();
	}
}
