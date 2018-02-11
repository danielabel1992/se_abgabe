package test.selection;

import base.*;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

public class RouletteWheelSelection {
	private selection.RouletteWheelSelection routeWheelSelection;
	private ArrayList<Knapsack> testData;

	@Before
	public void init(){
		routeWheelSelection = new selection.RouletteWheelSelection();
		testData = data.TestData.getTestData();
	}

	@Test
	public void doSelectionNotNull(){
		ArrayList<Knapsack> result = routeWheelSelection.doSelection(testData);
		assertNotNull(result);
	}
	@Test
	public void doSelectionLength(){
		ArrayList<Knapsack> result = routeWheelSelection.doSelection(testData);
		assertEquals((long)Configuration.instance.maxRetSelectionIndis, result.size());
	}
	@Test
	public void doSelectionValid(){
		ArrayList<Knapsack> result = routeWheelSelection.doSelection(testData);
		Boolean isValid = true;
		for (Knapsack k : result){
			if(k.getSize() > Configuration.instance.maxKnapsackVolume){
				isValid = false;
				break;
			}
		}
		assertTrue(isValid);
	}
	@Test
	public void doSelectionCheckForDuplicates(){
		ArrayList<Knapsack> result = routeWheelSelection.doSelection(testData);
		HashSet<Knapsack> set = new HashSet<Knapsack>(result);
		assertEquals(result.size(), set.size());
	}

}