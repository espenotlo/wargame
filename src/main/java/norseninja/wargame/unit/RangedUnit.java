package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;

import java.util.LinkedList;
import java.util.List;

public class RangedUnit extends Unit {

    public RangedUnit(String name, int health, int attack, int armor, Field field, Location location) {
        super(name, health, attack, armor, field, location);
    }

    public RangedUnit (String name, int health, Field field, Location location) {
        super(name, health, 15, 10, field, location);
    }

    @Override
    public int getAttackBonus() {
        return 6;
    }

    @Override
    public int getResistBonus() {
        return 0;
    }

    @Override
    public void act() {
        List<Location> l = getField().getOccupiedAdjacentLocations(getLocation(), 29);
        List<Location> opponentsLocations = new LinkedList<>();
        for (Location location : l) {
            Object obj = getField().getObjectAt(location);
            if (obj instanceof Unit) {
                Unit unit = (Unit) obj;
                if (unit.getArmy() != getArmy()) {
                    opponentsLocations.add(location);
                }
            }
        }
        if (opponentsLocations.size() > 0) {
            Location targetLocation = getLocation().getNearestLocation(opponentsLocations);
            Location newLocation = targetLocation.getNearestLocation(getField().getFreeAdjacentLocations(getLocation()));
            if (null != newLocation && newLocation.distanceTo(targetLocation) >= 3
                    && newLocation.distanceTo(targetLocation) <= getLocation().distanceTo(targetLocation)) {

                setLocation(newLocation);
            }
            //Opponent within attack range:
            if (getLocation().distanceTo(targetLocation) <= 3) {
                attack((Unit) getField().getObjectAt(targetLocation));
            }
        }
    }
}
