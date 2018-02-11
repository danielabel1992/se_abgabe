import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Properties;

public enum Configuration {
    instance;

    public static String userDirectory = System.getProperty("user.dir");
    public static String fileSeparator = System.getProperty("file.separator");

    public String getPathToJar() {
        return userDirectory + fileSeparator + getCorrelationTyp() + fileSeparator + "jar" + fileSeparator + "component.jar";
    }

    public static Properties getProperty() {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "correlation.props");
            properties.load(fileInputStream);
            fileInputStream.close();
            return properties;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setProperty(String argS) {

        try {
            Properties props = new Properties();
            props.setProperty("correlationType", argS);
            File f = new File(userDirectory + fileSeparator + "correlation.props");
            OutputStream out = new FileOutputStream( f );
            props.store(out, "");
            out.close();
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public static String getCorrelationTyp() {
        try {
            Properties properties = getProperty();
            if (properties.getProperty("correlationType").equals("spearman"))
                //return ""+ CorrelationType.spearman;
                return "exchange_component_01";
            else if (properties.getProperty("correlationType").equals("pearson"))
                //return ""+ CorrelationType.pearson;
                return "exchange_component_02";
            else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
