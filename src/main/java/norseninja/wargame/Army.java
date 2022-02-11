package norseninja.wargame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Army {
    private String name;
    private List<Unit> units;

    public Army(String name) {
        this.name = name;
        this.units = new ArrayList<Unit>();
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
        return this.units.size() > 0;
    }

    public Unit getRandom() {
        Random r = new Random();
        return this.units.get(r.nextInt(units.size()));
    }
}
