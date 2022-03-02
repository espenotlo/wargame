package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;

public class UnitFactory {

    public enum UnitType {
        INFANTRY, RANGED, CAVALRY, PALADIN, WARCHIEF, DEATH_KNIGHT
    }

    public static Unit createUnit(UnitType unitType, Battlefield battlefield, Location location) {
        return switch (unitType) {
            case INFANTRY -> new InfantryUnit("infantry", 60, battlefield, location);
            case RANGED -> new RangedUnit("ranged", 45, battlefield, location);
            case CAVALRY -> new CavalryUnit("cavalry", 75, battlefield, location);
            case PALADIN -> new Paladin("Paladin", 100, battlefield, location);
            case WARCHIEF -> new Warchief("Warchief", 100, battlefield, location);
            case DEATH_KNIGHT -> new DeathKnight("Death Knight", 100, battlefield, location);
        };
    }
}