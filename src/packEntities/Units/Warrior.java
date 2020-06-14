/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Units;

import java.awt.image.BufferedImage;
import packPlayer.Player;

/**
 *
 * @author user
 */
public class Warrior extends Unit {

    public Warrior(Player player) {
        super(player);
        Hp = 10;
        Att = 4;
    }
    @Override
    public BufferedImage getIlustration() {
        return getIlustration("sword.png");
    }

    @Override
    public String toString() {
        return "Warrior";
    }
    
}
