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
 * Entry created when entity is deployed on the map
 */
public class DeployEntry extends EntityEntryBase{

    /**
     * By who it was deployed by
     * @see packEntities.Units.Harvester
     * @see packEntities.Buildings.Castle
     * @see packEntities.Buildings.City
     */
    protected final Entity targetEntity;

    /**
     * From where it was deployed
     */
    protected final Point targetHexCoordinates;

    /**
     * Class constructor
     * @param targetEntity By who it was deployed by
     * @param targetHexCoordinates From where it was deployed
     * @param entity What unit was deployed
     * @param currentHexCoordinates Where it was deployed
     * @param player Who does entity belongs to
     * @param timestamp When was it made
     */
    public DeployEntry(Entity targetEntity, Point targetHexCoordinates, Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        this.targetEntity = targetEntity;
        this.targetHexCoordinates = targetHexCoordinates;
        action = actions.deploy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s deployed %s to (%d,%d)",
                entity.toString(),
                targetEntity.toString(),
                (int) targetHexCoordinates.getX(),
                (int) targetHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
