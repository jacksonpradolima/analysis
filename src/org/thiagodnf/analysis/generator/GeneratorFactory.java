package org.thiagodnf.analysis.generator;

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
		}
		
		return null;
	}	
}
