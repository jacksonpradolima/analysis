package org.thiagodnf.analysis.util;

import java.util.prefs.Preferences;

/**
 * Settings Utils Class is responsible for save
 * all program settings used by user.
 * 
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-10-30 
 */
public class SettingsUtils {
	
	public static final String NORMALIZE_HYPERVOLUME = "normalize-hypervolume";
	
	public static final String LOOK_AND_FEEL = "look-and-feel";
	
	public static final String DECIMAL_PLACES = "decimal-places";
	
	public static final String STANDARD_DEVIATION = "standard-deviation";

	public static Preferences getPreferences() {
		return Preferences.userNodeForPackage(SettingsUtils.class);
	}
	
	public static String getValue(String key) {
		return getPreferences().get(key, null);
	}
	
	public static void setValue(String key, String value){
		getPreferences().put(key, value);
	}

	public static String getValue(String key, String defaultValue) {
		return getPreferences().get(key, defaultValue);
	}
	
	public static String getDecimalPlaces() {
		return getValue(DECIMAL_PLACES, "5");
	}
	
	public static void setDecimalPlaces(String value) {
		setValue(DECIMAL_PLACES, value);
	}	

	public static void setStandardDeviation(String value) {
		setValue(STANDARD_DEVIATION, value);
	}
	
	public static String getStandardDeviation() {
		return getValue(STANDARD_DEVIATION, OptionsUtils.getAvailableStandardDeviation()[0]);
	}
	
	public static boolean isStandardDeviationVisible() {
		if (getValue(STANDARD_DEVIATION, "True").equalsIgnoreCase( "Yes")) {
			return true;
		}

		return false;
	}	
	
	public static void setLookAndFeel(String value) {
		setValue(LOOK_AND_FEEL, value);
	}
	
	public static String getLookAndFeel() {
		return getValue(LOOK_AND_FEEL, "javax.swing.plaf.nimbus.NimbusLookAndFeel");
	}
	
	public static void setNormalizeHypervolume(int value) {
		setValue(NORMALIZE_HYPERVOLUME, String.valueOf(value));
	}
	
	public static void setNormalizeHypervolume(String value) {
		setValue(NORMALIZE_HYPERVOLUME, value);
	}
	
	public static String getNormalizeHypervolume() {
		return getValue(NORMALIZE_HYPERVOLUME, OptionsUtils.getAvailableNormalizeHypervolume()[0]);
	}
//	
//	public static int getNormalizeHypervolume() {
//		String value = getValue(NORMALIZE_HYPERVOLUME, "1");
//		
//		if (value.equalsIgnoreCase("1")) {
//			return MaxMinGenerator.DEFAULT;
//		} else if (value.equalsIgnoreCase("2")) {
//			return MaxMinGenerator.MAXMIN;
//		} else if (value.equalsIgnoreCase("3")) {
//			return MaxMinGenerator.NORMALIZED;
//		}
//		
//		return 0;
//	}

	
}
