package selection;

import java.util.ArrayList;
import base.Knapsack;

public interface ISelection {
    ArrayList<Knapsack> doSelection(ArrayList<Knapsack> knapsacks);
}