package br.ufpr.inf.gres.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ProcessUtils {

	public static String run(String content,String software, String command) throws IOException, InterruptedException{
		File scriptFile = File.createTempFile("script", ".tmp");
		File outputFile = File.createTempFile("output", ".tmp");
		scriptFile.deleteOnExit();
		
		try (FileWriter scriptWriter = new FileWriter(scriptFile)) {
            scriptWriter.append(content);
        }
		
		ProcessBuilder processBuilder = new ProcessBuilder(software, command, scriptFile.getAbsolutePath());
        processBuilder.redirectOutput(outputFile);

        Process process = processBuilder.start();
        process.waitFor();
        
		return FileUtils.readFileToString(outputFile);
	}
}
