package norseninja.wargame.unit;

import norseninja.wargame.Army;
import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;
import norseninja.wargame.Randomizer;
import norseninja.wargame.tempeffect.TempEffect;

import java.util.List;

public abstract class Unit {
    private final String name;
    private int health;
    private int attack;
    private int attackBonus;
    private int armor;
    private int resistBonus;
    private int initiative;
    private int initiativeBonus;
    private int movementSpeed;
    private Army army;
    private Battlefield battlefield;
    private Location location;
    private int attackRange;

    protected Unit (String name, int health, int attack, int armor, Battlefield battlefield, Location location) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        this.battlefield = battlefield;
        this.location = location;
        this.attackBonus = 0;
        this.resistBonus = 0;
        this.initiative = 0;
        this.initiativeBonus = 0;
        this.movementSpeed = 1;
        this.attackRange = 1;
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

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
        if (this.health == 0) {
            setDead();
        }
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setInitiativeBonus(int initiativeBonus) {
        this.initiativeBonus = initiativeBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getResistBonus() {
        return resistBonus;
    }

    public void setResistBonus(int resistBonus) {
        this.resistBonus = resistBonus;
    }

    public int getInitiativeBonus() {
        return this.initiativeBonus;
    }

    public void attack(Unit opponent) {
        int postAttackHealth = Math.min(
                opponent.getHealth()
                        - (Randomizer.getRandom().nextInt(attack) + 1)
                        - this.getAttackBonus()
                        + opponent.getArmor()
                        + opponent.getResistBonus()
                , opponent.getHealth());
        opponent.setHealth(postAttackHealth);
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
            battlefield.clear(location);
        }
        this.location = newLocation;
        battlefield.place(this, newLocation);
    }

    public void act() {
        //Find nearest hostile unit
        Location targetLocation = acquireTarget();

        //If target was spotted
        if (null != targetLocation) {
            int distance = getLocation().distanceTo(targetLocation);

            //If target is out of range:
            if (distance > attackRange) {
                closeIn(targetLocation);

            //Target is too close for comfort:
            } else if (distance < attackRange) {
                keepAtRange(targetLocation);
            }
            //Attack if within range
            if (getLocation().distanceTo(targetLocation) <= attackRange) {
                attack((Unit) getField().getObjectAt(targetLocation));
            }
        }
    }

    private Location acquireTarget() {
        List<Location> hostiles = getField().getHostileLocationsWithinRange(this, Math.max(getField().getDepth(), getField().getWidth()));
        if (!hostiles.isEmpty()) {
            return getLocation().getNearestLocation(hostiles);
        }
        return null;
    }

    private void closeIn(Location targetLocation) {
        Location newLocation = targetLocation.getLocationDownToRange(getField().getFreeAdjacentLocations(getLocation(), getMovementSpeed()), attackRange);
        if (null != newLocation) {
            setLocation(newLocation);
        }
    }

    private void keepAtRange(Location targetLocation) {
        Location newLocation = targetLocation.getLocationUpToRange(getField().getFreeAdjacentLocations(getLocation(), getMovementSpeed()), attackRange);
        if (null != newLocation && newLocation.distanceTo(targetLocation) <= attackRange
                && newLocation.distanceTo(targetLocation) >= getLocation().distanceTo(targetLocation)) {
            setLocation(newLocation);
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
            List<TempEffect> tempEffects = battlefield.getTempEffectsBySource(this);
            if (!tempEffects.isEmpty()) {
                tempEffects.forEach(effect -> {
                    if (effect.isConcentration()) {
                        battlefield.removeTempEffect(effect);
                    }
                });
            }
            battlefield.clear(location);
            location = null;
            battlefield = null;
        }
    }

    public Battlefield getField() {
        return this.battlefield;
    }

    public Location getLocation() {
        return this.location;
    }
}
