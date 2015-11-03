package org.thiagodnf.analysis.task.generator;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.LoggerUtils;

/**
 * Approximate True Pareto-front Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class AproxParetoFrontGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(AproxParetoFrontGenerator.class.getName());

	public AproxParetoFrontGenerator(JFrame parent, File[] folders) {
		super(parent, folders, "PFKNOWN", File.separator+"PFAPROX", null);
	}

	public String toString() {
		return "Approximate Pareto-front Generator";
	}

	@Override
	protected String sortingFilesBy(String filename) {
		String fullPath = FilenameUtils.getFullPath(filename);
		return new File(fullPath).getParentFile().getAbsolutePath();
	}
}