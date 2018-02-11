public interface ICorrelation {
	String getVersion();
	double doCorrelation(double[] xArray, double[] yArray);
}