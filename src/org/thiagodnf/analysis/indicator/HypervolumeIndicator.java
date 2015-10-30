package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class HypervolumeIndicator extends Indicator{

	public HypervolumeIndicator(QualityIndicator qi) {
		super(qi);		
	}

	@Override
	public double execute(String file, SolutionSet population) {
		return qi.getHypervolume(population);
	}
}
