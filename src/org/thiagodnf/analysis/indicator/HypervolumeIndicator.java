package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class HypervolumeIndicator extends Indicator{

	public HypervolumeIndicator() {
		super("Hypervolume", "hypervolume");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		return qi.getHypervolume(population);
	}
}
