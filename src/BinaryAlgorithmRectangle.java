import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;


public class BinaryAlgorithmRectangle implements Algorithm {

    private Double mu = Conditions.mu;
    private Double sigma = Conditions.sigma;
    private Double deltaT = Conditions.deltaT;
    private Double h = Conditions.h;

    private Double s0 = Conditions.s0;
    private Double propCosts = Conditions.propCosts;
    private Double fixedCosts = Conditions.fixedCosts;
    private Double p = Conditions.p;
    private Double q = Conditions.q;
    private Double TIME_INTERVAL = Conditions.TIME_INTERVAL;
    private Integer TIME_STEPS = Conditions.TIME_STEPS;
    private Double SPACE_INTERVAL = Conditions.SPACE_INTERVAL;
    private Integer SPACE_STEPS = Conditions.SPACE_STEPS;

    private Double[][] v1 = new Double[SPACE_STEPS][TIME_STEPS];
    private Double[][] v0 = new Double[SPACE_STEPS][TIME_STEPS];
    private Double[][] s = new Double[SPACE_STEPS][TIME_STEPS];
    private int[][] u0 = new int[SPACE_STEPS][TIME_STEPS];
    private int[][] u1 = new int[SPACE_STEPS][TIME_STEPS];
    private BigDecimal[] edgePrice = new BigDecimal[TIME_STEPS];

    public BinaryAlgorithmRectangle() {
        super();
        initArrays();
        processArrays();
        detectPriceSlope();
    }

    private void initArrays() {
        for (int j = 0; j < TIME_STEPS; j++) {
            int middle = (SPACE_STEPS + 1) / 2 - 1;
            s[middle][j] = s0;
            for (int i = 1; i <= middle; i++) {
                //s[i][j] = s0 * pow(up(), SPACE_STEPS - i) * pow(down(), i);
                s[middle - i][j] = pow(up(), i);
                s[middle + i][j] = pow(down(), i);
            }
        }
        for (int i = 0; i < SPACE_STEPS; i++) {
            v0[i][0] = 0.0;
            v1[i][0] = -fixedCosts;
            v0[i][TIME_STEPS - 1] = 0.0;
            v1[i][TIME_STEPS - 1] = (1 - propCosts) * s[i][TIME_STEPS - 1] - fixedCosts;
        }
        for (int j = 0; j < TIME_STEPS; j++) {
            v0[0][j] = 0.0;
            v1[0][j] = -fixedCosts;
            v0[SPACE_STEPS - 1][j] = 0.0;
            v1[SPACE_STEPS - 1][j] = (1 - propCosts) * s[SPACE_STEPS - 1][j] - fixedCosts;
            edgePrice[j] = BigDecimal.ZERO;
        }
    }

    private Double down() {
        return 1 / up();
        //return 1 + mu * deltaT - sigma * sqrt(deltaT);
    }

    private Double up() {
        return exp(h);
        //return 1 + mu * deltaT + sigma * sqrt(deltaT);
    }

    private void processArrays() {
        for (int j = TIME_STEPS - 2; j >= 0; j--)
            for (int i = 1; i < SPACE_STEPS - 1; i++) {
                Double m1 = f(v0[i - 1][j + 1], v0[i + 1][j + 1]);
                Double n1 = f(v1[i - 1][j + 1], v1[i + 1][j + 1]);
                Double m2 = (-1 - propCosts) * s[i][j] - fixedCosts + n1;
                Double n2 = (1 - propCosts) * s[i][j] - fixedCosts + m1;
                v0[i][j] = Math.max(m1, m2);
                v1[i][j] = Math.max(n1, n2);
                u0[i][j] = m1 > m2 ? 0 : -1;
                u1[i][j] = n1 > n2 ? 0 : 1;
            }
    }

    private void detectPriceSlope() {
        for (int j = TIME_STEPS - 1; j >= 1; j--)
            for (int i = 0; i < SPACE_STEPS; i++)
                try {
                    if (u0[i][j] != u0[i][j - 1]) {
                        edgePrice[j] = BigDecimal.valueOf(s[i][j - 1]);
                        continue;
                    }
                } catch (NullPointerException e) {
                    System.out.println("i=" + i + "; j=" + j);
                }
    }

    public void printU0() {
        printControlArray(u0);
    }

    public void printU1() {
        printControlArray(u1);
    }

    private void printControlArray(int[][] array) {
        for (int j = TIME_STEPS - 2; j >= 0; j--) {
            printInfo(j);
            for (int i = SPACE_STEPS-1; i >= 0; i--)
                if (array[i][j] >= 0)
                    System.out.print(" " + array[i][j] + ":" + i + " | ");
                else
                    System.out.print(array[i][j] + ":" + i + " | ");
            System.out.println();
        }
    }

    public void printPrice() {
        printArray(s);
    }

    public void printV0() {
        printArray(v0);
    }

    public void printV1() {
        printArray(v1);
    }

    private void printArray(Number[][] array) {
        for (int j = TIME_STEPS - 1; j >= 0; j--) {
            printInfo(j);
            for (int i = SPACE_STEPS - 1; i >= 0; i--)
                if (array[i][j].doubleValue() >= 0)
                    System.out.print(" " + getBigDecimalFrom(array[i][j]) + ":" + (i + 1) + " | ");
                else
                    System.out.print(getBigDecimalFrom(array[i][j]) + ":" + (i + 1) + " | ");
            System.out.println();
        }
    }

    public void printAnalyticalLine() {
        for (int i = 0; i < TIME_STEPS; i++) {
            System.out.print(getAnalyticalS(i) + " ");
        }
    }

    private BigDecimal getAnalyticalS(int i) {
        Double result = 2 * fixedCosts / (exp(mu * (TIME_INTERVAL - i * deltaT)) - 1);
        return BigDecimal.valueOf(result.doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getBigDecimalFrom(Number number) {
        return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }

    private void printInfo(int j) {
        System.out.print("time step = " + (j + 1) + ":\t\t\t");
    }

    public void printEdgePrice() {
        System.out.println("Edge price on [0, " + TIME_INTERVAL + "] interval");
        for (int i = 0; i < edgePrice.length; i++)
            System.out.print(edgePrice[i].setScale(2, RoundingMode.HALF_UP) + " ");
    }

    Double f(Double v, Double v1) {
        return p * v + q * v1;
    }

}
