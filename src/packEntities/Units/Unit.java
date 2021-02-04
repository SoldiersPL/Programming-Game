/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Units;

import java.awt.Point;
import java.time.Instant;
import packEntities.Entity;
import packEntities.EntityDescriptor;
import packMap.Direction;
import packMap.Hex;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.MoveEntry;
import packPlayer.Player;

/**
 * Class representing all units in the game.
 * They can usually move and attack
 */
public abstract class Unit extends Entity {
    
    /**
     * Constructor
     * @param player Player that this entity belongs to
     */
    public Unit(Player player) {
        super(player);
        size = InitSize();
    }

    /**
     * Initiates attack on enemy unit
     * @param entity Direct pointer to unit that has to be attacked
     * @return True if attack succeeded, false if not
     */
    public final boolean Attack(EntityDescriptor entity)
    {
        if(Hp <= 0) return false; // dead units can't attack
        if(! entity.getHex().CheckIfNeighbour(hex)) return false; // units have to be at least in neighbouring hex
        entity.Attack(this); // since user can't access other player's units
        postActionBreak();
        return true;
    }

    /**
     * Initiates attack on enemy unit
     * @param column Where to search for unit
     * @param row Where to search for unit
     * @return True if attack succeeded, false if not
     */
    public final boolean Attack(int column, int row)
    {
        if(!inMapBounds(column, row)) return false;
        EntityDescriptor[] set = hex.map[column][row].getEntities();
        //get first entity belonging to other player and attack it
        for(EntityDescriptor i : set ) 
            if(! i.getPlayerColor().equals(player.getPlayerColor())) return Attack(i);
        return false;
    }

    /**
     * Initiates attack on enemy unit
     * @param coordinates Where to search for unit
     * @return True if attack succeeded, false if not
     */
    public final boolean Attack(Point coordinates)
    {
        return Attack(coordinates.x, coordinates.y);
    }

    /**
     * Initiates attack on enemy unit
     * @param direction Where to search for unit
     * @return True if attack succeeded, false if not
     */
    public final boolean Attack(Direction direction)
    {
        return Attack(direction.getNeighborCoordinates(hex.coordinates));
    }

    /**
     * Deploy function, mostly used by building series of classes
     * @param hex Where to deploy
     * @param origin From where to deploy
     * @return True if attack succeeded, false if not
     */
    public final boolean Deploy(Hex hex , Hex origin)
    {
        //Unit has not to be on the map to be deployed, and passed hex must be valid
        if(this.hex == null && hex != null && hex.CheckIfNeighbour(origin))
        {
            if(hex.AddEntity(this))
            {
                this.hex = hex;
                postActionBreak();
                return true;
            }
        }
        return false;
    }

    /**
     * Deploy function, mostly used by building series of classes
     * @param column Where to deploy
     * @param row Where to deploy
     * @param origin From where to deploy
     * @return True if attack succeeded, false if not
     */
    public final boolean Deploy(int column, int row , Hex origin)
    {
        return Deploy(hex.map[column][row],origin);
    }

    /**
     * Deploy function, mostly used by building series of classes
     * @param coordinates Where to deploy
     * @param origin From where to deploy
     * @return True if attack succeeded, false if not
     */
    public final boolean Deploy(Point coordinates , Hex origin)
    {
        return Deploy(coordinates.x, coordinates.y,origin);
    }

    /**
     * Deploy function, mostly used by building series of classes
     * @param direction Where to deploy
     * @param origin From where to deploy
     * @return True if attack succeeded, false if not
     */
    public final boolean Deploy(Direction direction , Hex origin)
    {
        return Deploy(direction.getNeighborCoordinates(hex.coordinates),origin);
    }

    /**
     * Attempts to move to provided hex
     * @param hex Where to move
     * @return True if attack succeeded, false if not
     */
    public final boolean Move(Hex hex)
    {
        if(this.hex != null && hex.CheckIfNeighbour(this.hex))
        {
            if(hex != null) hex.RemoveEntity(this);
            if(hex.AddEntity(this))
            {
                this.hex = hex;
                player.addtoLog(new MoveEntry(hex.coordinates, this, this.hex.coordinates, player, Instant.now()));
                postActionBreak();
                return true;
            }
            else this.hex.AddEntity(this);
        }
        return false;
    }

    /**
     * Attempts to move to provided hex
     * @param column Where to move
     * @param row Where to move
     * @return True if attack succeeded, false if not
     */
    public final boolean Move(int column, int row)
    {
        if(!inMapBounds(column, row)) return false;
        return Move(hex.map[column][row]);
    }

    /**
     * Attempts to move to provided hex
     * @param coordinates Where to move
     * @return True if attack succeeded, false if not
     */
    public final boolean Move(Point coordinates)
    {
        return Move(coordinates.x, coordinates.y);
    }

    /**
     * Attempts to move to provided hex
     * @param direction Where to move
     * @return True if attack succeeded, false if not
     */
    public final boolean Move(Direction direction)
    {
        return Move(direction.getNeighborCoordinates(hex.coordinates));
    }
    
    /**
     * Returns size of unit
     * @return Size of unit
     */
    @Override
    public int InitSize() {
        return 1;
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Unit";
    }
    
}
