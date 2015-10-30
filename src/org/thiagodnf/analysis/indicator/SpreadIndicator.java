package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class SpreadIndicator extends Indicator{

	public SpreadIndicator(QualityIndicator qi) {
		super(qi);		
	}

	@Override
	public double execute(String file, SolutionSet population) {
		return qi.getSpread(population);
	}
}
