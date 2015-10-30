package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class EpsilonIndicator extends Indicator{

	public EpsilonIndicator(QualityIndicator qi) {
		super(qi);		
	}

	@Override
	public double execute(String file, SolutionSet population) {
		return qi.getEpsilon(population);
	}
}
