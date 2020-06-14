package packMap;

import java.awt.Point;

public enum Direction {
    N(0, -1, -1), // (incColumn  , incRowEven , incRowOdd)
    NE(1, 0, -1),
    SE(1, 1, 0),
    S(0, 1, 1),
    SW(-1, 1, 0),
    NW(-1, 0, -1),
    C(0, 0, 0);
    private final int incColumn; // how much to increase column ( x ) by
    private final int incRowEven; // how much to increase row ( y ) by, if column is even
    private final int incRowOdd; // how much to increase row ( y ) by, if column is odd

    private Direction(final int incI, final int incJEven, final int incJOdd) {
        this.incColumn = incI;
        this.incRowEven = incJEven;
        this.incRowOdd = incJOdd;
    }

    public int getIncColumn() {
        return incColumn;
    }

    public int getIncRowEven() {
        return incRowEven;
    }

    public int getIncRowOdd() {
        return incRowOdd;
    }
    
    public Point getNeighborCoordinates(int x , int y) {
        int column = x + getIncColumn();
        int row = y + (Util.isEven(x) ? getIncRowEven() : getIncRowOdd());
        return new Point(column, row);
    }

    public Point getNeighborCoordinates(Point coordinates) {
        /*
        int column = coordinates.x + getIncColumn();
        int row = coordinates.y + (Util.isEven(coordinates.x) ? getIncRowEven() : getIncRowOdd());
        */
        return getNeighborCoordinates(coordinates.x, coordinates.y);
    }
    
    public static boolean CheckValues(int x, int y)
    {
        return (x < -1 || x > 1 || y < -1 || y > 1);
    }
    public static boolean CheckValues(Point values)
    {
        return CheckValues(values.x,values.y);
    }
}