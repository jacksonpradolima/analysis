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

import org.thiagodnf.analysis.task.generator.AproxParetoFrontGenerator;
import org.thiagodnf.analysis.task.generator.FUNALLGenerator;
import org.thiagodnf.analysis.task.generator.KnownParetoFrontGenerator;
import org.thiagodnf.analysis.task.generator.MaxMinGenerator;
import org.thiagodnf.analysis.task.generator.QualityIndicatorsGenerator;
import org.thiagodnf.analysis.task.generator.SummaryGenerator;

public class ChooseGeneratorsWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JCheckBox runKnownParetoFrontGeneratorCheckBox;
	
	protected JCheckBox runApproximateTrueParetoFrontGeneratorCheckBox;
	
	protected JCheckBox runFunAllGeneratorCheckBox;
	
	protected JCheckBox runQualityIndicatorsGeneratorCheckBox;
	
	protected JCheckBox runSummaryGeneratorCheckBox;
	
	protected JCheckBox runMaxMinGeneratorCheckBox;
	
	public ChooseGeneratorsWindow(JFrame parent){
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.runFunAllGeneratorCheckBox = getNewJCheckBox("FUNALL Files");
		this.runKnownParetoFrontGeneratorCheckBox = getNewJCheckBox("Known Pareto-front");
		this.runApproximateTrueParetoFrontGeneratorCheckBox = getNewJCheckBox("Approximate True Pareto-front");
		this.runQualityIndicatorsGeneratorCheckBox = getNewJCheckBox("Quality Indicators");
		this.runSummaryGeneratorCheckBox = getNewJCheckBox("Summary");
		this.runMaxMinGeneratorCheckBox = getNewJCheckBox("Maximum and Minimum Values");
		
		add(runFunAllGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runKnownParetoFrontGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runApproximateTrueParetoFrontGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runMaxMinGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runQualityIndicatorsGeneratorCheckBox);
		add(Box.createVerticalStrut(10));
		add(runSummaryGeneratorCheckBox);	
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
	
	public List<String> getSelectedGenerators() {
		List<String> generators = new ArrayList<String>();

		if (this.runFunAllGeneratorCheckBox.isSelected()) {
			generators.add(FUNALLGenerator.class.getSimpleName());
		}
		if (this.runKnownParetoFrontGeneratorCheckBox.isSelected()) {
			generators.add(KnownParetoFrontGenerator.class.getSimpleName());
		}
		if (this.runApproximateTrueParetoFrontGeneratorCheckBox.isSelected()) {
			generators.add(AproxParetoFrontGenerator.class.getSimpleName());
		}
		if (this.runMaxMinGeneratorCheckBox.isSelected()) {
			generators.add(MaxMinGenerator.class.getSimpleName());
		}		
		if (this.runQualityIndicatorsGeneratorCheckBox.isSelected()) {
			generators.add(QualityIndicatorsGenerator.class.getSimpleName());
		}
		if (this.runSummaryGeneratorCheckBox.isSelected()) {
			generators.add(SummaryGenerator.class.getSimpleName());
		}
	
		return generators;
	}	
}
