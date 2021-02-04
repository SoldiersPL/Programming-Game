/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Units;

import java.awt.image.BufferedImage;
import packPlayer.Player;

/**
 * Class representing Warrior type of unit.
 * It is stronger then Harvester, but can be made only in Castle and costs more resources
 */
public class Warrior extends Unit {

    /**
     * Constructor 
     * @param player Player that this entity belongs to 
     */
    public Warrior(Player player) {
        super(player);
        Hp = 10;
        Att = 4;
    }

    /**
     * Generates ilustration for this unit
     * @return Image representing this unit on map
     */
    @Override
    public BufferedImage getIlustration() {
        return getIlustration("sword.png");
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Warrior";
    }
    
}
