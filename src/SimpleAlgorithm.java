
import static java.lang.Math.max;


public class SimpleAlgorithm {

    Double s0 = 1.0;
    Double c1 = 0.06;
    Double p = 0.9;
    Double q = 1 - p;
    Double a = 1.1;
    Double b = 0.9;
    Double c = 0.0;
    Integer N = 50;
    Double[][] v1 = new Double[N][N + 1];
    Double[][] v0 = new Double[N][N + 1];
    Double[][] s = new Double[N][N + 1];
    Integer[][] u0 = new Integer[N][N + 1];
    Integer[][] u1 = new Integer[N][N + 1];

    public SimpleAlgorithm() {
        super();
        initArrays();
        processArrays();
    }

    private void initArrays() {
        s[0][0] = s0;
        for (int i = 1; i < s.length; i++)
            for (int j = 0; j <= i; j++) {
                s[i][j] = Math.pow(a, i - j) * Math.pow(b, j);
                u0[i][j] = 0;
                u1[i][j] = 0;
            }
        for (int i = 0; i < N; i++) {
            v0[N - 1][i] = 0.0;
            v1[N - 1][i] = 0.0;
        }
    }

    private void processArrays() {
        for (int i = N - 2; i >= 0; i--)
            for (int j = 0; j <= i; j++) {
                Double m1 = f(v0[i + 1][j], v0[i + 1][j + 1]);
                Double n1 = f(v1[i + 1][j], v1[i + 1][j + 1]);
                Double m2 = (-1 - c) * s[i][j] - c1 + n1;
                Double n2 = (1 - c) * s[i][j] - c1 + m1;
                v0[i][j] = Math.max(m1, m2);
                v1[i][j] = Math.max(n1, n2);
                u0[i][j] = m1 > m2 ? 0 : -1;
                u1[i][j] = n1 > n2 ? 0 : 1;
            }
    }

    public void printArray(Number[][] array) {
        for (int i = array.length - 2; i >= 0; i--) {
            System.out.print("i = " + (i + 1) + ":" + new String(new char[array.length - i]).replace("\0", "  "));
            for (int j = 0; j <= i; j++)
                if (array[i][j].doubleValue() >= 0)
                    System.out.print((j != i) ? " " + array[i][j] + " | " : " " + array[i][j]);
                else
                    System.out.print((j != i) ? array[i][j] + " | " : array[i][j]);

            System.out.println();
        }
    }

    Double a(Double s) {
        return a - s;
    }

    Double b(Double s) {
        return b - s;
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
