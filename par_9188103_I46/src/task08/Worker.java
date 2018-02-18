package task08;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable{

	private CyclicBarrier cyclicBarrier;
	private int index;

	private int minimum;
	private int maximum;

	private Random randomGenerator;
	private ArrayList<Integer> primeNumbers;



	public Worker(CyclicBarrier cyclicBarrier, int minimum, int maximum, int index, ArrayList<Integer> primeNumbers) {
		this.cyclicBarrier = cyclicBarrier;
		this.minimum = minimum;
		this.maximum = maximum;
		this.primeNumbers = primeNumbers;
		this.randomGenerator = new Random();
		this.index = index;
		System.out.println("T"+index+" init { "+ minimum + " - " + maximum + " }");
	}

	private void doStuff(){

		int n = 2;
		for (int actItem = minimum; actItem < maximum; actItem++) {
			int randomItemA = this.getRandomItemOfArrayList();
			int randomItemB = this.getRandomItemOfArrayList();
			int randomItemC = 0;
			int randomItemD = 0;
			if (n==3 || n == 4) {
				randomItemC = this.getRandomItemOfArrayList();
			}
			if (n==4) {
				randomItemD = this.getRandomItemOfArrayList();
			}

			String numberString;

			if (n==2) {
				numberString = concatPrimes(randomItemA, randomItemB);
			} else if (n==3) {
				numberString = concatPrimes(randomItemA, randomItemB, randomItemC);
			} else {
				numberString = concatPrimes(randomItemA, randomItemB, randomItemC, randomItemD);
			}
			ArrayList<BigInteger> concatedPrimesBigInt = null;
			ArrayList<Long> concatedPrimes = null;
			if (Configuration.instance.useBigInt==true) {
				concatedPrimesBigInt = this.getEdwinClarkPuzzleBigInt(numberString);
			} else {
				concatedPrimes = this.getEdwinClarkPuzzle(numberString);
			}


			StringBuilder sb = new StringBuilder("");
			sb.append("n = ").append(n);
			sb.append("[").append(randomItemA).append(",").append(randomItemB);
			if (n==3) sb.append(",").append(randomItemC);
			if (n==4) sb.append(",").append(randomItemD);
			sb.append("]: ");
			if (Configuration.instance.useBigInt==true) {
				sb.append(concatedPrimesBigInt.toString());
			} else {
				sb.append(concatedPrimes.toString());
			}
			System.out.println(sb.toString());

			n = (n==4) ? 2 : n+1;
		}
		System.out.println("Exit: " + minimum + "-" + maximum);
	}

	public ArrayList<BigInteger> getEdwinClarkPuzzleBigInt(String stringNumber){
		ArrayList<BigInteger> finalResult = new ArrayList<>();
		try{

			String[] stringNumbersArray = stringNumber.split("\\|");
			BigInteger[] numbers = new BigInteger[stringNumbersArray.length];
			for (int i = 0; i < stringNumbersArray.length; i++) {
				numbers[i] = new BigInteger(stringNumbersArray[i]);
			}

			for (long i = 0; i < numbers.length; i ++) {
				if (isPrimeBigInt(numbers[(int) i])) {
					finalResult.add(numbers[(int) i]);
				}
			}

			return finalResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
	}

	public ArrayList<Long> getEdwinClarkPuzzle(String stringNumber){
		ArrayList<Long> finalResult = new ArrayList<>();
		try{

			String[] stringNumbersArray = stringNumber.split("\\|");
			long[] numbers = new long[stringNumbersArray.length];
			for (int i = 0; i < stringNumbersArray.length; i++) {
				numbers[i] = Long.parseLong(stringNumbersArray[i]);
			}

			for (long i = 0; i < numbers.length; i ++) {
				if (isPrime(numbers[(int) i])) {
					finalResult.add(numbers[(int) i]);
				}
			}

			return finalResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
	}

	private int getRandomItemOfArrayList() {
		return primeNumbers.get(randomGenerator.nextInt(primeNumbers.size()));
	}

	public boolean isPrimeBigInt(BigInteger number) {
		//check via BigInteger.isProbablePrime(certainty)
		if (!number.isProbablePrime(5))
			return false;

		//check if even
		BigInteger two = new BigInteger("2");
		if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
			return false;

		//find divisor if any from 3 to 'number'
		for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
			if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
				return false;
		}
		return true;
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

	public String concatPrimes(int a, int b) {
		StringBuilder sb = new StringBuilder("");
		sb
				.append(a).append(b).append(Configuration.instance.splitter)
				.append(b).append(a);
		return sb.toString();
	}


	public String concatPrimes(int a, int b, int c) {

		StringBuilder sb = new StringBuilder("");
		sb
				.append(a).append(b).append(c).append(Configuration.instance.splitter)
				.append(a).append(c).append(c).append(Configuration.instance.splitter)
				.append(b).append(a).append(c).append(Configuration.instance.splitter)
				.append(b).append(c).append(a).append(Configuration.instance.splitter)
				.append(c).append(a).append(b).append(Configuration.instance.splitter)
				.append(c).append(b).append(a);
		return sb.toString();
	}

	public String concatPrimes(int a, int b, int c, int d) {
		StringBuilder sb = new StringBuilder("");
		sb
				.append(a).append(b).append(c).append(d).append(Configuration.instance.splitter)
				.append(a).append(b).append(d).append(c).append(Configuration.instance.splitter)
				.append(a).append(c).append(b).append(d).append(Configuration.instance.splitter)
				.append(a).append(c).append(d).append(b).append(Configuration.instance.splitter)
				.append(a).append(d).append(b).append(c).append(Configuration.instance.splitter)
				.append(a).append(d).append(c).append(b).append(Configuration.instance.splitter)
				.append(b).append(a).append(c).append(d).append(Configuration.instance.splitter)
				.append(b).append(a).append(d).append(c).append(Configuration.instance.splitter)
				.append(b).append(c).append(a).append(d).append(Configuration.instance.splitter)
				.append(b).append(c).append(d).append(a).append(Configuration.instance.splitter)
				.append(b).append(d).append(a).append(c).append(Configuration.instance.splitter)
				.append(b).append(d).append(c).append(a).append(Configuration.instance.splitter)
				.append(c).append(a).append(b).append(d).append(Configuration.instance.splitter)
				.append(c).append(a).append(d).append(b).append(Configuration.instance.splitter)
				.append(c).append(b).append(a).append(d).append(Configuration.instance.splitter)
				.append(c).append(b).append(d).append(a).append(Configuration.instance.splitter)
				.append(c).append(d).append(a).append(b).append(Configuration.instance.splitter)
				.append(c).append(d).append(b).append(a).append(Configuration.instance.splitter)
				.append(d).append(a).append(b).append(c).append(Configuration.instance.splitter)
				.append(d).append(a).append(c).append(b).append(Configuration.instance.splitter)
				.append(d).append(b).append(a).append(c).append(Configuration.instance.splitter)
				.append(d).append(b).append(c).append(a).append(Configuration.instance.splitter)
				.append(d).append(c).append(a).append(b).append(Configuration.instance.splitter)
				.append(d).append(c).append(b).append(a).append(Configuration.instance.splitter);
		return sb.toString();
	}

	public void run() {

		long runtimeStartT = System.currentTimeMillis();
		doStuff();
		long eT = System.currentTimeMillis() - runtimeStartT;
		double minutes = (double) eT / 1000 / 60;
		System.out.println("T"+ this.index + " finished: " + this.primeNumbers.size() + " in "+ eT  + " ms (ca. "+ minutes +" m)");

		try{
			cyclicBarrier.await();
		} catch (InterruptedException iex) {
			System.out.println(iex.getMessage());
		} catch (BrokenBarrierException bbex) {
			System.out.println(bbex.getMessage());
		}

	}
}
