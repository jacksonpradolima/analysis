package br.ufpr.inf.cbiogres.indicator;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public abstract class Indicator {
	
	/**
	 * Indicator name used to show some readable information to user
	 */
	protected String name;
	
	/**
	 * Value used in QI_ files to identify the indicator that will be read
	 */
	protected String key;
	
	/**
	 * Constructor
	 * 
	 * @param qi Quality Indicator Object
	 * @param name Indicator name
	 * @param key Indicator key
	 */
	public Indicator(String name, String key) {
		this.key = key;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

	public String getKey() {
		return this.key;
	}
	
	public abstract double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population);
}
