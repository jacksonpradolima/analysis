package org.thiagodnf.analysis.indicator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;

public class TimeIndicator extends Indicator{

	public TimeIndicator(QualityIndicator qi) {
		super(qi);
	}

	@Override
	public double execute(String file, SolutionSet population) {
//		StringBuffer buffer = new StringBuffer();
//		
//		String time = getTimeFromFile(fullPath + "TIME_" + filename);
//
//		if (!time.isEmpty()) {
//			buffer.append("time=" + time + "\n");
//		}
		return 0;
	}
	
	protected String getTimeFromFile(String filename) throws NumberFormatException {
		String time = "";
	
		try {
			time = FileUtils.readFileToString(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return time;
	}

}
