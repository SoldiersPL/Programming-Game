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
 * Class representing Forests on map
 * @see Hex
 */
public class Forest extends Hex{

    /**
     * Class Constructor
     * @param map Map this hex belongs to
     * @param coordinates Where is this Hex
     */
    public Forest(Hex[][] map ,Point coordinates) {
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
    public Forest(Hex[][] map ,int column, int row) {
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
        return new Color(75, 0, 0); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initialize amount of slots of this hex
     * @return How many slots
     */
    @Override
    public int InitSlots() {
        return 3; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initialize amount of resources of this hex
     * @return How many resources per gather action
     */
    @Override
    public int InitResources() {
        return 5; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return Class name
     */
    @Override
    public String toString() {
        return "Forest";
    }
    
    
    
}
