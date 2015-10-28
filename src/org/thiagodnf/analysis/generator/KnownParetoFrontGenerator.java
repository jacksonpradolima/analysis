package org.thiagodnf.analysis.generator;

import java.util.logging.Logger;

import javax.swing.JFrame;

import org.thiagodnf.analysis.util.LoggerUtils;

/**
 * Known Pareto-front Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class KnownParetoFrontGenerator extends AbstractParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(KnownParetoFrontGenerator.class.getName());
	
	public KnownParetoFrontGenerator(JFrame parent) {
		super(parent);
	}
	
	@Override
	public String getFilename() {
		return "FUNALL";
	}

	@Override
	public String getOutputFilename() {
		return "/PFKNOWN";
	}
	
	@Override
	public String toString() {
		return "Known Pareto-Front Generator";
	}
}

