package task08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Application {

    private CyclicBarrier cyclicBarrier;
    public final static int CORES = Runtime.getRuntime().availableProcessors();
    private ArrayList<Integer> primeNumbers;

    public static void main(String[] args) {
        long runtimeStart = System.currentTimeMillis();
        Application app = new Application();
        app.primeNumbers = app.getPrimesThreaded(Configuration.instance.maxGeneratedPrimeNumbers);
        app.execute();
        long e = System.currentTimeMillis() - runtimeStart;
        double minutes = (double) e / 1000 / 60;
        System.out.println( "Runtime: "+ e +" ms (" + minutes+" minutes)");
    }


    public Application(){
        cyclicBarrier = new CyclicBarrier(Application.CORES);
    }

    public void execute(){

        //int min = Configuration.instance.minimumI;
        int max = Configuration.instance.maximumI;
        int range = max / Application.CORES;

        int lastMax = 0;

        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < Application.CORES; i++){
            int newMax = lastMax + range;
            Worker w = new Worker(cyclicBarrier, lastMax, newMax, i, primeNumbers);
            Thread t = new Thread(w);
            threads.add(t);
            lastMax = newMax;
        }

        for(Thread t :threads){
            t.start();
        }

        System.out.println("Threads started");

        try{
            for(Thread t :threads){
                t.join();
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }

    }
    public boolean isPrime(long n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)Math.sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }

    public ArrayList<Integer> calcPrimes(int min, int max) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = min; i <= max ; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public ArrayList<Integer> getPrimesThreaded(int max){
        ArrayList<Integer> finalResult = new ArrayList<>();
        try{
            final List<Callable<ArrayList<Integer>>> parts = new ArrayList<>();
            final ExecutorService pool = Executors.newFixedThreadPool(CORES);
            int partSize = max / CORES;

            for (int i=2;i<=max;i+=partSize){
                final int from = i;
                int to = i+partSize;
                if(to>max) to = max;
                final int end = to;
                parts.add(() -> calcPrimes(from, end));
            }

            final List<Future<ArrayList<Integer>>> resultFromParts = pool.invokeAll(parts,10000, TimeUnit.SECONDS);
            pool.shutdown();

            for(final Future<ArrayList<Integer>> result : resultFromParts){
                finalResult.addAll(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalResult;
    }
}
