package norseninja.wargame;

import norseninja.wargame.tempeffect.TempEffect;
import norseninja.wargame.unit.Unit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class representing a field in which to do battle.
 * Contains a field on which units may roam,
 * as well as support for temporary battlefield effects.
 */
public class Battlefield {

    // A random number generator for providing random locations.
    private static final Random random = Randomizer.getRandom();

    private final int depth;
    private final int width;
    private final Object[][] field;
    private final List<TempEffect> tempEffects;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Battlefield(int depth, int width) {
        this.depth = depth;
        this.width = width;
        this.field = new Object[depth][width];
        this.tempEffects = new ArrayList<>();
    }

    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location) {
        field[location.getRow()][location.getCol()] = null;
    }

    /**
     * Place a unit at the given location.
     * If there is already a unit at the location it will
     * be lost.
     * @param unit The unit to be placed.
     * @param location Where to place the unit.
     */
    public void place(Object unit, Location location)
    {
        field[location.getRow()][location.getCol()] = unit;
    }

    /**
     * Return the unit at the given location, if any.
     * @param location Where in the field.
     * @return The {@code Object} at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Return the unit at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The {@code Object} at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col) {
        return field[row][col];
    }

    /**
     * Return a shuffled list of locations within range of the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @param range The range of adjacent locations.
     * @return A {@code List<Location>} of locations within range of that given.
     */
    public List<Location> getAdjacentLocations(Location location, int range) {
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -range; roffset <= range; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -range; coffset <= range; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, random);
        }
        return locations;
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @param range Get locations within this range.
     * @return A {@code List<Location>} of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location, int range)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location, range);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @param range Get locations within this range.
     * @return A {@code List<Location>} of occupied adjacent locations.
     */
    public List<Location> getOccupiedAdjacentLocations(Location location, int range)
    {
        List<Location> occupied = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location, range);
        for(Location l : adjacent) {
            if(getObjectAt(l) != null) {
                occupied.add(l);
            }
        }
        return occupied;
    }

    /**
     * Get a shuffled list of the locations containing hostile units.
     * @param unit The unit seeking hostiles.
     * @param range The range of the search.
     * @return A {@code List<Location>} of locations within range containing hostile units.
     */
    public List<Location> getHostileLocationsWithinRange(Unit unit, int range) {
        return getHostileUnitsWithinRange(unit, range)
                .stream()
                .map(Unit::getLocation)
                .collect(Collectors.toList());
    }

    /**
     * Get a shuffled list of all units within range.
     * @param location Location of the searcher.
     * @param range Range of the search.
     * @return A {@code List<Unit>} of all units within range.
     */
    public List<Unit> getUnitsWithinRange(Location location, int range) {
        List<Unit> units = new LinkedList<>();
        List<Location> occupiedLocations = getOccupiedAdjacentLocations(location, range);
        for (Location l : occupiedLocations) {
            if (getObjectAt(l) instanceof Unit) {
                units.add((Unit) getObjectAt(l));
            }
        }
        return units;
    }

    /**
     * Get a shuffled list of hostile units within range.
     * @param unit The unit seeking hostiles.
     * @param range The range of the search.
     * @return A {@code List<Unit>} of all hostile units within range.
     */
    public List<Unit> getHostileUnitsWithinRange(Unit unit, int range) {
        List<Unit> units = getUnitsWithinRange(unit.getLocation(), range)
                .stream()
                .filter(u -> u.getArmy() != unit.getArmy())
                .collect(Collectors.toList());
        Collections.shuffle(units, random);

        return units;
    }

    /**
     * Get a shuffled list of friendly units within range.
     * @param unit The unit seeking friends.
     * @param range The range of the search.
     * @return A {@code List<Unit>} of all friendly units within range.
     */
    public List<Unit> getFriendlyUnitsWithinRange(Unit unit, int range) {
        List<Unit> units = getUnitsWithinRange(unit.getLocation(), range)
                .stream()
                .filter(u -> u.getArmy() == unit.getArmy())
                .collect(Collectors.toList());
        Collections.shuffle(units, random);

        return units;
    }

    /**
     * Return the depth of the field.
     * @return The {@code int} depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Return the width of the field.
     * @return The {@code int} width of the field.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns a list containing all cells of given row in the field.
     * @param row the row to be returned
     * @return A {@code List<Unit>} containing all cells in a row.
     */
    public List<Unit> getRow(int row) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            units.add((Unit) getObjectAt(row,i));
        }
        return units;
    }

    /**
     * Returns a list of all the rows of the field.
     * @return A {@code List<List<Unit>>} containing all rows of the field.
     */
    public List<List<Unit>> getFieldAsList() {
        List<List<Unit>> rows = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            rows.add(getRow(i));
        }
        return rows;
    }

    /**
     * Draws the field in a string format.
     * @return {@code String} format of the field.
     */
    public String getAsString() {
        StringBuilder sb = new StringBuilder("Field:\n");
        for (int x = 0; x < depth; x++) {
            for (int y = 0; y < width; y++) {
                if (field[x][y] == null) {
                    sb.append("- ");
                } else {
                    Unit unit = (Unit) field[x][y];
                    switch (unit.getArmy().getName()) {
                        case "one" -> sb.append("A ");
                        case "two" -> sb.append("B ");
                        case "three" -> sb.append("C ");
                        case "four" -> sb.append("D ");
                        default -> sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Gets a list containing all temporary effects
     * (buffs, debuffs and auras) currently in effect.
     * @return A {@code List<TempEffect>} of all temporary effects in effect.
     */
    public List<TempEffect> getTempEffects() {
        return this.tempEffects;
    }

    /**
     * Signals that a turn has passed, removes
     * temporary effects that have expired and
     * updates the targets of all aura effects.
     */
    public void tickTempEffects() {
        tempEffects.removeIf(e -> !e.tick());
    }

    /**
     * Adds a temporary effect (buff, debuff or aura)
     * to the field.
     * @param tempEffect The temporary effect to be added.
     */
    public void addTempEffect(TempEffect tempEffect) {
        this.tempEffects.add(tempEffect);
    }

    /**
     * Removes a temporary effect (buff, debuff or aura)
     * from the field.
     * @param tempEffect The temporary effect to be removed.
     */
    public void removeTempEffect(TempEffect tempEffect) {
        this.tempEffects.remove(tempEffect);
    }

    /**
     * Gets a list containing all temporary effects
     * originating from given unit.
     * @param unit The source of the temporary effects.
     * @return A {@code List<TempEffect>} of temporary effects from given unit.
     */
    public List<TempEffect> getTempEffectsBySource(Unit unit) {
        return tempEffects.stream().filter(effect -> effect.getSource() == unit).collect(Collectors.toList());
    }
}
