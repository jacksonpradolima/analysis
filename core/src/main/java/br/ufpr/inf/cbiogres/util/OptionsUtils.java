package br.ufpr.inf.cbiogres.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import br.ufpr.inf.cbiogres.indicator.HypervolumeIndicator;

public class OptionsUtils {

	public static String[] getYesOrNoOptions() {
		return new String[]{
			"Yes",
			"No"
		};
	}
	
	public static String[] getRangeNumberOptions(int min, int max) {
		return getRangeNumberOptions(min, max, 1);
	}
	
	public static String[] getRangeNumberOptions(int min, int max, int step) {
		String[] result = new String[max - min + 1];

		int index = 0;

		for (int i = min; i <= max; i += step) {
			result[index++] = String.valueOf(i);
		}

		return result;
	}
	
	public static int getSelectedIndex(String item){
		return -1;
	}
	
	public static String[] getAvailableStandardDeviation() {
		return getYesOrNoOptions();
	}
	
	public static String[] getAvailableDecimalPlaces() {
		return getRangeNumberOptions(1, 10, 1);
	}
	
	public static String[] getAvailableNormalizeHypervolume() {
		return new String[]{
			HypervolumeIndicator.DEFAULT_JMETAL, 
			HypervolumeIndicator.MAX_MIN_GENERATED_VALUES, 
			HypervolumeIndicator.ZERO_AND_ONE_VALUES	
		};
	}
	
	public static String[] getAvailableLookAndFeel() {
		List<String> lookAndFeels = new ArrayList<String>();
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				lookAndFeels.add(info.getClassName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return lookAndFeels.toArray(new String[lookAndFeels.size()]);
	}
}
