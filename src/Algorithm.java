import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

public class Algorithm {

    private Double fixedCosts = 0.01;
    private Double propCosts = 0.00;

    private Double mu = 0.4;
    private Double sigma = 0.4;

    public static Double TIME_INTERVAL = 1.0;
    private Double SPACE_INTERVAL = 50.0;

    private static Double deltaT = 0.01;
    private double h = sigma * sqrt(deltaT);
    private Double deltaS = exp(h);

    public static Integer TIME_STEPS = (int)floor(TIME_INTERVAL/deltaT);
    private Integer SPACE_STEPS = (int)floor(SPACE_INTERVAL/deltaS);

    private Double[][] v1 = new Double[SPACE_STEPS][TIME_STEPS];
    private Double[][] v0 = new Double[SPACE_STEPS][TIME_STEPS];
    private Double[][] s = new Double[SPACE_STEPS][TIME_STEPS];
    private Integer[][] u0 = new Integer[SPACE_STEPS][TIME_STEPS];
    private Integer[][] u1 = new Integer[SPACE_STEPS][TIME_STEPS];



    public Algorithm() {
        super();
        initArrays();
        processArrays();
    }

    private void initArrays() {
        for (int i = 0; i < SPACE_STEPS; i++)
            for (int j = 0; j < TIME_STEPS; j++) {
                s[i][j] = exp(i*h);
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        // Boundary conditions
        for (int i = 0; i < SPACE_STEPS; i++) {
            v0[i][TIME_STEPS - 1] = 0.0;
            v1[i][TIME_STEPS - 1] = costsSell(s[i][TIME_STEPS - 1]);
        }
        // Terminal conditions
        for (int j = 0; j < TIME_STEPS; j++) {
            v0[0][j] = 0.0;
            v0[SPACE_STEPS - 1][j] = 0.0;
            v1[0][j] = costsSell(0.0);
            v1[SPACE_STEPS - 1][j] = costsSell(s[SPACE_STEPS - 1][j]);
        }
    }

    private void processArrays() {
        for (int j = TIME_STEPS - 2; j >= 0; j--)
            for (int i = 1; i < SPACE_STEPS - 1; i++) {
                double m1 = v0[i][j + 1] + deltaT * A(v0, i, j + 1);
                double m2 = v1[i][j + 1] + costsBuy(s[i][j]);
                double n1 = v1[i][j + 1] + deltaT * A(v1, i, j + 1);
                double n2 = v0[i][j + 1] + costsSell(s[i][j]);
                v1[i][j] = max(n1, n2);
                v0[i][j] = max(m1, m2);
                u0[i][j] = m1 > m2 ? 0 : -1;
                u1[i][j] = n1 > n2 ? 0 : 1;
            }
    }

    private Double costsSell(Double price) {
        return (1 - propCosts) * price - fixedCosts;
    }

    private Double costsBuy(Double price) {
        return (-1 - propCosts) * price - fixedCosts;
    }

    private Double A(Double[][] v, Integer i, Integer j) {
        return mu(s[i][j - 1]) * vOverS(v[i + 1][j], v[i][j]) +
                .5 * pow(sigma(s[i][j - 1]), 2) * vOverS2(v[i + 1][j], v[i][j], v[i - 1][j]);
    }

    private Double vOverS(Double v1, Double v0) {
        return (v1 - v0) / deltaS;
    }

    private Double vOverS2(Double v2, Double v1, Double v0) {
        return (v2 - 2 * v1 + v0) / pow(deltaS, 2);
    }

    private Double mu(Double price) {
        return mu;
    }

    private Double sigma(Double price) {
        return sigma;
    }

    public Number[][] getV1() {
        return v1;
    }

    public Integer getSPACE_STEPS() {
        return SPACE_STEPS;
    }

    public Integer getTIME_STEPS() {
        return TIME_STEPS;
    }

    public Number[][] getS() {
        return s;
    }

    public Number[][] getU0() {
        return u0;
    }

    public Number[][] getU1() {
        return u1;
    }

    public void printArray(Number[][] array) {
        for (int j = TIME_STEPS - 1; j >= 0; j--) {
            System.out.print("time step = " + (j + 1) + ":  ");
            for (int i = 0; i < SPACE_STEPS; i++) {
                String space = (array[i][j].doubleValue() < 0) ? " " : "  ";
                if (array[i][j] instanceof Double)
                    System.out.print(BigDecimal.valueOf((Double) array[i][j]).setScale(2, RoundingMode.HALF_UP) + space);
                else
                    System.out.print(array[i][j] + space);
            }
            System.out.println();
        }
    }

    public Double getMu() {
        return mu;
    }

    public Double getSigma() {
        return sigma;
    }

    public Double getDeltaT() {
        return deltaT;
    }

    public Double getProportionalCosts() {
        return propCosts;
    }

    public Double getFixedCosts() {
        return fixedCosts;
    }

    public Double getTIME_INTERVAL() {
        return TIME_INTERVAL;
    }

    public Double getSPACE_INTERVAL() {
        return SPACE_INTERVAL;
    }

    public Double getDeltaS() {
        return deltaS;
    }

    public Double getH() {
        return h;
    }
}
