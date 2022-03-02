package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;

public abstract class CommanderUnit extends CavalryUnit {

    protected CommanderUnit(String name, int health, int attack, int armor, Battlefield battlefield, Location location) {
        super(name, health, attack, armor, battlefield, location);
    }

    @Override
    public void act() {
        enableAura();
        super.act();
    }

    public abstract void enableAura();
}
