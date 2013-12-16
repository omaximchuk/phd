
import static java.lang.Math.max;
import static java.lang.Math.sqrt;


public class SimpleAlgorithm {

    private Double s0 = 1.0;
    private Double propCosts = 0.01;
    private Double fixedCosts = 0.1;
    private Double p = 0.9;
    private Double q = 1 - p;
    private Integer TIME_STEPS = 33;

    private Double mu;
    private Double sigma;
    private Double deltaT;

    private Double[][] v1 = new Double[TIME_STEPS + 1][TIME_STEPS];
    private Double[][] v0 = new Double[TIME_STEPS + 1][TIME_STEPS];
    private Double[][] s = new Double[TIME_STEPS + 1][TIME_STEPS];
    private Integer[][] u0 = new Integer[TIME_STEPS + 1][TIME_STEPS];
    private Integer[][] u1 = new Integer[TIME_STEPS + 1][TIME_STEPS];


    public SimpleAlgorithm(Algorithm algorithm) {
        super();
        mu = algorithm.getMu();
        sigma = algorithm.getSigma();
        deltaT = algorithm.getDeltaT();
        propCosts = algorithm.getProportionalCosts();
        fixedCosts = algorithm.getFixedCosts();
        initArrays();
        processArrays();
    }

    private void initArrays() {
        s[0][0] = s0;
        for (int j = 1; j < TIME_STEPS; j++)
            for (int i = 0; i <= j; i++) {
                s[i][j] = Math.pow(up(mu, sigma, deltaT), j - i) * Math.pow(down(mu, sigma, deltaT), i);
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        for (int i = 0; i < TIME_STEPS; i++) {
            v0[i][TIME_STEPS - 1] = 0.0;
            v1[i][TIME_STEPS - 1] = 0.0;//(1 - propCosts) * s[SPACE_STEPS - 1][i] - fixedCosts;
        }
    }

    private Double down(Double mu, Double sigma, Double deltaT) {
        return 1 + mu * deltaT - sigma * sqrt(deltaT);
    }

    private Double up(Double mu, Double sigma, Double deltaT) {
        return 1 + mu * deltaT + sigma * sqrt(deltaT);
    }

    private void processArrays() {
        for (int j = TIME_STEPS - 2; j >= 0; j--)
            for (int i = 0; i <= j; i++) {
                Double m1 = f(v0[i][j + 1], v0[i + 1][j + 1]);
                Double n1 = f(v1[i][j + 1], v1[i + 1][j + 1]);
                Double m2 = (-1 - propCosts) * s[i][j] - fixedCosts + n1;
                Double n2 = (1 - propCosts) * s[i][j] - fixedCosts + m1;
                v0[i][j] = Math.max(m1, m2);
                v1[i][j] = Math.max(n1, n2);
                u0[i][j] = m1 > m2 ? 0 : -1;
                u1[i][j] = n1 > n2 ? 0 : 1;
            }
    }

    public void printArray(Number[][] array) {
        for (int j = array.length - 2; j >= 0; j--) {
            System.out.print("time step = " + (j + 1) + ":" + new String(new char[array.length - j]).replace("\0", "  "));
            for (int i = 0; i <= j; i++)
                if (array[i][j].doubleValue() >= 0)
                    System.out.print((j != i) ? " " + array[i][j] + " | " : " " + array[i][j]);
                else
                    System.out.print((j != i) ? array[i][j] + " | " : array[i][j]);

            System.out.println();
        }
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

    public Number[][] getS() {
        return s;
    }

    public Number[][] getU0() {
        return u0;
    }

    public Number[][] getU1() {
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
}
