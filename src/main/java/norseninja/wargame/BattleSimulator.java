package norseninja.wargame;

import norseninja.wargame.tempeffect.TempEffect;
import norseninja.wargame.unit.Unit;

import java.time.LocalTime;
import java.util.*;

public class BattleSimulator {
    private final Army armyOne;
    private final Army armyTwo;
    private final List<Unit> combatants;
    private int round;
    Random r;

    public BattleSimulator(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.combatants = new ArrayList<>();
        r = new Random(LocalTime.now().getNano());
    }

    public Army simulate() {
        round = 0;
        combatants.addAll(armyOne.getUnits());
        combatants.addAll(armyTwo.getUnits());
        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            round++;
            System.out.println("-------------------------Round " + round + "-------------------------");
            System.out.println(armyOne.getField().getAsString());

            combatants.forEach(Unit::rollInitiative);
            combatants.sort(Comparator.comparing(Unit::getInitiative));
            fight();
        }

        if (armyOne.hasUnits()) {
            return armyOne;
        } else {
            return armyTwo;
        }
    }

    public int getRound() {
        return this.round;
    }

    private void fight() {
        combatants.forEach(attacker -> {
            if (attacker.getHealth() > 0) {
                attacker.act();
            }
        });
    }
}
