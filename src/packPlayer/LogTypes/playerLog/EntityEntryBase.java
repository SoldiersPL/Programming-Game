/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes.playerLog;

import java.awt.Point;
import java.time.Instant;
import packEntities.Entity;
import packPlayer.LogTypes.logEntry;
import packPlayer.LogTypes.playerLogEntry;
import packPlayer.Player;

/**
 * Class representing entries made by Player's entities
 */
public abstract class EntityEntryBase extends playerLogEntry {

    /**
     * Entity that made this entry
     */
    protected final Entity entity;

    /**
     * Where was it made on the map
     */
    protected final Point currentHexCoordinates;

    /**
     * Actions that entry can be created by
     * This enum mostly exists for comparison sake, so that raport won't mention unit's death before it was created if there is no break between actions
     */
    public static enum actions{

        /**
         * Unit was created
         */
        make(1),
        
        /**
         * Unit was deployed on the map
         */
        deploy(2),
        
        /**
         * Unit have moved
         */
        move(4),

        /**
         * Unit have attacked
         */
        attack(8),

        /**
         * Unit have harvested resources
         */
        harvest(16),

        /**
         * Unit have returned resources
         */
        returnRes(32),

        /**
         * Unit have died
         */
        died(64);
    
        private final int value;
        private actions(int value) {
            this.value = value;
        }

        /**
         * @return Sorting value of the action
         */
        public int getValue()
        {
            return value;
        }
    };

    /**
     * What action was this entry created by
     */
    protected actions action;

    /**
     * Class constructor
     * @param entity Entity creating the entry
     * @param currentHexCoordinates Where was it made
     * @param player Player that entity belongs to
     * @param timestamp When was it made
     */
    public EntityEntryBase(Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(player, timestamp);
        this.entity = entity;
        this.currentHexCoordinates = currentHexCoordinates;
    }
    
    /**
     * @return Entity that this entry was created by
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @return Where was it created
     */
    public Point getCurrentHexCoordinates() {
        return currentHexCoordinates;
    }

    @Override
    public int compareTo(logEntry o) {
        int result = super.compareTo(o);
        if(result == 0)
        {
            if(o instanceof EntityEntryBase)
            {
                EntityEntryBase tmp = (EntityEntryBase)o;
                if(tmp.action.equals(this.action))
                {
                    if(tmp.entity.equals(this.entity))
                    {
                        //If coordinates aren't equal to each other
                        if(! this.currentHexCoordinates.equals(tmp.currentHexCoordinates))
                        {
                            int tmp2 = this.currentHexCoordinates.x - tmp.currentHexCoordinates.x;
                            result = tmp2 == 0 ? this.currentHexCoordinates.y - tmp.currentHexCoordinates.y :  tmp2;
                        }
                    }
                    //if units are same, compare IDs
                    else result = this.entity.unitID - tmp.entity.unitID;
                }
                //if action is different, order them based on value of action
                else result = this.action.getValue() - tmp.action.getValue();
            }
            //else it belongs to EntityEntityBase, move it up
            else result = 1;
        }
        return result;
    }
    
    
}
