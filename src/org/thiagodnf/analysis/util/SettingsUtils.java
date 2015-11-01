package org.thiagodnf.analysis.util;

import java.util.prefs.Preferences;

import org.thiagodnf.analysis.task.generator.MaxMinGenerator;

/**
 * Settings Utils Class is responsible for save
 * all program settings used by user.
 * 
 * @author Thiago Nascimento
 * @version 1.0.0
 * @since 2015-10-30 
 */
public class SettingsUtils {

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
	
	public static int getDecimalPlaces() {
		return Integer.valueOf(getValue("decimal-places", "5"));
	}
	
	public static void setDecimalPlaces(String value) {
		setValue("decimal-places", value);
	}
	
	public static String[] getAvailableDecimalPlaces(){
		return new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	}

	public static void setStandardDeviation(boolean value) {
		setValue("is-standard-deviation-visible", String.valueOf(value));
	}
	
	public static boolean isStandardDeviationVisible() {
		if (getValue("is-standard-deviation-visible", "True").equalsIgnoreCase( "True")) {
			return true;
		}

		return false;
	}	
	
	public static void setLookAndFeel(String value) {
		setValue("look-and-feel", value);
	}
	
	public static String getLookAndFeel() {
		return getValue("look-and-feel", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
	}

	public static void setMaxMinValues(int value) {
		setValue("normalize-hypervolume", String.valueOf(value));
	}
	
	public static int getMaxMinValues() {
		String value = getValue("normalize-hypervolume", "1");
		
		if (value.equalsIgnoreCase("1")) {
			return MaxMinGenerator.DEFAULT;
		} else if (value.equalsIgnoreCase("2")) {
			return MaxMinGenerator.MAXMIN;
		} else if (value.equalsIgnoreCase("3")) {
			return MaxMinGenerator.NORMALIZED;
		}
		
		return 0;
	}
}
