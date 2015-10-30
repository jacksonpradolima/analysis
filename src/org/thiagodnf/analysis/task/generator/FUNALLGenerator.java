package org.thiagodnf.analysis.task.generator;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.analysis.util.LoggerUtils;

/**
 * FUNALL Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class FUNALLGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(FUNALLGenerator.class.getName());
	
	public FUNALLGenerator(JFrame parent, File[] folders) {
		super(parent, folders, "FUN_", "/FUNALL", "ALL");
	}
	
	@Override
	public String toString() {
		return "Running FUNALL Generator";
	}

	@Override
	protected String sortingFilesBy(String filename) {
		return FilenameUtils.getFullPath(filename);
	}
}

