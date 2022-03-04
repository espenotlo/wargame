package norseninja.wargame.model;

import java.util.List;

/**
 * A class representing a spot on the battlefield, which a unit may occupy.
 */
public class Location
{
    // Row and column positions.
    private final int row;
    private final int col;

    /**
     * Represent a row and column.
     * @param row The row.
     * @param col The column.
     */
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }

    public static Location parseLocation(String string) {
        if (null != string && string.contains(",") && string.length() > 2) {
            String[] coords = string.strip().split(",");
            try {
                int r = Integer.parseInt(coords[0]);
                int c = Integer.parseInt(coords[1]);
                return new Location(r, c);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Return a string of the form row,column
     * @return A {@code String} representation of the location.
     */
    public String toString() {
        return "(" + row + "," + col + ")";
    }

    /**
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (row << 16) + col;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The column.
     */
    public int getCol() {
        return col;
    }

    /**
     * Calculates the distance from this location to given location.
     * The distance returned is the larger difference between the x and y coordinates of the two locations.
     * @param location The target location.
     * @return {@code int} distance to the given location.
     */
    public int distanceTo(Location location) {
        return Math.max(Math.max(this.getRow(), location.getRow()) - Math.min(this.getRow(), location.getRow())
                , Math.max(this.getCol(), location.getCol()) - Math.min(this.getCol(), location.getCol()));
    }

    /**
     * Finds the nearest location to the current location from a list of locations.
     * In case of a tie, the first of the closest locations is chosen.
     * @param locations The list of locations to compare.
     * @return The nearest {@code Location} to this one.
     */
    public Location getNearestLocation(List<Location> locations) {
        Location closestLocation = locations.get(0);
        for (Location location: locations) {
            if (distanceTo(location) < distanceTo(closestLocation)) {
                closestLocation = location;
            }
        }
        return closestLocation;
    }

    /**
     * Finds the closest location down to the given range. Ignores locations closer than given range.
     * @param locations The list of locations to compare.
     * @param range The desired range.
     * @return The closest {@code Location} down to the given range.
     */
    public Location getLocationDownToRange(List<Location> locations, int range) {
        if (null == locations || locations.isEmpty()) {
            return null;
        }
        Location bestLocation = null;
        int i = range;
        while (null == bestLocation) {
            for (Location location : locations) {
                if (distanceTo(location) - i == 0) {
                    bestLocation = location;
                }
            }
            i++;
        }
        return bestLocation;
    }

    /**
     * Finds the furthest location up to the given range. Ignores all locations further away than the given range.
     * @param locations The list of locations to compare.
     * @param range The desired range.
     * @return The furthest {@code Location} up to the given range.
     */
    public Location getLocationUpToRange(List<Location> locations, int range) {
        Location bestLocation = null;
        int i = range;
        while (null == bestLocation && i > 0) {
            for (Location location : locations) {
                if (i - distanceTo(location) == 0) {
                    bestLocation = location;
                }
            }
            i--;
        }
        return bestLocation;
    }
}