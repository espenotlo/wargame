package norseninja.wargame;

import norseninja.wargame.unit.Unit;

import java.util.*;

public class Field {

    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();

    private final int depth;
    private final int width;
    private final Object[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
        this.field = new Object[depth][width];
    }

    /**
     * Empty the field.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }

    /**
     * Place a unit at the given location.
     * If there is already a unit at the location it will
     * be lost.
     * @param unit The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object unit, int row, int col)
    {
        place(unit, new Location(row, col));
    }

    /**
     * Place a unit at the given location.
     * If there is already a unit at the location it will
     * be lost.
     * @param unit The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object unit, Location location)
    {
        field[location.getRow()][location.getCol()] = unit;
    }

    /**
     * Return the unit at the given location, if any.
     * @param location Where in the field.
     * @return The unit at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Return the unit at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The unit at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col)
    {
        return field[row][col];
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> getAdjacentLocations(Location location) {
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
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
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @param range The range of adjacent locations.
     * @return A list of locations adjacent to that given.
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
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location);
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
     * @return A list of free adjacent locations.
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
     * @return A list of occupied adjacent locations.
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

    public List<Location> getAllLocations() {
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                locations.add(new Location(row, col));
            }
        }
        // Shuffle the list. Several other methods rely on the list
        // being in a random order.
        Collections.shuffle(locations, rand);

        return locations;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }

    public List<Unit> getRow(int row) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            units.add((Unit) getObjectAt(row,i));
        }
        return units;
    }

    public List<Unit> getColumn(int column) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            units.add((Unit) getObjectAt(i,column));
        }
        return units;
    }

    public List<List<Unit>> getFieldAsList() {
        List<List<Unit>> rows = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            rows.add(getRow(i));
        }
        return rows;
    }

    /**
     * Draws the field in a string format.
     * @return string format of the field.
     */
    public String getAsString() {
        StringBuilder sb = new StringBuilder("Field:\n");
        int i = 0;
        for (int y = 0; y < depth; y++) {
            for (int x = 0; x < width; x++) {
                if (field[x][y] == null) {
                    sb.append("- ");
                } else {
                    Unit unit = (Unit) field[x][y];
                    if (unit.getArmy().getName().equals("one")) {
                        sb.append("X");
                    } else {
                        sb.append("O");
                    }
                    sb.append(" ");
                    i++;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
