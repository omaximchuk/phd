import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;


public class BinaryAlgorithmRectangle {

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
    private Integer[][] u0 = new Integer[SPACE_STEPS][TIME_STEPS];
    private Integer[][] u1 = new Integer[SPACE_STEPS][TIME_STEPS];
    private BigDecimal[] edgePrice = new BigDecimal[TIME_STEPS];

    public BinaryAlgorithmRectangle(Double s0) {
        super();
        this.s0 = s0;
        initArrays();
        processArrays();
        detectPriceSlope();
    }

    private void initArrays() {
        for (int j = 0; j < TIME_STEPS; j++)
            for (int i = 0; i < SPACE_STEPS; i++) {
                s[i][j] = s0 * pow(up(), SPACE_STEPS - i) * pow(down(), i);
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        for (int i = 0; i < SPACE_STEPS; i++) {
            v0[i][0] = 0.0;
            v1[i][0] = -fixedCosts;
            v0[i][TIME_STEPS - 1] = 0.0;
            v1[i][TIME_STEPS - 1] = 0.0;//(1 - propCosts) * s[i][TIME_STEPS - 1] - fixedCosts;
        }
        for (int j = 0; j < TIME_STEPS; j++) {
            v0[0][j] = 0.0;
            v1[0][j] = -fixedCosts;
            v0[SPACE_STEPS - 1][j] = 0.0;
            v1[SPACE_STEPS - 1][j] = 0.0;
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
            for (int i = 0; i < SPACE_STEPS - 1; i++) {
                Double m1 = f(v0[i][j + 1], v0[i + 1][j + 1]);
                Double n1 = f(v1[i][j + 1], v1[i + 1][j + 1]);
                Double m2 = (-1 - propCosts) * s[i][j] - fixedCosts + n1;
                Double n2 = (1 - propCosts) * s[i][j] - fixedCosts + m1;
                v0[i + 1][j] = Math.max(m1, m2);
                v1[i + 1][j] = Math.max(n1, n2);
                u0[i + 1][j] = m1 > m2 ? 0 : -1;
                u1[i + 1][j] = n1 > n2 ? 0 : 1;
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

    public void printControlArray(Integer[][] array) {
        for (int j = TIME_STEPS - 2; j >= 0; j--) {
            printInfo(array, j);
            for (int i = 0; i < SPACE_STEPS; i++)
                if (array[i][j].doubleValue() >= 0)
                    System.out.print(" " + array[i][j] + " | ");
                else
                    System.out.print(array[i][j] + " | ");
            System.out.println();
        }
    }

    public void printArray(Number[][] array) {
        for (int j = TIME_STEPS - 1; j >= 0; j--) {
            printInfo(array, j);
            for (int i = 0; i < SPACE_STEPS; i++)
                if (array[i][j].doubleValue() >= 0)
                    System.out.print(" " + getBigDecimalFrom(array[i][j]) + " | ");
                else
                    System.out.print(getBigDecimalFrom(array[i][j]) + " | ");
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

    public void printWholeArray(Number[][] array) {
        for (int j = array.length - 2; j >= 0; j--) {
            //System.out.print("time step = " + (j + 1) + ": ");
            for (int i = array.length - 1; i >= 0; i--)
                if (array[i][j] == null)
                    System.out.print(new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP) + " | ");
                else
                    System.out.print(BigDecimal.valueOf(array[i][j].doubleValue()).setScale(2, RoundingMode.HALF_UP) + " | ");
            System.out.println();
        }
    }

    public static BigDecimal getBigDecimalFrom(Number number) {
        return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }

    private void printInfo(Number[][] array, int j) {
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

    public void setMu(Double my) {
        this.mu = mu;
    }

    public void setSigma(Double sigma) {
        this.sigma = sigma;
    }

    public Double[][] getS() {
        return s;
    }

    public Double getS0() {
        return s0;
    }

    public Integer[][] getU0() {
        return u0;
    }

    public Integer[][] getU1() {
        return u1;
    }

    public Number[][] getV1() {
        return v1;
    }

    public Number[][] getV0() {
        return v0;
    }

    public void setDeltaT(Double deltaT) {
        this.deltaT = deltaT;
    }

    public Double getH() {
        return h;
    }

    public Integer getSpaceSteps() {
        return SPACE_STEPS;
    }
}
