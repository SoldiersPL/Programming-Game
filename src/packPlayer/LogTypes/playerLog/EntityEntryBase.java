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
 *
 * @author piotr
 */
public abstract class EntityEntryBase extends playerLogEntry {
    protected final Entity entity;
    protected final Point currentHexCoordinates;
    public static enum actions{ move(1), attack(2), harvest(4), make(8), deploy(16), returnRes(32), died(64);
    
        private final int value;
        private actions(int value) {
            this.value = value;
        }
        public int getValue()
        {
            return value;
        }
    };
    protected actions action;

    public EntityEntryBase(Entity entity, Point currentHexCoordinates, Player player, Instant timestamp) {
        super(player, timestamp);
        this.entity = entity;
        this.currentHexCoordinates = currentHexCoordinates;
    }
    
    public Entity getEntity() {
        return entity;
    }

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
