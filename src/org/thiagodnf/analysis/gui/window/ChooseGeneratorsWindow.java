package org.thiagodnf.analysis.gui.window;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.thiagodnf.analysis.generator.FUNALLGenerator;
import org.thiagodnf.analysis.generator.Generator;
import org.thiagodnf.analysis.generator.PFKnownGenerator;

public class ChooseGeneratorsWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JCheckBox runPFknownGeneratorCheckBox;
	
	protected JCheckBox runFunAllGeneratorCheckBox;
	
	protected JCheckBox qualityIndicatorsCheckBox;
	
	protected JCheckBox statisticalIndicatorsCheckBox;
	
	public ChooseGeneratorsWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.runPFknownGeneratorCheckBox = getNewJCheckBox("Known Pareto Front");
		this.qualityIndicatorsCheckBox = getNewJCheckBox("Quality Indicators");
		this.statisticalIndicatorsCheckBox = getNewJCheckBox("Statistical Indicators");
		this.runFunAllGeneratorCheckBox = getNewJCheckBox("FUNALL Files");
		
		add(runFunAllGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runPFknownGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(qualityIndicatorsCheckBox);
		add(Box.createVerticalStrut(10));
		add(statisticalIndicatorsCheckBox);
	}
	
	protected JCheckBox getNewJCheckBox(String name) {
		JCheckBox cb = new JCheckBox(name);
		cb.setHorizontalAlignment(SwingConstants.RIGHT);
		return cb;
	}
	
	public int showOptionDialog(){
		Object[] options = { "Run", "Cancel"};
		
		return JOptionPane.showOptionDialog(parent, this, "Choose the generators",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
	}
	
	public List<Generator> getSelectedGenerators(){
		List<Generator> generators = new ArrayList<Generator>();
		
//		if (this.pfkCheckBox.isSelected()) {
//			generators.add(new PFKnownGenerator(frame, numberOfObjectives, folders));
//		}
//		if (this.qualityIndicatorsCheckBox.isSelected()) {
//			generators.add(new QualityIndicatorsGenerator(frame, numberOfObjectives, folders));
//		}
//		if (this.statisticalIndicatorsCheckBox.isSelected()) {
//			generators.add(new StatsGenerator(frame, numberOfObjectives, folders));
//		}
		if (this.runFunAllGeneratorCheckBox.isSelected()) {
			generators.add(new FUNALLGenerator(parent));
		}
		if (this.runPFknownGeneratorCheckBox.isSelected()) {
			generators.add(new PFKnownGenerator(parent));
		}
		
		return generators;
	}
	
}
