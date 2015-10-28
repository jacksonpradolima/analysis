package org.thiagodnf.analysis.generator;

import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.util.LoggerUtils;

/**
 * Approximate True Pareto-front Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class ApproximateTrueParetoFrontGenerator extends AbstractParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(ApproximateTrueParetoFrontGenerator.class.getName());
	
	public ApproximateTrueParetoFrontGenerator(JFrame parent) {
		super(parent);
	}
		
	@Override
	public String getFilename() {
		return "PFKNOWN";
	}

	@Override
	public String getOutputFilename() {
		return "/PFTRUEAPROX";
	}

	@Override
	public String toString() {
		return "Approximate True Pareto-front Generator";
	}
}