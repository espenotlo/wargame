package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;

public class InfantryUnit extends Unit {

    public InfantryUnit (String name, int health, int attack, int armor, Field field, Location location) {
        super(name, health, attack, armor, field, location);
    }

    public InfantryUnit (String name, int health, Field field, Location location) {
        super(name, health, 15, 10, field, location);
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

    @Override
    public int getResistBonus() {
        return 3;
    }
}
