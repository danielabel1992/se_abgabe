package selection;

import java.util.ArrayList;
import base.Knapsack;
import main.Configuration;
import random.MersenneTwisterFast;

public class TournamentSelection implements ISelection {
    public ArrayList<Knapsack> doSelection(ArrayList<Knapsack> knapsacks) {
        ArrayList<Knapsack> allValids = new ArrayList<Knapsack>(knapsacks);
        allValids.removeIf(k -> k.getSize() >  Configuration.instance.maxKnapsackVolume);

        if (allValids.size()<=Configuration.instance.maxRetSelectionIndis) {
            return allValids;
        }
        int retSize =  Configuration.instance.maxRetSelectionIndis;
        MersenneTwisterFast m = Configuration.instance.randomNumberGenerator;
        ArrayList<Knapsack> winners = new ArrayList<>();
        int posRetValues = 0;
        while (posRetValues < retSize) {
            // Tunierteilnehmer zum Tunier hinzufügen
            ArrayList<Knapsack> tournament = new ArrayList<>();
            int posTournament = 0;
            int maxTournamentPlayers = Configuration.instance.maxTournamentSize;
            if (allValids.size() <= maxTournamentPlayers) {
                maxTournamentPlayers = allValids.size();
            }
            while (posTournament < maxTournamentPlayers) {
                int index = m.nextInt(allValids.size());
                tournament.add(allValids.get(index));
                posTournament++;
            }
            // Let's play the Tournament
            Knapsack winner = tournament.get(0);
            for (posTournament = 1; posTournament < tournament.size(); posTournament++) {
                if (winner.getTotal() < tournament.get(posTournament).getTotal()) {
                    winner = tournament.get(posTournament);
                }
            }
            winners.add(winner);
            allValids.remove(winner);
            posRetValues ++;
        }
        return winners;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}