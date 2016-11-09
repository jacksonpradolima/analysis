package br.ufpr.inf.gres.gui.window;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * This class is used to show to user a message box as a dialog, that is, the
 * windows on the top has the focus and the user have to do something on it.
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class MessageBox {

	public static void error(Component parent, Exception ex) {
		message(parent, "Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
		ex.printStackTrace();
	}
	
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
