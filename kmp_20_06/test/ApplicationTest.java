import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

	@Test
	void componentPearsonNoNull() {
		Object instance;
		Configuration.setProperty("pearson");
		try {
			System.out.println("pathToJar : " + Configuration.instance.getPathToJar());
			URL[] urls = {new File(Configuration.instance.getPathToJar()).toURI().toURL()};
			URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
			Class clazz = Class.forName("Correlation", true, urlClassLoader);
			System.out.println("clazz     : " + clazz.toString());

			instance = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
			Object port = clazz.getDeclaredField("port").get(instance);
			System.out.println("port      : " + port.hashCode());

			Method getVersion = port.getClass().getMethod("getVersion");

			String version = (String) getVersion.invoke(port);
			System.out.println("version   : " + version);

			Method doCorrel = port.getClass().getMethod("doCorrelation");

			assertNotNull(port);
			assertNotNull(getVersion);
			assertNotNull(doCorrel);

		} catch (Exception e) {
			System.out.println("--- exception");
			System.out.println(e.getMessage());

		}
	}
	@Test
	void componentSpearmonNotNull() {
		Object instance;
		Configuration.setProperty("spearman");
		try {
			System.out.println("pathToJar : " + Configuration.instance.getPathToJar());
			URL[] urls = {new File(Configuration.instance.getPathToJar()).toURI().toURL()};
			URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
			Class clazz = Class.forName("Correlation", true, urlClassLoader);
			System.out.println("clazz     : " + clazz.toString());

			instance = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
			Object port = clazz.getDeclaredField("port").get(instance);
			System.out.println("port      : " + port.hashCode());

			Method getVersion = port.getClass().getMethod("getVersion");

			String version = (String) getVersion.invoke(port);
			System.out.println("version   : " + version);

			Method doCorrel = port.getClass().getMethod("doCorrelation");

			assertNotNull(port);
			assertNotNull(getVersion);
			assertNotNull(doCorrel);

		} catch (Exception e) {
			System.out.println("--- exception");
			System.out.println(e.getMessage());

		}
	}
}