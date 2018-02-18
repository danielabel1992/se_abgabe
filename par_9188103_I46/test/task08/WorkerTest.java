package task08;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

import static org.junit.Assert.*;

public class WorkerTest {

    private Worker worker;
    private ArrayList<Integer> primeNumbers;

    @Before
    public void init() {
        CyclicBarrier c = new CyclicBarrier(Runtime.getRuntime().availableProcessors());
        Application app = new Application();
        primeNumbers = app.getPrimesThreaded(Configuration.instance.maxGeneratedPrimeNumbers);
        this.worker = new Worker(c, 2, 12500000, 1, primeNumbers);
    }

    @Test
    public void getEdwinClarkPuzzleBigInt() {
        assertNotNull(worker.getEdwinClarkPuzzleBigInt("57"));
    }

    @Test
    public void getEdwinClarkPuzzle() {
        assertNotNull(worker.getEdwinClarkPuzzle("57"));
    }

    @Test
    public void isPrimeBigInt() {
        assertNotNull(worker.isPrimeBigInt(new BigInteger("5")));
    }

    @Test
    public void isPrime() {
        assertNotNull(worker.isPrime(5));
    }

    @Test
    public void concatPrimesN2() {
        assertNotNull(worker.concatPrimes(1,2));
    }

    @Test
    public void concatPrimesN3() {
        assertNotNull(worker.concatPrimes(1,2, 3));
    }

    @Test
    public void concatPrimesN4() {
        assertNotNull(worker.concatPrimes(1,2, 3, 4));
    }
}