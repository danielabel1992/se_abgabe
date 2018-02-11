import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

	private Application app;

	@BeforeEach
	void setUp() {
		app = new Application();
	}

	@Test
	void loadRecords() {
		assertNotNull(app.getRecords());
		assertEquals(app.getRecords().size(), 1000000);
	}

	@Test
	void executeSQL01() {
		assertEquals(app.executeSQL01(), 1000000);
	}

	@Test
	void executeSQL02() {
		assertEquals(app.executeSQL02(), 5213);
	}

	@Test
	void executeSQL03() {
		assertEquals(app.executeSQL03(), 10345);
	}

	@Test
	void executeSQL04() {
		assertEquals(app.executeSQL04(), 52046);
	}

	//@Test
	//void executeSQL05() {
		// Limit nicht eindeutig zu viele Werte
	//}

	@Test
	void executeSQL06() {
		int[]check = {114, 484, 423, 74, 465, 404};
		int[]get = app.executeSQL06();
		boolean ok = true;
		for (int i = 0; i < check.length; i++){
			if(check[i] != get[i]){
				ok = false;
				break;
			}
		}
		assertTrue(ok);
	}

	@Test
	void executeSQL07() {
		Map getted = app.executeSQL07();
		Map checker = new HashMap<String, Long>();
		checker.put("critical", 332807L);
		checker.put("major", 333762L);
		checker.put("minor", 333431L);
		assertEquals(checker, getted);
	}

	@Test
	void executeSQL08() {
		Map getted = app.executeSQL08();
		Map checker = new HashMap<Integer, Long>();
		checker.put(3, 10625L);
		checker.put(1, 10390L);
		checker.put(2, 10609L);
		checker.put(4, 10464L);

		assertEquals(checker, getted);

	}

	@Test
	void executeSQL09() {
		Map getted = app.executeSQL09();
		Map checker = new HashMap<String, Long>();
		checker.put("a", 31295L);
		checker.put("c", 31061L);
		checker.put("b", 31319L);

		assertEquals(checker, getted);
	}

	@Test
	void executeSQL10() {
		Map getted = app.executeSQL10();
		Map checker = new HashMap<Integer, Long>();
		checker.put(1, 30322L);
		checker.put(2, 30433L);
		checker.put(4, 30458L);
		checker.put(3, 30306L);
		assertEquals(checker, getted);
	}

	@Test
	void executeSQL11() {
		Map getted = app.executeSQL11();
		Map checker = new HashMap<String, Long>();
		checker.put("h", 1898305L);
		checker.put("c", 1928173L);
		checker.put("f", 1903616L);
		checker.put("a", 1918444L);
		checker.put("g", 1914268L);
		assertEquals(checker, getted);
	}

	@Test
	void executeSQL12() {
		Map getted = app.executeSQL12();
		Map checker = new HashMap<String, Integer>();
		checker.put("c", 122);
		checker.put("b", 123);
		checker.put("a", 122);


		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Object s : getted.keySet()) {
			Double dva = (Double) getted.get(s);
			result.put((String) s,dva.intValue());
		}

		assertEquals(checker, result);
	}
}