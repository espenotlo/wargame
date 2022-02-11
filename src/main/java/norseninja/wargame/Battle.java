package norseninja.wargame;

import java.time.LocalTime;
import java.util.Random;

public class Battle {
    private final Army armyOne;
    private final Army armyTwo;
    Random r;

    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        r = new Random(LocalTime.now().getNano());
    }

    public Army simulate() {
        int round = 0;
        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            round++;
            fight();
        }

        if (armyOne.hasUnits()) {
            System.out.println("army one wins after " + round + " rounds");
            return armyOne;
        } else {
            System.out.println("army two wins after " + round + " rounds");
            return armyTwo;
        }
    }

    private void fight() {
        Army first = null;
        Army second = null;
        if (r.nextInt(2) == 0) {
            first = armyOne;
            second = armyTwo;
        } else {
            first = armyTwo;
            second = armyOne;
        }
        attack(armyOne, armyTwo);
        if (armyTwo.hasUnits()) {
            attack(armyTwo, armyOne);
        }
    }

    private void attack(Army attacker, Army defender) {
        Unit defendingUnit = defender.getRandom();
        if (attacker.getRandom().attack(defendingUnit) == 0) {
            defender.removeUnit(defendingUnit);
        }
    }
}
