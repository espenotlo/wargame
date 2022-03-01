package norseninja.wargame;

import norseninja.wargame.unit.Unit;
import norseninja.wargame.unit.UnitFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Army {
    private final String name;
    private final List<Unit> units;
    private final Field field;
    private final Location startLocation;
    private final Random random;

    public Army(String name, Random random, Field field, Location startLocation) {
        this.name = name;
        this.units = new ArrayList<>();
        this.field = field;
        this.startLocation = startLocation;
        this.random = random;
    }

    public void muster(int numberOfUnits) {
        for (int i = 0; i< numberOfUnits; i++) {
            Location start = startLocation.getNearestLocation(field.getFreeAdjacentLocations(startLocation,numberOfUnits));
            Unit unit = UnitFactory.createUnit(random.nextInt(3), field, start);
            unit.setInitiative(random.nextInt(1000));
            unit.setArmy(this);
            addUnit(unit);
            field.place(unit, start);
        }
    }



    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public void addAll(List<Unit> units) {
        this.units.addAll(units);
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    public boolean hasUnits() {
        return units.stream().anyMatch(u -> u.getHealth() > 0);
    }

    public Unit getRandom() {
        Unit returnUnit = null;
        List<Unit> livingUnits = units.stream().filter(u -> u.getHealth() > 0).collect(Collectors.toList());
        if (livingUnits.size() > 0) {
            Random r = new Random(LocalDateTime.now().getNano());
            returnUnit = livingUnits.get(r.nextInt(livingUnits.size()));
        }

        return returnUnit;
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public String getName() {
        return this.name;
    }

    public Field getField() {
        return this.field;
    }

    public Map<String, Integer> getRoster() {
        return this.units.stream().collect(Collectors.toMap(Unit::getName, e -> 1, Integer::sum));
    }
}