package br.ufpr.inf.cbiogres.util;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.inf.cbiogres.indicator.EpsilonIndicator;
import br.ufpr.inf.cbiogres.indicator.GDIndicator;
import br.ufpr.inf.cbiogres.indicator.HypervolumeIndicator;
import br.ufpr.inf.cbiogres.indicator.IGDIndicator;
import br.ufpr.inf.cbiogres.indicator.InParetoFrontIndicator;
import br.ufpr.inf.cbiogres.indicator.Indicator;
import br.ufpr.inf.cbiogres.indicator.NumberOfNonRepeatedSolutionsIndicator;
import br.ufpr.inf.cbiogres.indicator.NumberOfSolutionsIndicator;
import br.ufpr.inf.cbiogres.indicator.SpreadIndicator;
import br.ufpr.inf.cbiogres.indicator.TimeIndicator;

public class IndicatorUtils {
	
	/**
	 * This method returns the indicator list used in the tool.
	 * 
	 * @return Indicator list
	 */
	public static List<Indicator> getIndicatorList() {
		List<Indicator> indicators = new ArrayList<Indicator>();

		indicators.add(new HypervolumeIndicator());
		indicators.add(new GDIndicator());
		indicators.add(new IGDIndicator());
		indicators.add(new SpreadIndicator());
		indicators.add(new EpsilonIndicator());
		indicators.add(new NumberOfSolutionsIndicator());
		indicators.add(new NumberOfNonRepeatedSolutionsIndicator());
		indicators.add(new InParetoFrontIndicator());
		indicators.add(new TimeIndicator());

		return indicators;
	}
	
	/**
	 * This methods return a indicator when a indicator name
	 * is passed as parameter
	 * 
	 * @param name Indicator name
	 * @return The indicator instance. When not found, return null value
	 */
	public static Indicator getIndicator(String name) {
		List<Indicator> indicators = getIndicatorList();

		for (Indicator indicator : indicators) {
			if (indicator.getName().equalsIgnoreCase(name)) {
				return indicator;
			}
		}

		return null;
	}
}
