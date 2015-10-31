package org.thiagodnf.analysis.gui.window;

import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.thiagodnf.analysis.util.SettingsUtils;

public class PreferencesWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JFormattedTextField decimalPlatesTextField;
	
	protected JCheckBox standardDeviationCheckBox;
	
	public PreferencesWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		NumberFormat format = NumberFormat.getNumberInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(10);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
		
		this.standardDeviationCheckBox = new JCheckBox("Show Standard Deviation");
		this.decimalPlatesTextField = new JFormattedTextField(formatter);
		
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesTextField);
		add(Box.createVerticalStrut(10));
		add(standardDeviationCheckBox);
		
		// Load all settings and fresh the window
		if (SettingsUtils.isStandardDeviationVisible()) {
			this.standardDeviationCheckBox.setSelected(true);
		} else {
			this.standardDeviationCheckBox.setSelected(false);
		}
		
		this.decimalPlatesTextField.setText(String.valueOf(SettingsUtils.getDecimalPlaces()));
	}
		
	public int showOptionDialog(){
		Object[] options = { "Save", "Cancel" };

		String title = "Preferences";
		int optionType = JOptionPane.YES_NO_CANCEL_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}
	
	public void save() {
		SettingsUtils.setDecimalPlaces((Integer) decimalPlatesTextField.getValue());
		SettingsUtils.setStandardDeviation(standardDeviationCheckBox.isSelected());
	}	
}
