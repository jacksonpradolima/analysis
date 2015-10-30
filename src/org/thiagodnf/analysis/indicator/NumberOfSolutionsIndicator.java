package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class NumberOfSolutionsIndicator extends Indicator{

	public NumberOfSolutionsIndicator(QualityIndicator qi) {
		super(qi);
	}

	@Override
	public double execute(String file, SolutionSet population) {
		return population.size();
	}
}
