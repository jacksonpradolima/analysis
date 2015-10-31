package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class EpsilonIndicator extends Indicator{

	public EpsilonIndicator() {
		super("Epsilon", "epsilon");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		return qi.getEpsilon(population);
	}
}
