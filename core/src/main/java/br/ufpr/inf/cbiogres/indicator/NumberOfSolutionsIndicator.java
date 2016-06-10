package br.ufpr.inf.cbiogres.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class NumberOfSolutionsIndicator extends Indicator{

	public NumberOfSolutionsIndicator() {
		super("Number of Solutions", "number-of-solutions");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront,String file, SolutionSet population) {
		return population.size();
	}
}
