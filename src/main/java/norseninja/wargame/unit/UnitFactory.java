package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;

public class UnitFactory {
    public static final int INFANTRY = 0;
    public static final int RANGED = 1;
    public static final int CAVALRY = 2;
    public static final int COMMANDER = 4;

    public static Unit createUnit(int unitType, Field field, Location location) {
        Unit returnUnit = null;

        if (unitType == 0) {
            returnUnit = new InfantryUnit("infantry", 60, field, location);
        } else if (unitType == 1) {
            returnUnit = new RangedUnit("ranged", 45, field, location);
        } else if (unitType == 2) {
            returnUnit = new CavalryUnit("cavalry", 75, field, location);
        } else if (unitType == 3) {
            returnUnit = new CommanderUnit("commander", 90, field, location);
        }
        return returnUnit;
    }
}
