import org.junit.*;
import static org.junit.Assert.*;

public class TestSpearman {

    private Correlation correlation;
    private double[] x;
    private double[] y;

    @Before
    public void init() {
        this.correlation = Correlation.getInstance();
        x = new double[]{59, 35, 43, 23, 42, 27};
        y = new double[]{14.61,11.80,14.34,13.03,14.18,11.02};
    }

    @Test
    public void testSpearmanAlgorithm(){

        // Beispiel übernommen von
        // http://www.crashkurs-statistik.de/spearman-korrelation-rangkorrelation/

        double r = this.correlation.spearman(x,y);
        r = r * 100;
        r = Math.round(r);
        r = r / 100;
        assertTrue(r == 0.83);
    }

}
