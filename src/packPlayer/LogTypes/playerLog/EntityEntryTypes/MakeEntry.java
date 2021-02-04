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
 * Entry made when entity was created
 */
public class MakeEntry extends EntityEntryBase{

    /**
     * What entity created it
     * @see packEntities.Units.Harvester
     * @see packEntities.Buildings.Castle
     * @see packEntities.Buildings.City
     */
    protected final Entity targetEntity;

    /**
     * Class construcotr
     * @param targetEntity Entity that created it
     * @param entity Created entity
     * @param currentHexCoordinates Where was it created
     * @param player Player that entity belongs to
     * @param timestamp When was it made
     */
    public MakeEntry(Entity targetEntity, Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        this.targetEntity = targetEntity;
        action = actions.make;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s made %s in (%d,%d)",
                entity.toString(),
                targetEntity.toString(),
                (int) currentHexCoordinates.getX(),
                (int) currentHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
