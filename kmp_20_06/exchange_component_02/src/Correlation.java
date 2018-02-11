import java.lang.reflect.Method;

public class Correlation {

	private static Correlation instance = new Correlation();
	public Port port;

	private Correlation(){
		port = new Port();
	}

	public double innerCorrelation(double[] scores1, double[] scores2){

		// https://dzone.com/articles/calculate-pearsons-correlation
		double result = 0;
		double sum_sq_x = 0;
		double sum_sq_y = 0;
		double sum_coproduct = 0;
		double mean_x = scores1[0];
		double mean_y = scores2[0];
		for(int i=2;i<scores1.length+1;i+=1){
			double sweep =Double.valueOf(i-1)/i;
			double delta_x = scores1[i-1]-mean_x;
			double delta_y = scores2[i-1]-mean_y;
			sum_sq_x += delta_x * delta_x * sweep;
			sum_sq_y += delta_y * delta_y * sweep;
			sum_coproduct += delta_x * delta_y * sweep;
			mean_x += delta_x / i;
			mean_y += delta_y / i;
		}
		double pop_sd_x = (double) Math.sqrt(sum_sq_x/scores1.length);
		double pop_sd_y = (double) Math.sqrt(sum_sq_y/scores1.length);
		double cov_x_y = sum_coproduct / scores1.length;
		result = cov_x_y / (pop_sd_x*pop_sd_y);
		return result;
	}
	public String toString(){
		return "PearsonsCorrelation - Version 1.0";
	}

	public static Correlation getInstance() {
		return instance;
	}
	public class Port implements ICorrelation {
		private Method[] methods = getClass().getMethods();

		public double doCorrelation(double[] xArray, double[] yArray){
			return innerCorrelation(xArray, yArray);
		}
		public String getVersion(){
			return Correlation.this.toString();
		}

		public void listMethods() {
			System.out.println("--- public methods for " + getClass().getName());
			for (int i = 0; i < methods.length; i++)
			if (!methods[i].toString().contains("Object") && !methods[i].toString().contains("list"))
				System.out.println(methods[i]);
				System.out.println("---");
		}
	}
}
