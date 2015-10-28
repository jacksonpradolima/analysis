package org.thiagodnf.analysis.generator;

import java.io.File;
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
public class ApproximateTrueParetoFrontGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(ApproximateTrueParetoFrontGenerator.class.getName());
	
	public ApproximateTrueParetoFrontGenerator(JFrame parent, File[] folders) {
		super(parent, folders);
	}
		
	public String getFilename() {
		return "PFKNOWN";
	}

	public String getOutputFilename() {
		return "/PFTRUEAPROX";
	}

	public String toString() {
		return "Approximate True Pareto-front Generator";
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}