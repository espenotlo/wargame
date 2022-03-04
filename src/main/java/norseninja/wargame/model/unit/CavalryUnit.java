package norseninja.wargame.model.unit;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;

public class CavalryUnit extends Unit {
    private boolean firstStrike = true;

    public CavalryUnit(String name, int health, int attack, int armor, Battlefield battlefield, Location location) {
        super(name, health, attack, armor, battlefield, location);
        super.setMovementSpeed(3);
    }

    public CavalryUnit (String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 20, 5, battlefield, location);
        super.setMovementSpeed(3);
    }

    @Override
    public int getAttackBonus() {
        if (firstStrike) {
            return super.getAttackBonus() + 10;
        }
        return super.getAttackBonus();
    }

    @Override
    public int attack(Unit opponent) {
        this.firstStrike = false;
        return super.attack(opponent);
    }
}
