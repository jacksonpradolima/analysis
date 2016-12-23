package br.ufpr.inf.gres.gui.task.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;

import br.ufpr.inf.gres.core.util.LoggerUtils;

import com.google.common.base.Preconditions;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.util.PointSolution;

/**
 * PFKnown Generator Class
 * 
 * @author Thiago Nascimento and Jackson Antonio do Prado Lima
 * @since 2015-10-27
 * @version 1.0.0
 */
public abstract class ParetoFrontGenerator extends Generator {
	
	protected final static Logger logger = LoggerUtils.getLogger(ParetoFrontGenerator.class.getName());
	
	protected String inputFile;
	
	protected String outputFile;
	
	protected String ignoreFile;
	
	public ParetoFrontGenerator(JFrame parent, File[] folder, String inputFile, String outputFile, String ignoreFile) {
		super(parent, folder);
		
		Preconditions.checkNotNull(inputFile, "Input file cannot be null");
		Preconditions.checkNotNull(outputFile, "Output file cannot be null");
		Preconditions.checkArgument(!inputFile.isEmpty(), "Input file cannot be empty");
		Preconditions.checkArgument(!outputFile.isEmpty(), "Output file cannot be empty");
		
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.ignoreFile = ignoreFile;
	}
	
	protected void generate(String folder, List<String> files, String filename, int totalOfFiles) throws Exception {
		
		Preconditions.checkNotNull(folder, "Folder cannot be null");
		Preconditions.checkArgument(!folder.isEmpty(), "Folder cannot be empty");
		
		logger.info("Generating for folder: " + folder);
				
                NonDominatedSolutionListArchive<PointSolution> nonDominatedSolutionArchive = new NonDominatedSolutionListArchive<>();
                
		for (String file : files) {
			updateNote(getCurrentProgress() + " from " + totalOfFiles);
			
			logger.info("Reading the fun file: " + file);
                        
                        Front front = new ArrayFront(file);
                        List<PointSolution> solutionList = FrontUtils.convertFrontToSolutionList(front);
			
			logger.info(solutionList.size() + " found solutions.");
			                       
                        for (PointSolution solution : solutionList) {                            
                            nonDominatedSolutionArchive.add(solution);
                        }
                        
			updateProgress();
		}
		
		logger.info(nonDominatedSolutionArchive.getSolutionList().size() + " found solutions. Saving the " + filename + " file");

                new SolutionListOutput(nonDominatedSolutionArchive.getSolutionList()).printObjectivesToFile(folder + filename);		
	}
	
	protected Void doInBackground() throws Exception {
		
		List<String> files = getFilesStartingWith(folders, inputFile, ignoreFile);
		
		logger.info(files.size() + " has been found");

		updateMaximum(files.size());

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String filename : files) {
			updateNote("Sorting files " + getCurrentProgress() + " from " + files.size());

			String key = sortingFilesBy(filename);

			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}

			map.get(key).add(filename);

			updateProgress();
		}
		
		updateMaximum(files.size());
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			generate(entry.getKey(), entry.getValue(), outputFile, files.size());
		}
		
		afterFinishing();
		
		logger.info("Done");
		
		return null;
	}
	
	protected abstract String sortingFilesBy(String filename);
}