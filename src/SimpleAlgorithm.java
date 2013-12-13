
import static java.lang.Math.max;


public class SimpleAlgorithm {

    Double s0 = 1.0;
    Double fixedCosts = 0.0;
    Double p = 0.9;
    Double q = 1 - p;
    Double up = 1.1;
    Double down = 0.5;
    Double propCosts = 0.01;
    Integer N = 10;
    Double[][] v1 = new Double[N + 1][N];
    Double[][] v0 = new Double[N + 1][N];
    Double[][] s = new Double[N + 1][N];
    Integer[][] u0 = new Integer[N + 1][N];
    Integer[][] u1 = new Integer[N + 1][N];

    public SimpleAlgorithm() {
        super();
        initArrays();
        processArrays();
    }

    private void initArrays() {
        s[0][0] = s0;
        for (int j = 1; j < N; j++)
            for (int i = 0; i <= j; i++) {
                s[i][j] = Math.pow(up, j - i) * Math.pow(down, i);
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        for (int i = 0; i < N; i++) {
            v0[i][N - 1] = 0.0;
            v1[i][N - 1] = 0.0;//(1 - propCosts) * s[SPACE_STEPS-1][i] - fixedCosts;
        }
    }

    private void processArrays() {
        for (int j = N - 2; j >= 0; j--)
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

}
