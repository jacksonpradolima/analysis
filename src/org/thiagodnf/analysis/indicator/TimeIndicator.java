package org.thiagodnf.analysis.indicator;

import java.io.File;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

import org.apache.commons.io.FilenameUtils;
import org.thiagodnf.core.util.FilesUtils;

public class TimeIndicator extends Indicator{

	public TimeIndicator() {
		super("Time", "time");
	}

	@Override
	public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
		String filename = FilenameUtils.getName(file);
		
		// Ignore FUNALL and PFKNOWN files
		if (filename.equalsIgnoreCase("FUNALL")) {
			return 0.0;
		}
		if (filename.equalsIgnoreCase("PFKNOWN")) {
			return 0.0;
		}
		
		String[] split = filename.split("_");
		
		String fullpath = FilenameUtils.getFullPath(file);
		
		int id = Integer.valueOf(split[1]);
		
		String timePath = fullpath + File.separator + "TIME_" + id;

		if (!new File(timePath).exists()) {
			return 0;
		}
		
		return FilesUtils.getValue(new File(timePath));
	}
}
