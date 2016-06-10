package br.ufpr.inf.cbiogres.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.Ranking;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * SolutionSet Utils Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class SolutionSetUtils {

	public static SolutionSet getFromFile(File filename) {
		return getFromFile(filename.getAbsolutePath());
	}
	
	public static SolutionSet getFromFile(String filename) {
		SolutionSet population = new SolutionSet(Integer.MAX_VALUE);

		LineIterator it = null;

		int maxObjectives = -1;
		
		try {
			it = FileUtils.lineIterator(new File(filename), "UTF-8");
			while (it.hasNext()) {
				String[] split = it.nextLine().split(" ");
				
				if (split.length == 1 && split[0].isEmpty()) {
					continue;
				}
				
				int numberOfObjectives = split.length;

				if (maxObjectives == -1) {
					maxObjectives = numberOfObjectives;
				} else if (maxObjectives != numberOfObjectives) {
					throw new IllegalArgumentException("The filename " + filename + " has different number of objectives");
				}
				
				Solution s = new Solution(numberOfObjectives);

				for (int i = 0; i < numberOfObjectives; i++) {
					s.setObjective(i, Double.valueOf(split[i]));
				}

				population.add(s);
			}
		} catch (FileNotFoundException ex) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (it != null) {
				it.close();
			}
		}

		return population;
	}
	
	public static SolutionSet removeDominatedSolutions(SolutionSet population) {
		if (population.size() == 0) {
			return population;
		}

		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}
	
	public static SolutionSet removeRepeatedSolutions(SolutionSet population){
		SolutionSet newPopulation = new SolutionSet(population.size());
		
		for (int i = 0; i < population.size(); i++) {
			Solution s = population.get(i);

			if (!contains(newPopulation, s)) {
				newPopulation.add(copy(s));
			}
		}
		
		return newPopulation;
	}
	
	public static Solution copy(Solution s) {
		if (s == null) {
			throw new IllegalArgumentException("Solution s cannot be null");
		}
		
		if (s.getNumberOfObjectives() <= 0) {
			throw new IllegalArgumentException("Solutions cannot be number of objectives less than 0");
		}
		
		Solution copy = new Solution(s.getNumberOfObjectives());

		for (int i = 0; i < s.getNumberOfObjectives(); i++) {
			copy.setObjective(i, s.getObjective(i));
		}

		return copy;
	}
	
	public static boolean contains(SolutionSet population, Solution s1) {
		if (population == null || s1 == null) {
			throw new IllegalArgumentException("SolutionSet and Solution cannot be null");
		}

		for (int i = 0; i < population.size(); i++) {
			Solution s2 = population.get(i);

			if (isEqual(s1, s2)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isEqual(Solution s1, Solution s2) {
		if (s1 == null || s2 == null) {
			throw new IllegalArgumentException("Soluton s1 and s2 cannot be null");
		}
		
		if (s1.getNumberOfObjectives() != s2.getNumberOfObjectives()) {
			throw new IllegalArgumentException("Solutions cannot be different number of objetives");
		}
		
		for (int i = 0; i < s1.getNumberOfObjectives(); i++) {
			
			if (s1.getObjective(i) != s2.getObjective(i)) {
				return false;
			}
		}

		return true;
	}
	
	public static SolutionSet getIntersection(SolutionSet pfk, SolutionSet population) {
		SolutionSet same = new SolutionSet(Integer.MAX_VALUE);

		for (int i = 0; i < population.size(); i++) {
			Solution s = population.get(i);

			if (contains(pfk, s)) {
				same.add(copy(s));
			}
		}

		return same;
	}
	
	public static int getNumberOfObjectives(SolutionSet population){
		int numberOfObjectives = -1;
		
		for (int i = 0; i < population.size(); i++) {
			if (numberOfObjectives == -1) {
				numberOfObjectives = population.get(i).getNumberOfObjectives();
			}
			if (numberOfObjectives != population.get(i).getNumberOfObjectives()) {
				throw new IllegalArgumentException("The number of objectives is diferent");
			}
		}
		
		return numberOfObjectives;
	}
}
