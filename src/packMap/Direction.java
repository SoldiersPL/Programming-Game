package packMap;

import java.awt.Point;

/**
 * Enumerator allowing easier navigation across the map for units, by convering simple directions in to coordinates
 */
public enum Direction {

    /**
     * North
     */
    N(0, -1, -1), // (incColumn  , incRowEven , incRowOdd)

    /**
     * North-East
     */
    NE(1, 0, -1),

    /**
     * South-East
     */
    SE(1, 1, 0),

    /**
     * South
     */
    S(0, 1, 1),

    /**
     * South-West
     */
    SW(-1, 1, 0),

    /**
     * North-West
     */
    NW(-1, 0, -1),

    /**
     * Center
     */
    C(0, 0, 0);
    private final int incColumn; // how much to increase column ( x ) by
    private final int incRowEven; // how much to increase row ( y ) by, if column is even
    private final int incRowOdd; // how much to increase row ( y ) by, if column is odd

    private Direction(final int incI, final int incJEven, final int incJOdd) {
        this.incColumn = incI;
        this.incRowEven = incJEven;
        this.incRowOdd = incJOdd;
    }

    /**
     * @return How much to increase column ( x ) by
     */
    public int getIncColumn() {
        return incColumn;
    }

    /**
     * @return how much to increase row ( y ) by, if column is even
     */
    public int getIncRowEven() {
        return incRowEven;
    }

    /**
     * @return how much to increase row ( y ) by, if column is odd
     */
    public int getIncRowOdd() {
        return incRowOdd;
    }
    
    /**
     * Based on what value is this enumerator in, give coordinates to hex neighbouring one of provided coordinates
     * Example: If provided coordinates are X = 1 and Y = 1, and requested enumerator is S(South), then returned coordinates will be X = 1 and Y = 2
     * @param x Coordinate of starting hex
     * @param y Coordinate of starting hex
     * @return Coordinates of hex in requested direction
     */
    public Point getNeighborCoordinates(int x , int y) {
        int column = x + getIncColumn();
        int row = y + (Util.isEven(x) ? getIncRowEven() : getIncRowOdd());
        return new Point(column, row);
    }

    /**
     * Based on what value is this enumerator in, give coordinates to hex neighbouring one of provided coordinates
     * Example: If provided coordinates are X = 1 and Y = 1, and requested enumerator is S(South), then returned coordinates will be X = 1 and Y = 2 
     * @param coordinates Coordinate of starting hex
     * @return Coordinates of hex in requested direction
     */
    public Point getNeighborCoordinates(Point coordinates) {
        /*
        int column = coordinates.x + getIncColumn();
        int row = coordinates.y + (Util.isEven(coordinates.x) ? getIncRowEven() : getIncRowOdd());
        */
        return getNeighborCoordinates(coordinates.x, coordinates.y);
    }
}