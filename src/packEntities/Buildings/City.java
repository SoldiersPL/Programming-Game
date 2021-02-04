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
 * Class representing City type of building
 * It can create Worker units
 */
public class City extends Building{

    /**
     * Constructor
     * @param player Player that this entity belongs to
     */
    public City(Player player) {
        super(player);
        Hp = 30;
        Att = 0;
        permitedBuildableUnits.put("Harvester", true);
    }

    /**
     * Generates ilustration for this unit
     * @return Image representing this unit on map
     */
    @Override
    public BufferedImage getIlustration() {
        return getIlustration("house.png");
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        lock = new ReentrantLock();
        queue = new ArrayList<>();
        permitedBuildableUnits = new HashMap<>();
        permitedBuildableUnits.put("Harvester", true);
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "City";
    }
}
