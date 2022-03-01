package norseninja.wargame;

import norseninja.wargame.unit.Unit;
import norseninja.wargame.unit.UnitFactory;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = Randomizer.getRandom();
        Field field = new Field(30,30);

        Army armyOne = new Army("one", r, field, new Location(0,0));
        Army armyTwo = new Army("two", r, field, new Location(29,29));

        armyOne.muster(10);
        armyTwo.muster(10);

        printArmy(armyOne);
        printArmy(armyTwo);

        BattleSimulator bs = new BattleSimulator(armyOne, armyTwo);
        Army winner = bs.simulate();
        System.out.println("Army " + winner.getName() + " won after " + bs.getRound() + " rounds.");
        System.out.println(field.getAsString());
    }

    private static void printArmy(Army army) {
        System.out.println("Army " + army.getName() + ":");
        army.getRoster().forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println();
    }
}
