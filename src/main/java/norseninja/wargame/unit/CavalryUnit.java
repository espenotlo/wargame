package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;

import java.util.LinkedList;
import java.util.List;

public class CavalryUnit extends Unit {
    private boolean firstStrike = true;

    public CavalryUnit(String name, int health, int attack, int armor, Field field, Location location) {
        super(name, health, attack, armor, field, location);
    }

    public CavalryUnit (String name, int health, Field field, Location location) {
        super(name, health, 15, 10, field, location);
    }

    @Override
    public int getAttackBonus() {
        if (firstStrike) {
            firstStrike = false;
            return 6;
        } else {
            return 2;
        }
    }

    @Override
    public int getResistBonus() {
        return 2;
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
            Location newLocation = targetLocation.getNearestLocation(getField().getFreeAdjacentLocations(getLocation(), 3));
            if (null != newLocation && newLocation.distanceTo(targetLocation) >= 1
                    && newLocation.distanceTo(targetLocation) <= getLocation().distanceTo(targetLocation)) {

                setLocation(newLocation);
            }
            if (getLocation().distanceTo(targetLocation) <= 1) {
                attack((Unit) getField().getObjectAt(targetLocation));
            }
        }
    }
}
