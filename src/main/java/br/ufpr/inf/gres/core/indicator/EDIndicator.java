package br.ufpr.inf.gres.core.indicator;

import br.ufpr.inf.gres.core.util.SolutionSetUtils;
import br.ufpr.inf.gres.gui.task.generator.QualityIndicatorsGenerator;
import java.util.Arrays;
import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDIndicator extends Indicator {

    private static final Logger log = LoggerFactory.getLogger(QualityIndicatorsGenerator.class);

    public EDIndicator() {
        super("ED", "ed");
    }

    @Override
    public double execute(QualityIndicator qi, SolutionSet paretoFront, String file, SolutionSet population) {
        double bestED = Double.MAX_VALUE;

        String filename = FilenameUtils.getName(file);
        if (filename.equalsIgnoreCase("PFKNOWN")) {
            int nObjectives = SolutionSetUtils.getNumberOfObjectives(paretoFront);
            double[][] truePareto = paretoFront.writeObjectivesToMatrix();
            double[][] pop = population.writeObjectivesToMatrix();

            EuclideanDistance euclideanDistance = new EuclideanDistance();

            double[] bestSolution = null;

            double worstED = Double.MIN_VALUE;
            double[] worstSolution = null;

            double distance = 0.0;

            for (double[] pareto : truePareto) {
                for (double[] p : pop) {

                    distance = euclideanDistance.compute(pareto, p);

                    if (bestED > distance || bestSolution == null) {
                        bestED = distance;
                        bestSolution = p;
                    }
                    
                    if (worstED < distance || worstSolution == null) {
                        worstED = distance;
                        worstSolution = p;
                    }                
                }
            }

            log.info("[Best/Worst] Euclidean Distance and Solution: " + bestED + " " + getValues(bestSolution) + " / " + worstED + " " + getValues(worstSolution));
        }
        return bestED;
    }

    private String getValues(double[] value) {
        return "(" + Arrays.toString(value).replace("[", "").replace("]", "") + ")";
    }
}
