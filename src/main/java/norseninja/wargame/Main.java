package norseninja.wargame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = Randomizer.getRandom();
        Battlefield battlefield = new Battlefield(30,30);

        Army armyOne = new Army("one", r, battlefield, new Location(0,0));
        Army armyTwo = new Army("two", r, battlefield, new Location(29,29));
        Army armyThree = new Army("three", r, battlefield, new Location(29,0));
        Army armyFour = new Army("four", r, battlefield, new Location(0,29));

        List<Army> armies = new ArrayList<>();
        armies.add(armyOne);
        armies.add(armyTwo);
        armies.add(armyThree);
        armies.add(armyFour);

        armies.forEach(army -> {
            army.muster(10);
            printArmy(army);
        });

        BattleSimulator bs = new BattleSimulator(armies);
        Army winner = bs.simulate();
        System.out.println("Army " + winner.getName() + " won after " + bs.getRound() + " rounds.");
        System.out.println(battlefield.getAsString());
    }

    private static void printArmy(Army army) {
        System.out.println("Army " + army.getName() + ":");
        army.getRoster().forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println();
    }
}
