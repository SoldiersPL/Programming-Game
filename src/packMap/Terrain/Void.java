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
 *
 * @author user
 */
public class Void extends Hex{

    public Void(Hex[][] map ,Point coordinates) {
        super(map,coordinates);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }
    public Void(Hex[][] map ,int column, int row) {
        super(map, column, row);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }

    
    @Override
    public Color InitColor() {
        return Color.BLACK; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Void";
    }
    
}
