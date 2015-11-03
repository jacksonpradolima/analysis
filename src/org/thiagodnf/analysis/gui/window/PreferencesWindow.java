package org.thiagodnf.analysis.gui.window;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.thiagodnf.analysis.util.OptionsUtils;
import org.thiagodnf.analysis.util.SettingsUtils;

/**
 * This class is responsible for show a window to user with all available settings
 * in this tool 
 * 
 * @author Thiago Nascimento
 * @since 2015-11-03
 * @version 1.0.0
 *
 */
public class PreferencesWindow extends DialogWindow{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JComboBox<String> decimalPlatesComboBox ;
	
	protected JComboBox<String> standardeviationComboBox;
	
	protected JComboBox<String> lookAndFeelComboBox;
	
	protected JComboBox<String> normalizeHypervolumeComboBox;
	
	public PreferencesWindow(JFrame parent) {
		super(parent, "Preferences", JOptionPane.OK_CANCEL_OPTION);
		
		this.decimalPlatesComboBox = getComboBox(OptionsUtils.getAvailableDecimalPlaces());
		this.lookAndFeelComboBox = getComboBox(OptionsUtils.getAvailableLookAndFeel());
		this.standardeviationComboBox = getComboBox(OptionsUtils.getAvailableStandardDeviation());
		this.normalizeHypervolumeComboBox = getComboBox(OptionsUtils.getAvailableNormalizeHypervolume());
		
		// Select on combo the saved look and feel
		this.decimalPlatesComboBox.setSelectedItem(SettingsUtils.getDecimalPlaces());
		this.standardeviationComboBox.setSelectedItem(SettingsUtils.getStandardDeviation());
		this.normalizeHypervolumeComboBox.setSelectedItem(SettingsUtils.getNormalizeHypervolume());
		this.lookAndFeelComboBox.setSelectedItem(SettingsUtils.getLookAndFeel());	
				
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Show Standard Deviation"));
		add(Box.createVerticalStrut(10));
		add(standardeviationComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Normalize Hypervolume"));
		add(Box.createVerticalStrut(10));
		add(normalizeHypervolumeComboBox);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Look and Feel"));
		add(Box.createVerticalStrut(10));	
		add(lookAndFeelComboBox);
	}
	
	public JComboBox<String> getComboBox(String[] options) {
		JComboBox<String> cb = new JComboBox<String>(options);
		
		cb.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return cb;
	}
		
	public void save() {
		SettingsUtils.setNormalizeHypervolume((String) normalizeHypervolumeComboBox.getSelectedItem());		
		SettingsUtils.setStandardDeviation((String) standardeviationComboBox.getSelectedItem());
		SettingsUtils.setDecimalPlaces((String) decimalPlatesComboBox.getSelectedItem());		
		SettingsUtils.setLookAndFeel((String)lookAndFeelComboBox.getSelectedItem());
	}	
}
