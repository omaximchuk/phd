public class Main {


    public static void main(String[] args) {
        BinaryAlgorithmRectangle r = new BinaryAlgorithmRectangle(1.0);
        r.printArray(r.getU1());
    }

    private static void showInitialConditions(Algorithm algorithm) {
        System.out.println("time interval = " + algorithm.getTIME_INTERVAL());
        System.out.println("delta t = " + algorithm.getDeltaT());
        System.out.println("# time steps = " + algorithm.getTIME_STEPS());
        System.out.println("space interval = " + algorithm.getSPACE_INTERVAL());
        System.out.println("delta s = " + algorithm.getDeltaS());
        System.out.println("# space steps = " + algorithm.getSPACE_STEPS());
        System.out.println("sigma = " + algorithm.getSigma());
        System.out.println("mu = " + algorithm.getMu());
    }

    static void compareAndPrintFirst(BinaryAlgorithm s1, BinaryAlgorithm s2) {
        Double[][] price1 = s1.getS();
        Double[][] price2 = s2.getS();
        Number[][] u01 = s1.getU0();
        for (int j = s1.getTIME_STEPS() - 1; j >= 0; j--) {
            System.out.print("time step = " + (j + 1) + new String(new char[price1.length - j]).replace("\0", "  "));
            for (int i = 0; i < j; i++) {
//                if (price1[i][j] <= price2[j][j])
//                    continue;
                //System.out.print(" " + BinaryAlgorithm.getBigDecimalFrom(price1[i][j]) + " |");
                if (u01[i][j] != u01[i+1][j])
                    System.out.print("index = " + i + " space = " + price1[i][j]);
                //System.out.print(" " + u01[i][j] + " |");
            }
            System.out.println();
        }
    }

    static void compareAndPrintSecond(BinaryAlgorithm s1, BinaryAlgorithm s2) {
        Double[][] price1 = s1.getS();
        Double[][] price2 = s2.getS();
        Number[][] u02 = s2.getU0();
        for (int j = s2.getTIME_STEPS() - 1; j >= 0; j--) {
            System.out.print("time step = " + (j + 1) + new String(new char[price2.length - j]).replace("\0", "  "));
            for (int i = 0; i < j; i++) {
//                if (price2[i][j] >= price1[0][j])
//                    continue;
                //System.out.print(" " + BinaryAlgorithm.getBigDecimalFrom(price2[i][j]) + " |");
                if (u02[i][j] != u02[i+1][j])
                    System.out.print("index = " + i + " space = " + price2[i][j]);
                //System.out.print(" " + u02[i][j] + " |");
            }
            System.out.println();
        }
    }
}
