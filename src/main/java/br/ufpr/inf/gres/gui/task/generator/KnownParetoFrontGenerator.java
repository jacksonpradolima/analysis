package br.ufpr.inf.gres.gui.task.generator;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.commons.io.FilenameUtils;
import br.ufpr.inf.gres.core.util.LoggerUtils;

/**
 * Known Pareto-front Generator Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class KnownParetoFrontGenerator extends ParetoFrontGenerator {
	
	protected final static Logger logger = LoggerUtils.getLogger(KnownParetoFrontGenerator.class.getName());
	
	public KnownParetoFrontGenerator(JFrame parent, File[] folders) {
		super(parent, folders, "FUNALL", File.separator+"PFKNOWN", null);
	}	

	@Override
	public String toString() {
		return "Running Known Pareto-Front Generator";
	}

	@Override
	protected String sortingFilesBy(String filename) {
		String fullPath = FilenameUtils.getFullPath(filename);
		return new File(fullPath).getParentFile().getAbsolutePath();
	}
}

