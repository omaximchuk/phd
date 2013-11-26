import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class Algorithm {

    Double s0 = 1.0;
    Double fixedCosts = 0.1;
    Double propCosts = 0.09;
    Double time = 1.0;
    Double space = 1.0;
    Integer N = 400;
    Integer M = 20;
    Double[][] v1 = new Double[N][M];
    Double[][] v0 = new Double[N][M];
    Double[][] s = new Double[N][M];
    Integer[][] u0 = new Integer[N][M];
    Integer[][] u1 = new Integer[N][M];
    private Double deltaT = time / N;
    private Double deltaS = space / M;
    private Double mu = 0.5;
    private Double sigma = 0.4;

    public Algorithm() {
        super();
        initArrays();
        processArrays();
    }

    private void initArrays() {
        s[0][0] = s0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                s[i][j] = deltaS * j;
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        // Boundary and terminal conditions
        for (int j = 0; j < M; j++) {
            v0[N - 1][j] = 0.0;
            v1[N - 1][j] = costsSell(s[N - 1][j]);
        }

        for (int i = 0; i < N; i++) {
            v0[i][0] = 0.0;
            v0[i][M - 1] = 0.0;
            v1[i][0] = costsSell(0.0);
            v1[i][M - 1] = costsSell(s[i][M - 1]);
        }
    }

    private void processArrays() {
        for (int i = N - 2; i >= 0; i--)
            for (int j = 1; j < M - 1; j++) {
                double m1 = v0[i + 1][j] + deltaT * A(v0, i + 1, j);
                double m2 = v1[i + 1][j] + costsBuy(s[i][j]);
                double n1 = v1[i + 1][j] + deltaT * A(v1, i + 1, j);
                double n2 = v0[i + 1][j] + costsSell(s[i][j]);
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
        return mu(s[i - 1][j]) * vOverS(v[i][j + 1], v[i][j]) +
                .5 * pow(sigma(s[i - 1][j]), 2) * vOverS2(v[i][j + 1], v[i][j], v[i][j - 1]);
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

    public Integer getN() {
        return N;
    }

    public Integer getM() {
        return M;
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
        for (int i = N - 1; i >= 0; i--) {
            System.out.print("i = " + (i + 1) + ":  ");
            for (int j = 0; j < M; j++) {
                String space = (array[i][j].doubleValue() < 0) ? " " : "  ";
                    if (array[i][j] instanceof Double)
                        System.out.print(BigDecimal.valueOf((Double) array[i][j]).setScale(2, RoundingMode.HALF_UP) + space);
                    else
                        System.out.print(array[i][j] + space);
            }
            System.out.println();
        }
    }

}
