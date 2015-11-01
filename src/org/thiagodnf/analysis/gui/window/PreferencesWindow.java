package org.thiagodnf.analysis.gui.window;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.thiagodnf.analysis.task.generator.MaxMinGenerator;
import org.thiagodnf.analysis.util.LookAndFeelUtils;
import org.thiagodnf.analysis.util.SettingsUtils;

public class PreferencesWindow extends DialogWindow{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JComboBox<String> decimalPlatesComboBox ;
	
	protected JComboBox<String> sdComboBox;
	
	protected JComboBox<String> lookAndFeelComboBox;
	
	protected JComboBox<String> normalizeHypervolumeComboBox;
	
	public PreferencesWindow(JFrame parent) {
		super(parent, "Preferences", JOptionPane.OK_CANCEL_OPTION);
		
		this.decimalPlatesComboBox = new JComboBox<String>(SettingsUtils.getAvailableDecimalPlaces());
		this.lookAndFeelComboBox = new JComboBox<String>(LookAndFeelUtils.getAvailableLookAndFeel());
		this.sdComboBox = new JComboBox<String>(new String[]{"Yes", "No"});
		this.normalizeHypervolumeComboBox = new JComboBox<String>(new String[]{"Using Default JMetal", "Using Max and Min Generated Values", "Using 0.0 and 1.01 values"});
		
		this.decimalPlatesComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.lookAndFeelComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.sdComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.normalizeHypervolumeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Show Standard Deviation"));
		add(Box.createVerticalStrut(10));
		add(sdComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Normalize Hypervolume"));
		add(Box.createVerticalStrut(10));
		add(normalizeHypervolumeComboBox);
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
			this.normalizeHypervolumeComboBox.setSelectedIndex(0);
		}else if(SettingsUtils.getMaxMinValues() == MaxMinGenerator.MAXMIN){
			this.normalizeHypervolumeComboBox.setSelectedIndex(1);
		}else if(SettingsUtils.getMaxMinValues() == MaxMinGenerator.NORMALIZED){
			this.normalizeHypervolumeComboBox.setSelectedIndex(2);
		}
		
		this.decimalPlatesComboBox.setSelectedItem(String.valueOf(SettingsUtils.getDecimalPlaces()));
	}
		
	public void save() {
		if (((String) sdComboBox.getSelectedItem()).equalsIgnoreCase("Yes")) {
			SettingsUtils.setStandardDeviation(true);
		} else {
			SettingsUtils.setStandardDeviation(false);
		}

		if (normalizeHypervolumeComboBox.getSelectedIndex() == 0) {
			SettingsUtils.setMaxMinValues(MaxMinGenerator.DEFAULT);
		} else if (normalizeHypervolumeComboBox.getSelectedIndex() == 1) {
			SettingsUtils.setMaxMinValues(MaxMinGenerator.MAXMIN);
		} else if (normalizeHypervolumeComboBox.getSelectedIndex() == 2) {
			SettingsUtils.setMaxMinValues(MaxMinGenerator.NORMALIZED);
		}		
		
		SettingsUtils.setDecimalPlaces((String) decimalPlatesComboBox.getSelectedItem());		
		SettingsUtils.setLookAndFeel((String)lookAndFeelComboBox.getSelectedItem());
	}	
}
