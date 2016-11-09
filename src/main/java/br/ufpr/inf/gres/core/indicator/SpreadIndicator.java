package br.ufpr.inf.gres.core.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class SpreadIndicator extends Indicator{

	public SpreadIndicator() {
		super("Spread", "spread");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		return qi.getSpread(population);
	}
}
