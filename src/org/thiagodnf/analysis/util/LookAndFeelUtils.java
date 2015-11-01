package org.thiagodnf.analysis.util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelUtils {

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
