package norseninja.wargame.model.unit;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;

public class InfantryUnit extends Unit {

    public InfantryUnit (String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 8, battlefield, location);
        setAttackBonus(3);
        setResistBonus(3);
    }
}
