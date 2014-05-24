import static java.lang.Math.*;

public class Conditions {

    public static Double mu = 0.4;
    public static Double sigma = 0.8;
    public static Double deltaT = 0.05;
    public static Double h = sigma * sqrt(deltaT);
    public static Double deltaS = exp(h);
    public static Double s0 = 1.0;
    public static Double propCosts = 0.0;
    public static Double fixedCosts = 0.01;
    public static Double p = .5 + 1 / (2 * sigma) * (mu - .5 * pow(sigma, 2)) * sqrt(deltaT);
    public static Double q = 1 - p;
    public static Double TIME_INTERVAL = 1.0;
    public static Integer TIME_STEPS = (int) floor(TIME_INTERVAL / deltaT);
    public static Double SPACE_INTERVAL = 10.0;
    public static Integer SPACE_STEPS = 2 * TIME_STEPS - 1;

}
