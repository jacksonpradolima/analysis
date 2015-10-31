package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class GDIndicator extends Indicator{

	public GDIndicator() {
		super("GD", "gd");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		return qi.getGD(population);
	}
}
