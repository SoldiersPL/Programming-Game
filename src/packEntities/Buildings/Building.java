/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Buildings;
import java.awt.Point;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import packEntities.Entity;
import packEntities.Units.Harvester;
import packEntities.Units.Unit;
import packEntities.Units.Warrior;
import packMap.Direction;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.DeployEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.MakeEntry;
import packPlayer.Player;

/**
 * Class representing all buildings in the game.
 * They usually are able to produce units, and posses good numbers of hp, but can't move 
 */
public abstract class Building extends Entity {

    /**
     * Hashmap that posseses all buildable units in game,
     * when creating more types of units, add units to this Hashmap
     */
    protected final static HashMap<String , make> buildableUnits = new HashMap<>(){{
        put("Warrior", (player) -> new Warrior(player));
        put("Harvester", (player) -> new Harvester(player));
    }};

    /**
     * Hashmap containing what units are buildable by this perticular class
     */
    protected transient HashMap<String , Boolean> permitedBuildableUnits = new HashMap<>();

    /**
     * Queue of units to deploy
     */
    protected transient ArrayList<Unit> queue = new ArrayList<>();

    /**
     * Constructor 
     * @param player Player that this entity belongs to
     */
    public Building(Player player) {
        super(player);
        size = InitSize();
    }

    /**
     * If player posseses enough resources, this method makes it and adds it to queue
     * @param unitType What unit type to make
     */
    public void MakeUnit(String unitType) {
        //check if this entity can create wanted unit
        boolean isPermited = permitedBuildableUnits.get(unitType) == null ? false : permitedBuildableUnits.get(unitType);
        //If its permited and player got enough resources, make it and add it to queue
        if(isPermited)
        {
            Unit unit = buildableUnits.get(unitType).make(player);
            player.removeResources(unit.getCost());
            queue.add(unit);
            player.addtoLog(new MakeEntry(unit, this, this.hex.coordinates, player, Instant.now()));
        }
        postActionBreak();
    }

    /**
     * Attempts to deploy first unit of unitType from queue to coordinates
     * @param unitType Type of unit to deploy
     * @param column Where to deploy
     * @param row Where to deploy
     * @return Returns pointer to unit or null if did not succeed for whatever reason
     */
    public Unit Deploy(String unitType , int column, int row)
    {
        Unit unit = null;
        if(!inMapBounds(column, row)) return unit;
        for(Unit i : queue)
        {
            //If not of the same type, continue
            if(! i.toString().equals(unitType)) continue;
            
            //If unit of this type is found in queue, but player don't have enough resources right now, continue
            if(i.getCost() > player.getResources()) break;
            
            //try to add, if successful, remove from queue
            if(i.Deploy(hex.map[column][row],this.hex))
            {
                unit = i;
                queue.remove(unit);
                player.addtoLog(new DeployEntry(unit, new Point(column,row), this, this.hex.coordinates, player, Instant.now()));
                break;
            }
        }
        postActionBreak();
        return unit;
    }

    /**
     * Attempts to deploy first unit from queue to coordinates
     * @param column Where to deploy
     * @param row Where to deploy
     * @return Returns pointer to unit or null if did not succeed for whatever reason
     */
    public Unit Deploy(int column, int row)
    {
        Unit unit = null;
        if(!inMapBounds(column, row)) return unit;
        for(Unit i : queue)
        {
            //Check player got enough resources to deploy this unit
            if(i.getCost() > player.getResources()) continue;
            
            //try to add, if successful, remove from queue
            if(hex.map[column][row].AddEntity(i))
            {
                unit = i;
                queue.remove(unit);
                player.addtoLog(new DeployEntry(unit, new Point(column,row), this, this.hex.coordinates, player, Instant.now()));
                break;
            }
        }
        postActionBreak();
        return unit;
    }

    /**
     * Attempts making unit of unitType type and attempts to deploy it to coordinates
     * @param unitType Type of unit to deploy
     * @param column Where to deploy
     * @param row Where to deploy
     * @return Returns pointer to unit or null if did not succeed for whatever reason
     */
    public Unit MakeAndDeploy(String unitType , int column, int row)
    {
        MakeUnit(unitType);
        return Deploy(unitType,column, row);
    }

    /**
     * Attempts making unit of unitType type and attempts to deploy it to coordinates
     * @param unitType Type of unit to deploy
     * @param coordinates Where to deploy
     * @return Returns pointer to unit or null if did not succeed for whatever reason
     */
    public Unit MakeAndDeploy(String unitType , Point coordinates)
    {
        return MakeAndDeploy(unitType, coordinates.x, coordinates.y);
    }

    /**
     * Attempts making unit of unitType type and attempts to deploy it to coordinates
     * @param unitType Type of unit to deploy
     * @param direction Where to deploy
     * @return Returns pointer to unit or null if did not succeed for whatever reason
     */
    public Unit MakeAndDeploy(String unitType , Direction direction)
    {
        return MakeAndDeploy(unitType, direction.getNeighborCoordinates(hex.coordinates));
    }

    /**
     * Returns current queue of units to deploy that this entity holds
     * @return Current queue of units to deploy that this entity holds
     */
    public ArrayList<Unit> getQueue() {return queue;} 

    /**
     * Get set of what units this entity can make
     * @return Set of unit types this entity can make
     */
    public Set<String> getBuildableUnits() { return buildableUnits.keySet();}

    /**
     * Serves to set size of this specific entity
     * @return Size of this specific entity
     */
    @Override
    public int InitSize() {
        return 4;
    }

    /**
     * Serves as base interface for make series of methods
     */
    protected interface make { 

        /**
         * Serves as base interface for make series of methods
         * @param player Player made unit will belong to
         * @return Created unit
         */
        public Unit make(Player player); }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        lock = new ReentrantLock();
        queue = new ArrayList<>();
        permitedBuildableUnits = new HashMap<>();
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Building";
    }
    
}
