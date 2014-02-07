public class Main {

    public static void main(String[] args) {
        Algorithm algorithm = new Algorithm();
        Double time_interval = algorithm.getTIME_INTERVAL();
        SimpleAlgorithm simpleAlgorithm = new SimpleAlgorithm(algorithm, .2*time_interval, .8*time_interval);

        showInitialConditions(algorithm);

        simpleAlgorithm.printControlArray(simpleAlgorithm.getU0());
        System.out.println();
        simpleAlgorithm.printControlArray(simpleAlgorithm.getU1());
    }

    private static void showInitialConditions(Algorithm algorithm) {
        System.out.println("time interval = " + algorithm.getTIME_INTERVAL());
        System.out.println("delta t = " + algorithm.getDeltaT());
        System.out.println("# time steps = " + algorithm.getTIME_STEPS());
        System.out.println("space interval = " + algorithm.getSPACE_INTERVAL());
        System.out.println("delta s = " + algorithm.getDeltaS());
        System.out.println("# space steps = " + algorithm.getSPACE_STEPS());
    }
}
