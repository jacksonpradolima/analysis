package org.thiagodnf.analysis.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;
import org.thiagodnf.analysis.util.SolutionSetUtils;

public class InParetoFrontIndicator extends Indicator {

	protected SolutionSet paretoFront;
	
	public InParetoFrontIndicator(QualityIndicator qi, SolutionSet paretoFront) {
		super(qi);
		this.paretoFront = paretoFront;
	}

	@Override
	public double execute(String file, SolutionSet population) {
		SolutionSet nonRepeatedpopulation = SolutionSetUtils.removeRepeatedSolutions(population);
		
		SolutionSet intersec = SolutionSetUtils.getIntersection(paretoFront, nonRepeatedpopulation);
		
		return intersec.size();
	}
}
