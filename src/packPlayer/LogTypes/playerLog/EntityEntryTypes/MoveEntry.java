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
 *
 * @author piotr
 */
public class MoveEntry extends EntityEntryBase{
    protected final Point targetHexCoordinates;

    public MoveEntry(Point targetHexCoordinates, Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        this.targetHexCoordinates = targetHexCoordinates;
        action = actions.move;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s moved from (%d,%d) to (%d,%d)",
                entity.toString(),
                (int) currentHexCoordinates.getX(),
                (int) currentHexCoordinates.getY(),
                (int) targetHexCoordinates.getX(),
                (int) targetHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
