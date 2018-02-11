import org.junit.*;
import static org.junit.Assert.*;

public class TestPearsonCorrelation {

    private Correlation correlation;
    private double[] x;
    private double[] y;

    @Before
    public void init() {
        this.correlation = Correlation.getInstance();
        x = new double[]{4, 21, 2, 11, 14, 2, 6};
        y = new double[]{70,63,82,65,61,74,84};
    }

    @Test
    public void testPearsonAlgorithm(){

        // Beispiel übernommen von
        // http://www.crashkurs-statistik.de/der-korrelationskoeffizient-nach-pearson/

        double r = this.correlation.innerCorrelation(x,y);
        r = r * 100;
        r = Math.round(r);
        r = r / 100;
        System.out.println(r);
        assertTrue(r == -0.74);
    }

}
