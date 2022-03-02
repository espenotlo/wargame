package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;

public class InfantryUnit extends Unit {

    public InfantryUnit (String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 8, battlefield, location);
        setAttackBonus(3);
        setResistBonus(3);
    }
}
