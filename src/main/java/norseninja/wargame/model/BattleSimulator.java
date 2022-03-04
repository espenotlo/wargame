package norseninja.wargame.model;

import norseninja.wargame.model.tempeffect.TempEffect;
import norseninja.wargame.model.unit.Unit;

import java.time.LocalTime;
import java.util.*;

public class BattleSimulator {
    private final List<Army> armies;
    private final List<Unit> combatants;
    private int round;
    Random r;

    public BattleSimulator(List<Army> armies) {
        this.armies = armies;
        this.combatants = new ArrayList<>();
        r = new Random(LocalTime.now().getNano());
    }

    public Army simulate() {
        round = 0;
        armies.forEach(army -> combatants.addAll(army.getUnits()));

        while (armiesWithUnits() > 1) {
            round++;
            armies.get(0).getField().getTempEffects().forEach(TempEffect::tick);
            armies.get(0).getField().tickTempEffects();
            System.out.println("-------------------------Round " + round + "-------------------------");
            System.out.println(armies.get(0).getField().getAsString());

            combatants.forEach(Unit::rollInitiative);
            combatants.sort(Comparator.comparing(Unit::getInitiative));
            fight();
        }
        Army winner = null;
        for (Army army : armies) {
            if (army.hasUnits()) {
                winner = army;
            }
        }
        return winner;
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

    private int armiesWithUnits() {
        int i = 0;
        for (Army army : armies) {
            if (army.hasUnits()) {
                i++;
            }
        }
        return i;
    }
}
