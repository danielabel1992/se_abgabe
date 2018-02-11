package selection;

import java.util.ArrayList;

import base.Knapsack;
import main.Configuration;

public class RouletteWheelSelection implements ISelection {
    public ArrayList<Knapsack> doSelection(ArrayList<Knapsack> knapsacks) {

        ArrayList<Knapsack> selectedKnaps = new ArrayList<>();
        ArrayList<Knapsack> allValids = new ArrayList<>(knapsacks);
        allValids.removeIf(k -> k.getSize() >  Configuration.instance.maxKnapsackVolume);
        if(allValids.size() <= Configuration.instance.maxRetSelectionIndis) return allValids;

        int max = 0;
        for (Knapsack k : allValids) {
            max += k.getTotal();
        }

        double lastKey = 0;
        ArrayList<Double> keys = new ArrayList<Double>();
        for(Knapsack k: allValids){
            double key = k.getTotal() / (double)max;

            double rKey = Math.round(key*1000.0)/1000.0;
            double keyToSave = rKey + lastKey;
            //System.out.println("Key: "+key +" rKey: "+rKey + " KeyToSave: "+keyToSave);

            lastKey = keyToSave;
            keys.add(keyToSave);
        }
        keys.set(keys.size() - 1, 1.0);

        while(selectedKnaps.size() < Configuration.instance.maxRetSelectionIndis){

            double d = Configuration.instance.randomNumberGenerator.nextDouble();
            Knapsack k = null;
            //System.out.println(d);
            for(int i = 0; i < keys.size(); i++){
                if(d <= keys.get(i)){
                    //System.out.println("Key"+ keys.get(i));
                    k = allValids.get(i);
                    break;
                }
            }
            if(k != null){
                if(!selectedKnaps.contains(k)){
                    selectedKnaps.add(k);
                }
            }

        }

        return selectedKnaps;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
