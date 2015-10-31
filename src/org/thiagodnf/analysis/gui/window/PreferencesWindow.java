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

import org.thiagodnf.analysis.util.SettingsUtils;

public class PreferencesWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JFormattedTextField decimalPlatesTextField;
	
	protected JComboBox<String> sdComboBox;
	
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
	    
	    this.decimalPlatesTextField = new JFormattedTextField(formatter);
		this.lookAndFeelComboBox = new JComboBox<String>(getAvailableLookAndFeel());
		this.sdComboBox = new JComboBox<String>(new String[]{"Yes", "No"});
		
		this.decimalPlatesTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.lookAndFeelComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.sdComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(new JLabel("Decimal Places"));
		add(Box.createVerticalStrut(10));
		add(decimalPlatesTextField);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Show Standard Deviation"));
		add(Box.createVerticalStrut(10));
		add(sdComboBox);
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
		
		SettingsUtils.setDecimalPlaces((Integer) decimalPlatesTextField.getValue());		
		SettingsUtils.setLookAndFeel((String)lookAndFeelComboBox.getSelectedItem());
	}	
}
