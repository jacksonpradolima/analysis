package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class GDIndicator extends Indicator{

	public GDIndicator(QualityIndicator qi) {
		super(qi);		
	}

	@Override
	public double execute(String file, SolutionSet population) {
		return qi.getGD(population);
	}
}
