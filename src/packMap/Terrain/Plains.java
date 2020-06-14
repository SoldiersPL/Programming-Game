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
public class Plains extends Hex{

    public Plains(Hex[][] map ,Point coordinates) {
        super(map,coordinates);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }
    public Plains(Hex[][] map ,int column, int row) {
        super(map, column, row);
        freeSlots = InitSlots();
        resources = InitResources();
        baseColor = InitColor();
    }

    @Override
    public Color InitColor() {
        return Color.GREEN; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int InitSlots() {
        return 6; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Plains";
    }
    
}
