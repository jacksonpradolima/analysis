package org.thiagodnf.analysis.util;

import java.io.File;

public class ParetoFrontUtils {
	
	public static final String APROX = "PFAPROX";
	
	public static final String KNOWN = "PFKNOWN";

	public static File findParetoFront(File file) {
		File pf = file;

		while ((pf = pf.getParentFile()) != null) {
			File path = new File(pf.getAbsolutePath() + File.separator + APROX);

			if (path.exists()) {
				return path;
			}
		}

		return pf;
	}
}
