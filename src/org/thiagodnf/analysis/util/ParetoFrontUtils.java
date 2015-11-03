package org.thiagodnf.analysis.util;

import java.io.File;

public class ParetoFrontUtils {
	
	public static final String APPROX = "PFAPPROX";
	
	public static final String KNOWN = "PFKNOWN";

	public static File findParetoFront(String filename) {
		return findParetoFront(new File(filename));
	}
	
	public static File findParetoFront(File file) {
		File pf = file;

		while ((pf = pf.getParentFile()) != null) {
			File path = new File(pf.getAbsolutePath() + File.separator + APPROX);

			if (path.exists()) {
				return path;
			}
		}

		return pf;
	}
}
