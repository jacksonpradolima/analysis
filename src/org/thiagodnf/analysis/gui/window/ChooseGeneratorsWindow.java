package org.thiagodnf.analysis.gui.window;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.thiagodnf.analysis.generator.ApproximateTrueParetoFrontGenerator;
import org.thiagodnf.analysis.generator.FUNALLGenerator;
import org.thiagodnf.analysis.generator.AbstractGenerator;
import org.thiagodnf.analysis.generator.KnownParetoFrontGenerator;

public class ChooseGeneratorsWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JCheckBox runKnownParetoFrontGeneratorCheckBox;
	
	protected JCheckBox runApproximateTrueParetoFrontGeneratorCheckBox;
	
	protected JCheckBox runFunAllGeneratorCheckBox;
	
	protected JCheckBox qualityIndicatorsCheckBox;
	
	protected JCheckBox statisticalIndicatorsCheckBox;
	
	public ChooseGeneratorsWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.runFunAllGeneratorCheckBox = getNewJCheckBox("FUNALL Files");
		this.runKnownParetoFrontGeneratorCheckBox = getNewJCheckBox("Known Pareto-front");
		this.runApproximateTrueParetoFrontGeneratorCheckBox = getNewJCheckBox("Approximate True Pareto-front");
		this.qualityIndicatorsCheckBox = getNewJCheckBox("Quality Indicators");
		this.statisticalIndicatorsCheckBox = getNewJCheckBox("Statistical Indicators");
		
		add(runFunAllGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runKnownParetoFrontGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runApproximateTrueParetoFrontGeneratorCheckBox);
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
		Object[] options = { "Run", "Cancel" };

		String title = "Choose the generators";
		int optionType = JOptionPane.YES_NO_CANCEL_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}
	
	public List<AbstractGenerator> getSelectedGenerators(){
		List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();
		
//		if (this.qualityIndicatorsCheckBox.isSelected()) {
//			generators.add(new QualityIndicatorsGenerator(frame, numberOfObjectives, folders));
//		}
//		if (this.statisticalIndicatorsCheckBox.isSelected()) {
//			generators.add(new StatsGenerator(frame, numberOfObjectives, folders));
//		}
		if (this.runFunAllGeneratorCheckBox.isSelected()) {
			generators.add(new FUNALLGenerator(parent));
		}
		if (this.runKnownParetoFrontGeneratorCheckBox.isSelected()) {
			generators.add(new KnownParetoFrontGenerator(parent));
		}
		if (this.runApproximateTrueParetoFrontGeneratorCheckBox.isSelected()) {
			generators.add(new ApproximateTrueParetoFrontGenerator(parent));
		}
		
		return generators;
	}
	
}
