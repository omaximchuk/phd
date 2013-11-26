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
//        SimpleAlgorithm a = new SimpleAlgorithm();
//        a.printArray(a.getU0());
//        System.out.println();
//        a.printArray(a.getU1());

        Algorithm a = new Algorithm();
        a.printArray(a.getU0());
        System.out.println();
        a.printArray(a.getU1());
    }
}
