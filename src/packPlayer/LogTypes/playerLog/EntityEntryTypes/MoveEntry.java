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
 * Entry created when unit moves
 */
public class MoveEntry extends EntityEntryBase{

    /**
     * Where did it move
     */
    protected final Point targetHexCoordinates;

    /**
     * Class construcotr
     * @param targetHexCoordinates Where did entity moved
     * @param entity Entity that moved
     * @param currentHexCoordinates From where did it move
     * @param player Player that entity belongs to
     * @param timestamp When was it made
     */
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
