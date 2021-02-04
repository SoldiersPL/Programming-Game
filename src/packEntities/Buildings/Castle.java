/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Buildings;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import packPlayer.Player;

/**
 * Class representing Castle type of building
 * It is stronger then City and can make Warrior type of unit
 */
public class Castle extends Building{
    
    /**
     * Constructor
     * @param player Player that this entity belongs to
     */
    public Castle(Player player) {
        super(player);
        Hp = 50;
        Att = 5;
        permitedBuildableUnits.put("Warrior", true);
    }

    /**
     * Generates ilustration for this unit
     * @return Image representing this unit on map
     */
    @Override
    public BufferedImage getIlustration() {
        return getIlustration("tower.png");
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        lock = new ReentrantLock();
        queue = new ArrayList<>();
        permitedBuildableUnits = new HashMap<>();
        permitedBuildableUnits.put("Warrior", true);
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Castle";
    }
}
