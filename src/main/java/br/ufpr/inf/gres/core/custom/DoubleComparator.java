package br.ufpr.inf.gres.core.custom;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to compare values at result table. You can to send a
 * simple double value in string format or send two double value where the
 * second one is a standard deviation. For example, a single value is formated
 * as "2.4" and the second option is you use "2.4 (3)" using parenthesis.
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
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
