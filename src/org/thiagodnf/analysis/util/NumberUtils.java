package org.thiagodnf.analysis.util;

import java.math.BigDecimal;
import java.util.Properties;

public class NumberUtils {

	public static String round(Object number) {
		return round(number, Integer.valueOf(SettingsUtils.getDecimalPlaces()));
	}
	
	public static String round(Object number, int decimalPlates) {
		String n = String.valueOf(number);
		
		BigDecimal decimal = new BigDecimal(n);
		
		int round = BigDecimal.ROUND_HALF_UP;
		
		double roundNumber = decimal.setScale(decimalPlates, round).doubleValue();
		
		return String.valueOf(roundNumber);
	}	
	
	public static String formatNumbers(Properties prop, String key) {
		return formatNumbers(prop, key, Integer.valueOf(SettingsUtils.getDecimalPlaces()));
	}
	
	public static String formatNumbers(Properties prop, String key, int round) {
		String value = NumberUtils.round(prop.get(key + ".mean"), round);
		String sd = NumberUtils.round(prop.get(key + ".sd"), round);

		if (SettingsUtils.isStandardDeviationVisible()) {
			return String.format("%s (%s)", value, sd);
		}

		return value;
	}
}
