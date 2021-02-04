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
 * Entry created when unit attacks another entity
 */
public class AttackEntry extends EntityEntryBase{

    /**
     * Entity to be attacked
     */
    protected final Entity targetEntity;

    /**
     * Where is targeted entity
     */
    protected final Point targetHexCoordinates;

    /**
     * Clas constructor
     * @param targetEntity Entity to be attacked
     * @param targetHexCoordinates Where is that entity
     * @param entity Initiating entity
     * @param currentHexCoordinates Where is that entity
     * @param player What played does initiating Entity belongs to
     * @param timestamp When was it made
     */
    public AttackEntry(Entity targetEntity, Point targetHexCoordinates, Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(entity, currentHexCoordinates, player, timestamp);
        this.targetEntity = targetEntity;
        this.targetHexCoordinates = targetHexCoordinates;
        action = actions.attack;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("%s attacked %s from (%d,%d) in (%d,%d)",
                entity.toString(),
                targetEntity.toString(),
                (int) currentHexCoordinates.getX(),
                (int) currentHexCoordinates.getY(),
                (int) targetHexCoordinates.getX(),
                (int) targetHexCoordinates.getY()));
        sb.append("</html>");
        return sb.toString();
    }


}
