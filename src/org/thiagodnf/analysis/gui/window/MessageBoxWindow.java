package org.thiagodnf.analysis.gui.window;

import java.awt.Component;
import javax.swing.JOptionPane;

public class MessageBoxWindow {

	public static void error(Component parent, String message) {
		message(parent, "Error", message, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void warning(Component parent, String message) {
		message(parent, "Warning", message, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void info(Component parent, String message) {
		message(parent, "Information", message, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void message(Component parent,String title, String message, int type) {
		JOptionPane.showMessageDialog(parent, message, title, type);
	}	
}
