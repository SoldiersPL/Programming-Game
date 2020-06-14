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
 *
 * @author user
 */
public abstract class Unit extends Entity {
    
    public Unit(Player player) {
        super(player);
        size = InitSize();
    }
    public final boolean Attack(EntityDescriptor entity)
    {
        if(Hp <= 0) return false; // dead units can't attack
        if(! entity.getHex().CheckIfNeighbour(hex)) return false; // units have to be at least in neighbouring hex
        entity.Attack(this); // since user can't access other player's units
        postActionBreak();
        return true;
    }
    public final boolean Attack(int column, int row)
    {
        if(!inMapBounds(column, row)) return false;
        EntityDescriptor[] set = hex.map[column][row].getEntities();
        //get first entity belonging to other player and attack it
        for(EntityDescriptor i : set ) 
            if(! i.getPlayerColor().equals(player.getPlayerColor())) return Attack(i);
        return false;
    }
    public final boolean Attack(Point coordinates)
    {
        return Attack(coordinates.x, coordinates.y);
    }
    public final boolean Attack(Direction direction)
    {
        return Attack(direction.getNeighborCoordinates(hex.coordinates));
    }
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
    public final boolean Deploy(int column, int row , Hex origin)
    {
        return Deploy(hex.map[column][row],origin);
    }
    public final boolean Deploy(Point coordinates , Hex origin)
    {
        return Deploy(coordinates.x, coordinates.y,origin);
    }
    public final boolean Deploy(Direction direction , Hex origin)
    {
        return Deploy(direction.getNeighborCoordinates(hex.coordinates),origin);
    }
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
    public final boolean Move(int column, int row)
    {
        if(!inMapBounds(column, row)) return false;
        return Move(hex.map[column][row]);
    }
    public final boolean Move(Point coordinates)
    {
        return Move(coordinates.x, coordinates.y);
    }
    public final boolean Move(Direction direction)
    {
        return Move(direction.getNeighborCoordinates(hex.coordinates));
    }
    
    @Override
    public int InitSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "Unit";
    }
    
}
