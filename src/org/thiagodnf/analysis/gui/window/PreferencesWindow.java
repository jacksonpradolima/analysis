package org.thiagodnf.analysis.gui.window;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.NumberFormatter;

import org.thiagodnf.analysis.util.SettingsUtils;

public class PreferencesWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JFormattedTextField decimalPlatesTextField;
	
	protected JCheckBox standardDeviationCheckBox;
	
	protected JComboBox<String> lookAndFeelComboBox;
	
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
		this.lookAndFeelComboBox = new JComboBox<String>(getAvailableLookAndFeel());
		
		this.standardDeviationCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.decimalPlatesTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.lookAndFeelComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Select on combo the saved look and feel
		this.lookAndFeelComboBox.setSelectedItem(SettingsUtils.getLookAndFeel());
		
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesTextField);
		add(Box.createVerticalStrut(10));
		add(standardDeviationCheckBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Look and Feel"));
		add(Box.createVerticalStrut(10));	
		add(lookAndFeelComboBox);
		
		
		// Load all settings and fresh the window
		if (SettingsUtils.isStandardDeviationVisible()) {
			this.standardDeviationCheckBox.setSelected(true);
		} else {
			this.standardDeviationCheckBox.setSelected(false);
		}
		
		this.decimalPlatesTextField.setText(String.valueOf(SettingsUtils.getDecimalPlaces()));
	}
		
	private String[] getAvailableLookAndFeel() {
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
		SettingsUtils.setLookAndFeel((String)lookAndFeelComboBox.getSelectedItem());
	}	
}
