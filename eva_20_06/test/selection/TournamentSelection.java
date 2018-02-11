package test.selection;

import base.Item;
import base.Knapsack;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TournamentSelection {
    private selection.TournamentSelection tournamentSelection;
    private ArrayList<Knapsack> testValues;

    @Before
    public void init() {
        tournamentSelection = new selection.TournamentSelection();
        testValues = data.TestData.getTestData();
    }


    @Test
    public void doSelectionCheckResultNotNull() {
        ArrayList<Knapsack> result = tournamentSelection.doSelection(testValues);
        assertNotNull(result);
    }
    @Test
    public void doSelectionCheckLength() {
        ArrayList<Knapsack> result = tournamentSelection.doSelection(testValues);
        assertEquals((long)Configuration.instance.maxRetSelectionIndis, result.size());
    }

    @Test
    public void doSelectionCheckForDuplicates() {
        ArrayList<Knapsack> result = tournamentSelection.doSelection(testValues);
        HashSet<Knapsack> tempResult = new HashSet<>(result);
        assertEquals((long)Configuration.instance.maxRetSelectionIndis,tempResult.size());
    }

    @Test
    public void doSelectionCheckForValidItems() {
        ArrayList<Knapsack> result = tournamentSelection.doSelection(testValues);
        boolean tmpValue = true;
        for (Knapsack k : result) {
            if (k.getSize() > Configuration.instance.maxKnapsackVolume) {
                System.out.println(k.getSize());

                tmpValue = false;
                break;
            }
        }
        assertTrue(tmpValue);
    }
}