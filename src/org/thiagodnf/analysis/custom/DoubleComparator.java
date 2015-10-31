package org.thiagodnf.analysis.custom;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleComparator implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		
		String[] splitS1 = separateValues(s1);
		String[] splitS2 = separateValues(s2);
		
		double v1 = Double.valueOf(splitS1[0].trim());
		double v2 = Double.valueOf(splitS2[0].trim());

		double sd1 = Double.valueOf(splitS1[1].trim());
		double sd2 = Double.valueOf(splitS2[1].trim());
		
		int result = Double.compare(v1, v2);

		if (result == 0) {
			return Double.compare(sd1, sd2);
		}

		return result;
	}
	
	public String[] separateValues(String str){
		String[] split = {"0","0"};
		
		// Create a Pattern object
		Pattern r = Pattern.compile("(.*)\\((.*)\\)");

		// Now create matcher object.
		Matcher m = r.matcher(str);
		
		if (m.find()) {
			split[0] = m.group(1);
			split[1] = m.group(2);
		} else {
			split[0] = str;
		}
		
		return split;
	}
}
