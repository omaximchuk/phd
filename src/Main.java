import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.MultiColorScatter;

import static java.lang.Math.max;

public class Main {

    public static void main(String[] args) {
        SimpleAlgorithm simpleAlgorithm = new SimpleAlgorithm();
        Algorithm algorithm = new Algorithm();

        simpleAlgorithm.setMu(algorithm.getMu());
        simpleAlgorithm.setSigma(algorithm.getSigma());

//        simpleAlgorithm.printArray(simpleAlgorithm.getU0());
//        System.out.println();
//        simpleAlgorithm.printArray(simpleAlgorithm.getU1());

        System.out.println("space step = " + algorithm.SPACE_STEPS);
        System.out.println(" time step = " + algorithm.TIME_STEPS);
        algorithm.printArray(algorithm.getU0());
        System.out.println();
        algorithm.printArray(algorithm.getU1());
    }
}
