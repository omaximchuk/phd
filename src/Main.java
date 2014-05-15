public class Main {


    public static void main(String[] args) {
        Algorithm r = new BinaryAlgorithmRectangle();
        r.printU0();

        System.out.println("\n\n\n");
        r.printPrice();

//        Algorithm t = new BinaryAlgorithm();
//        t.printU0();
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
