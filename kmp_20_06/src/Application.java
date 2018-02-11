import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Application {

    private Object port;

    @SuppressWarnings({"rawtypes", "unchecked"})

    public static void main(String... args) {
        String line;
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        showHelp();
        while (exit == false) {
            System.out.print(">");
            line = sc.nextLine();
            if (line.equals("exit")) {
                exit = true;
            } else if (line.equals("show components")) {
                System.out.println(Arrays.asList(CorrelationType.values()));
            } else if (line.equals("show current component")) {
                System.out.println(Configuration.getProperty().getProperty("correlationType"));
            } else if (line.startsWith("execute")) {
                handleExecute(line);
            } else if (line.equals("help")) {
                showHelp();
            } else if (line.startsWith("set current component")) {
                handleSetCurrentComponent(line);
            } else {
                System.out.println("Befehl nicht gefunden!");
            }
        }
    }

    public static void handleSetCurrentComponent(String line) {

        if (line.equals("set current component")) {
            System.out.println("Sie haben keine neue Komponente angegeben.");
            return;
        }

        boolean isValidEnum = false;
        line = line.replace("set current component", "");
        line = line.replace(" ", "");
        for (CorrelationType c : CorrelationType.values()) {
            if (c.toString().equals(line)) {
                isValidEnum = true;
                Configuration.setProperty(line);
            }
        }
        if (isValidEnum == false) {
            System.out.println("Sie haben eine falsche Komponente angegeben.");
        }

    }

    public void createCorrelationPortInstance() {
        Object instance;
        try {
            //System.out.println("pathToJar : " + Configuration.instance.getPathToJar());
            URL[] urls = {new File(Configuration.instance.getPathToJar()).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            Class clazz = Class.forName("Correlation", true, urlClassLoader);
            //System.out.println("clazz     : " + clazz.toString());

            instance = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            port = clazz.getDeclaredField("port").get(instance);
            //System.out.println("port      : " + port.hashCode());

            Method getVersion = port.getClass().getMethod("getVersion");
            String version = (String) getVersion.invoke(port);
            //System.out.println("version   : " + version);

        } catch (Exception e) {
            System.out.println("--- exception");
            System.out.println(e.getMessage());

        }
    }

    public double execute(double[] xArray, double[] yArray, String operation) {
        double result = 0;
        try {
            Method method = port.getClass().getMethod(operation, xArray.getClass(), yArray.getClass());
            //Method method = port.getClass().getMethod(operation,    int.class,int.class);
            result = (double) method.invoke(port, xArray, yArray);
        } catch (Exception e) {
            System.out.println("operation " + operation + " not supported.");
        }
        return result;
    }

    private double[] getArrayFromList(ArrayList<Double> doubles) {
        double[] target = new double[doubles.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = doubles.get(i);                // java 1.5+ style (outboxing)
        }
        return target;
    }

    public static void handleExecute(String line) {
        ArrayList<Double> xArray = new ArrayList<Double>();
        ArrayList<Double> yArray = new ArrayList<Double>();
        line = line.replace("execute", "");
        String[] strArr = line.split(" ");

        for (String item : strArr) {
            if (!item.equals("")) {
                String[] xy = item.split(",");
                xArray.add(Double.parseDouble(xy[0]));
                yArray.add(Double.parseDouble(xy[1]));
            }
        }
        Application application = new Application();
        application.createCorrelationPortInstance();
        System.out.println(application.execute(application.getArrayFromList(xArray), application.getArrayFromList(yArray), "doCorrelation"));
    }

    public static void showHelp() {
        System.out.println();
        System.out.println("## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##");
        System.out.println("help                                Zeigt diese Befehlsliste an.");
        System.out.println("exit                                Beendet das Progtamm.");
        System.out.println("show components                     Liefert alle Komponenten.");
        System.out.println("show current component              Liefert die aktuelle Komponente.");
        System.out.println("set current component <Komponente>  Setzt die aktuelle Komponente.");
        System.out.println("execute <X1,Y1 X2,Y2 X3,Y3 ...>     Führt die Korrelation aus.");
        System.out.println("## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##");
    }

}