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
	
	public static void setDecimalPlaces(int value) {
		setValue("decimal-places", String.valueOf(value));
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
}
