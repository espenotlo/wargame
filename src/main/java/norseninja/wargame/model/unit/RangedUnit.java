package norseninja.wargame.model.unit;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;

public class RangedUnit extends Unit {

    public RangedUnit (String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 5, battlefield, location);
        super.setAttackBonus(6);
        setAttackRange(5);
    }
}
