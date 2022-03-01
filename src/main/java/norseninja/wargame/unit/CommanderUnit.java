package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;

public class CommanderUnit extends CavalryUnit {

    public CommanderUnit(String name, int health, int attack, int armor, Field field, Location location) {
        super(name, health, attack, armor, field, location);
    }

    CommanderUnit(String name, int health, Field field, Location location) {
        super(name, health, 25, 15, field, location);
    }
}
