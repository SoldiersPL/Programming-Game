/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packMap.Terrain;

import java.awt.Color;
import java.awt.Point;
import packMap.Hex;

/**
 * Class representing Empty tile on map
 * It mostly exists to deny units from going over that specific route
 * @see Hex
 */
public class Void extends Hex{

    /**
     * Class Constructor
     * @param map Map this hex belongs to
     * @param coordinates Where is this Hex
     */
    public Void(Hex[][] map ,Point coordinates) {
        super(map,coordinates);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }

    /**
     * Class Constructor
     * @param map Map this hex belongs to
     * @param column Where is this Hex
     * @param row Where is this Hex
     */
    public Void(Hex[][] map ,int column, int row) {
        super(map, column, row);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }

    /**
     * Initialize color of this hex
     * @return Color of the hex
     */
    @Override
    public Color InitColor() {
        return Color.BLACK; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return Class name
     */
    @Override
    public String toString() {
        return "Void";
    }
    
}
