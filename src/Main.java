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
        Algorithm algorithm = new Algorithm();
        SimpleAlgorithm simpleAlgorithm = new SimpleAlgorithm(algorithm);

        simpleAlgorithm.printArray(simpleAlgorithm.getU0());
        System.out.println();
        simpleAlgorithm.printArray(simpleAlgorithm.getU1());

        System.out.println(" time step = " + algorithm.getTIME_STEPS());
        System.out.println("space step = " + algorithm.getSPACE_STEPS());
        algorithm.printArray(algorithm.getU0());
        System.out.println();
        algorithm.printArray(algorithm.getU1());
    }
}
