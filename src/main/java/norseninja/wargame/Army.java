package norseninja.wargame;

import norseninja.wargame.unit.Unit;
import norseninja.wargame.unit.UnitFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A class representing an army of units on a battlefield.
 */
public class Army {
    private final String name;
    private final List<Unit> units;
    private final Battlefield battlefield;
    private final Location startLocation;
    private final Random random;

    /**
     * Instantiates the class.
     * @param name Name of the army.
     * @param random The random number generator to be used by the army.
     * @param battlefield The battlefield the army is on.
     * @param startLocation The start location of the army on the battlefield.
     */
    public Army(String name, Random random, Battlefield battlefield, Location startLocation) {
        this.name = name;
        this.units = new ArrayList<>();
        this.battlefield = battlefield;
        this.startLocation = startLocation;
        this.random = random;
    }

    /**
     * Adds the given number of random units to the army.
     * One of them will be a random commander unit.
     * @param numberOfUnits The number of units to be added.
     */
    public void muster(int numberOfUnits) {
        UnitFactory.UnitType unitType =
        switch (random.nextInt(3)) {
            case 0 -> UnitFactory.UnitType.PALADIN;
            case 1 -> UnitFactory.UnitType.WARCHIEF;
            default -> UnitFactory.UnitType.DEATH_KNIGHT;
        };
        Unit commander = UnitFactory.createUnit(unitType, battlefield, startLocation);
        commander.setInitiative(random.nextInt(1000));
        commander.setArmy(this);
        addUnit(commander);
        battlefield.place(commander, startLocation);

        for (int i = 1; i < numberOfUnits; i++) {
            Location start = startLocation
                    .getNearestLocation(battlefield
                            .getFreeAdjacentLocations(startLocation, numberOfUnits));

            switch (random.nextInt(3)) {
                case 0 -> unitType = UnitFactory.UnitType.INFANTRY;
                case 1 -> unitType = UnitFactory.UnitType.RANGED;
                default -> unitType = UnitFactory.UnitType.CAVALRY;

            }
            Unit unit = UnitFactory.createUnit(unitType, battlefield, start);
            unit.setInitiative(random.nextInt(1000));
            unit.setArmy(this);
            addUnit(unit);
            battlefield.place(unit, start);
        }
    }

    /**
     * Adds a unit to the army.
     * @param unit The unit to be added.
     */
    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    /**
     * Adds all units in the given list to the army.
     * @param units The list of units to be added.
     */
    public void addAll(List<Unit> units) {
        this.units.addAll(units);
    }

    /**
     * Removes given unit from the army.
     * @param unit the unit to be removed.
     */
    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    /**
     * Checks if there are any living units in the army.
     * @return {@code true} if there are any living units in the army; {@code false} if not.
     */
    public boolean hasUnits() {
        return units.stream().anyMatch(u -> u.getHealth() > 0);
    }

    /**
     * Gets a random, living unit from the army.
     * @return A random living {@code Unit}, or {@code null} if no living units remain.
     */
    public Unit getRandom() {
        Unit returnUnit = null;
        List<Unit> livingUnits = units
                .stream()
                .filter(u -> u.getHealth() > 0)
                .collect(Collectors.toList());
        if (!livingUnits.isEmpty()) {
            returnUnit = livingUnits.get(random.nextInt(livingUnits.size()));
        }

        return returnUnit;
    }

    /**
     * @return {@code List<Unit>} of all units in the army.
     */
    public List<Unit> getUnits() {
        return this.units;
    }

    /**
     * @return {@code String} name of the army.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return {@code Battlefield} the army is on.
     */
    public Battlefield getField() {
        return this.battlefield;
    }

    /**
     * Gets a map containing the different unit types in the army and their counts.
     * @return {@code Map<String,Integer>} of the different unit types and their counts.
     */
    public Map<String, Integer> getRoster() {
        return this.units.stream().collect(Collectors.toMap(Unit::getName, e -> 1, Integer::sum));
    }
}