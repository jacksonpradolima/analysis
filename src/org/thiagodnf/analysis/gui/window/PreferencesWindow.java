package org.thiagodnf.analysis.gui.window;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.NumberFormatter;

import org.thiagodnf.analysis.task.generator.MaxMinGenerator;
import org.thiagodnf.analysis.util.SettingsUtils;

public class PreferencesWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JFormattedTextField decimalPlatesTextField;
	
	protected JComboBox<String> sdComboBox;
	
	protected JComboBox<String> lookAndFeelComboBox;
	
	protected JComboBox<String> normalizedComboBox;
	
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
	    
	    this.decimalPlatesTextField = new JFormattedTextField(formatter);
		this.lookAndFeelComboBox = new JComboBox<String>(getAvailableLookAndFeel());
		this.sdComboBox = new JComboBox<String>(new String[]{"Yes", "No"});
		this.normalizedComboBox = new JComboBox<String>(new String[]{"Using Default JMetal", "Using Max and Min Generated Values", "Using 0.0 and 1.01 values"});
		
		this.decimalPlatesTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.lookAndFeelComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.sdComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.normalizedComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesTextField);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Show Standard Deviation"));
		add(Box.createVerticalStrut(10));
		add(sdComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Normalize Hypervolume"));
		add(Box.createVerticalStrut(10));
		add(normalizedComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Look and Feel"));
		add(Box.createVerticalStrut(10));	
		add(lookAndFeelComboBox);
		
		// Select on combo the saved look and feel
		this.lookAndFeelComboBox.setSelectedItem(SettingsUtils.getLookAndFeel());
				
				
		// Load all settings and fresh the window
		if (SettingsUtils.isStandardDeviationVisible()) {
			this.sdComboBox.setSelectedItem("Yes");			
		} else {
			this.sdComboBox.setSelectedItem("No");
		}
		
		if(SettingsUtils.getMaxMinValues() == MaxMinGenerator.DEFAULT){
			this.normalizedComboBox.setSelectedIndex(0);
		}else if(SettingsUtils.getMaxMinValues() == MaxMinGenerator.MAXMIN){
			this.normalizedComboBox.setSelectedIndex(1);
		}else if(SettingsUtils.getMaxMinValues() == MaxMinGenerator.NORMALIZED){
			this.normalizedComboBox.setSelectedIndex(2);
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
		if(((String) sdComboBox.getSelectedItem()).equalsIgnoreCase("Yes")){
			SettingsUtils.setStandardDeviation(true);
		}else{
			SettingsUtils.setStandardDeviation(false);
		}
		
		if(normalizedComboBox.getSelectedIndex() == 0){
			SettingsUtils.setMaxMinValues(MaxMinGenerator.DEFAULT);
		}else if(normalizedComboBox.getSelectedIndex() == 1){
			SettingsUtils.setMaxMinValues(MaxMinGenerator.MAXMIN);
		} else if(normalizedComboBox.getSelectedIndex() == 2){
			SettingsUtils.setMaxMinValues(MaxMinGenerator.NORMALIZED);
		}			
		
		SettingsUtils.setDecimalPlaces((Integer) decimalPlatesTextField.getValue());		
		SettingsUtils.setLookAndFeel((String)lookAndFeelComboBox.getSelectedItem());
	}	
}
