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
 * Entry created when unit dies
 */
public class DiedEntry extends EntityEntryBase{

    /**
     * Class constructor
     * @param entity What unit died
     * @param currentHexCoordinates Where did it die
     * @param player What player did it belong to
     * @param timestamp When was it made
     */
    public DiedEntry(Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        action = actions.died;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s died in (%d,%d)",
                entity.toString(),
                (int) currentHexCoordinates.getX(),
                (int) currentHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
