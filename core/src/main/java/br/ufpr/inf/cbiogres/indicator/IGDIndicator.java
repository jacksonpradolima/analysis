package br.ufpr.inf.cbiogres.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class IGDIndicator extends Indicator{

	public IGDIndicator() {
		super("IGD", "igd");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront,String file, SolutionSet population) {
		return qi.getIGD(population);
	}
}
