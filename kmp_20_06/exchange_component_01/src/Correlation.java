import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class Correlation {
    private static Correlation instance = new Correlation();
    public Port port;

    private Correlation() {
        port = new Port();
    }

    public static Correlation getInstance() {
        return instance;
    }

    public class Port implements ICorrelation {
        private Method[] methods = getClass().getMethods();

        public double doCorrelation(double[] xArray, double[] yArray) {
            double corr = spearman(xArray, yArray);
            return corr;
        }

        public String getVersion() {
            return "V 1.0";
        }
    }


    /**
    https://www.hackerrank.com/challenges/s10-spearman-rank-correlation-coefficient/forum/comments/250259
     */

    /* Calculates Spearman's rank correlation coefficient, */
    public static Double spearman(double[] X, double[] Y) {
        /* Error check */
        if (X == null || Y == null || X.length != Y.length) {
            return null;
        }

        /* Create Rank arrays */
        int[] rankX = getRanks(X);
        int[] rankY = getRanks(Y);

        /* Apply Spearman's formula */
        int n = X.length;
        double numerator = 0;
        for (int i = 0; i < n; i++) {
            numerator += Math.pow((rankX[i] - rankY[i]), 2);
        }
        numerator *= 6;
        return 1 - numerator / (n * ((n * n) - 1));
    }

    /* Returns a new (parallel) array of ranks. Assumes unique array values */
    public static int[] getRanks(double[] array) {
        int n = array.length;

        /* Create Pair[] and sort by values */
        Pair[] pair = new Pair[n];
        for (int i = 0; i < n; i++) {
            pair[i] = new Pair(i, array[i]);
        }
        Arrays.sort(pair, new PairValueComparator());

        /* Create and return ranks[] */
        int[] ranks = new int[n];
        int rank = 1;
        for (Pair p : pair) {
            ranks[p.index] = rank++;
        }
        return ranks;
    }
}

/* A class to store 2 variables */
class Pair {
    public final int index;
    public final double value;

    public Pair(int i, double v) {
        index = i;
        value = v;
    }
}

/* This lets us sort Pairs based on their value field */
class PairValueComparator implements Comparator<Pair> {
    double epsilon = 0.0001; // shouldn't use " == " to compare "doubles"

    @Override
    public int compare(Pair p1, Pair p2) {
        if (Math.abs(p1.value - p2.value) < epsilon) {
            return 0;
        } else if (p1.value < p2.value) {
            return -1;
        } else {
            return 1;
        }
    }

}