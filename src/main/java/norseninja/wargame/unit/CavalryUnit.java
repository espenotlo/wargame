package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;

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
    public void attack(Unit opponent) {
        super.attack(opponent);
        this.firstStrike = false;
    }
}
