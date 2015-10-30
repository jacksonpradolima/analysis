package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public abstract class Indicator {
	
	protected QualityIndicator qi;
	
	public Indicator(QualityIndicator qi) {
		this.qi = qi;
	}

	public abstract double execute(String file, SolutionSet population);
}
