/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes.playerLog.EntityEntryTypes;

import java.awt.Point;
import java.time.Instant;
import packEntities.Entity;
import packPlayer.LogTypes.playerLog.EntityEntryBase;
import packPlayer.Player;

/**
 * Entry created when entity have harvested resources from hex
 * @see packEntities.Units.Harvester
 */
public class HarvestEntry extends EntityEntryBase{

    /**
     * Class constructor
     * @param entity Entity that have harvested resources
     * @param currentHexCoordinates Wher were they harvested
     * @param player Played that entity belongs to
     * @param timestamp When was it made
     */
    public HarvestEntry(Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        action = actions.harvest;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s harvested resources in (%d,%d)",
                entity.toString(),
                (int) currentHexCoordinates.getX(),
                (int) currentHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
