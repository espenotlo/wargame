package norseninja.wargame.unit;

import norseninja.wargame.Army;
import norseninja.wargame.Field;
import norseninja.wargame.Location;
import norseninja.wargame.Randomizer;

import java.util.LinkedList;
import java.util.List;

public abstract class Unit {
    private final String name;
    private int health;
    private int attack;
    private int armor;
    private int initiative;
    private Army army;
    private Field field;
    private Location location;

    public Unit (String name, int health, int attack, int armor, Field field, Location location) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        this.initiative = 0;
        this.field = field;
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
        if (this.health == 0) {
            setDead();
        }
    }

    abstract int getAttackBonus();

    abstract int getResistBonus();

    public int attack(Unit opponent) {
        int postAttackHealth = Math.min(
                opponent.getHealth()
                        - Randomizer.getRandom().nextInt(attack)
                        - this.getAttackBonus()
                        + opponent.getArmor()
                        + opponent.getResistBonus()
                , opponent.getHealth());
        opponent.setHealth(postAttackHealth);
        return postAttackHealth;
    }

    public Army getArmy() {
        return this.army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public int getInitiative() {
        return this.initiative;
    }

    public void rollInitiative() {
        setInitiative(Randomizer.getRandom().nextInt(1000));
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setLocation(Location newLocation) {
        if (null != location) {
            field.clear(location);
        }
        this.location = newLocation;
        field.place(this, newLocation);
    }

    public void act() {
        List<Location> l = field.getOccupiedAdjacentLocations(location, 29);
        List<Location> opponentsLocations = new LinkedList<>();
        for (Location location : l) {
            Object obj = field.getObjectAt(location);
            if (obj instanceof Unit) {
                Unit unit = (Unit) obj;
                if (unit.getArmy() != this.army) {
                    opponentsLocations.add(location);
                }
            }

        }
        if (opponentsLocations.size() > 0) {
            Location targetLocation = location.getNearestLocation(opponentsLocations);
            Location newLocation = targetLocation.getNearestLocation(field.getFreeAdjacentLocations(location));
            if (null != newLocation && newLocation.distanceTo(targetLocation) >= 1
                    && newLocation.distanceTo(targetLocation) <= getLocation().distanceTo(targetLocation)) {
                setLocation(newLocation);
            }
            if (getLocation().distanceTo(targetLocation) <= 1) {
                attack((Unit) field.getObjectAt(targetLocation));
            }
        }
    }

    /**
     * Indicate that the unit is no longer alive.
     * It is removed from the field.
     */
    private void setDead()
    {
        if (location != null)
        {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected Field getField() {
        return this.field;
    }

    public Location getLocation() {
        return this.location;
    }
}