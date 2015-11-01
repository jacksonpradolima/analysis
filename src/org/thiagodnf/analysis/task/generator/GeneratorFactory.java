package org.thiagodnf.analysis.task.generator;

import java.io.File;

import javax.swing.JFrame;

public class GeneratorFactory {
	
	public static Generator getGenerator(JFrame parent, String generatorName, File[] folders) {
		if (generatorName.equalsIgnoreCase(FUNALLGenerator.class.getSimpleName())) {
			return new FUNALLGenerator(parent, folders);
		} else if (generatorName.equalsIgnoreCase(AproxParetoFrontGenerator.class.getSimpleName())) {
			return new AproxParetoFrontGenerator(parent, folders);
		} else if (generatorName.equalsIgnoreCase(KnownParetoFrontGenerator.class.getSimpleName())) {
			return new KnownParetoFrontGenerator(parent, folders);
		}else if (generatorName.equalsIgnoreCase(QualityIndicatorsGenerator.class.getSimpleName())) {
			return new QualityIndicatorsGenerator(parent, folders);
		}else if (generatorName.equalsIgnoreCase(SummaryGenerator.class.getSimpleName())) {
			return new SummaryGenerator(parent, folders);
		}else if (generatorName.equalsIgnoreCase(MaxMinGenerator.class.getSimpleName())) {
			return new MaxMinGenerator(parent, folders);
		}
		
		return null;
	}	
}
