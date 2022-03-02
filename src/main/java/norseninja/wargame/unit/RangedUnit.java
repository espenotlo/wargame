package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;

public class RangedUnit extends Unit {

    public RangedUnit (String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 5, battlefield, location);
        super.setAttackBonus(6);
        setAttackRange(5);
    }
}
