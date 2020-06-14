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
 *
 * @author user
 */
public class Castle extends Building{
    
    public Castle(Player player) {
        super(player);
        Hp = 50;
        Att = 5;
        permitedBuildableUnits.put("Warrior", true);
    }
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
    @Override
    public String toString() {
        return "Castle";
    }
}
