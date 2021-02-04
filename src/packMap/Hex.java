/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packMap;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;
import packEntities.Entity;
import packEntities.EntityDescriptor;
import packPlayer.Player;
import packMap.Terrain.*;

/**
 * Base class for all classes representing hexes / fields on the map
 * @serial 
 */
public abstract class Hex implements Serializable{

    /**
     * Set containing all entities on the hex
     */
    protected final Set<Entity> entities = new HashSet();

    /**
     * How many slots for entities does this hex have left
     */
    protected int freeSlots;

    /**
     * How many resources can be gathered per action from this hex
     */
    protected int resources;

    /**
     * What's the inner color of the hex, representing what type of terrain it is
     */
    protected Color baseColor;

    /**
     * What' the border color of the hex, representing what player got the most entities on it
     */
    protected Color borderColor = Color.BLACK;

    /**
     * Amount of units per player in the hex, used to set borderColor
     */
    protected int[] playerUnitCount = new int[12]; // amount of units of this id in a hex, crashes with using Player.maxPlayerCount for some reason

    /**
     * Lock so only one entity can attempt action on it
     */
    protected ReentrantLock lock = new ReentrantLock();

    /**
     * What map does this hex belongs to
     */
    public final Hex[][] map;

    /**
     * Coordinates of this hex
     */
    public final Point coordinates;
    
    private interface make 
    { 
        public Hex make(Hex[][] map ,int column, int row);
    }
    private static final HashMap<String, make> makeMap = new HashMap<>()
    {{
        put("Forest", (map,column,row)-> new Forest(map, column, row)); 
        put("Mountains", (map,column,row)-> new Mountains(map, column, row));
        put("Plains", (map,column,row)-> new Plains(map, column, row));
        put("Void", (map,column,row)-> new packMap.Terrain.Void(map, column, row));
        put("Starting Hex", (map,column,row)-> new packMap.Terrain.StartingHex(map, column, row));
    }};
    
    /**
     * Constructor
     * @param map What map does this hex belongs to
     * @param coordinates Where on the map is this hex
     */
    protected Hex(Hex[][] map ,Point coordinates) {
        this.coordinates = coordinates;
        this.map = map;
    }

    /**
     * Constructor
     * @param map What map does this hex belongs to
     * @param column Where on the map is this hex
     * @param row Where on the map is this hex
     */
    protected Hex(Hex[][] map ,int column, int row) {
        this.coordinates = new Point(column, row);
        this.map = map;
    }
    
    /**
     * Initialize amount of slots of this hex
     * @return How many slots
     */
    public int InitSlots() { return 0;}

    /**
     * Initialize amount of resources of this hex
     * @return How many resources per gather action
     */
    public int InitResources() { return 0; }

    /**
     * Initialize color of this hex
     * @return Color of the hex
     */
    public Color InitColor() { return null; }
    
    private void AddEntityProper(Entity entity)
    {
        entities.add(entity);
        CheckFreeSlots();
        playerUnitCount[entity.getPlayer().playerID]++;
        ChangeBorder();
    }
    private void CheckFreeSlots()
    {
        freeSlots =InitSlots();
        try{
            this.lock.lock();
            for(Entity i : entities)
            {
                freeSlots -= i.getSize();
            }
        }
        finally
        {
            this.lock.unlock();
        }
    }

    /**
     * Adds entity to the slot, entity canno't be in any other slot for this action to succeed
     * @param entity Entity to be added
     * @return True if succeeded, false if failed due to some reason
     */
    public boolean AddEntity(Entity entity)
    {
        if( entity.checkHex() ) // unit initiated movement itself or is getting deployed by building
        {
            try
            {
                lock.lock();
                if(freeSlots >= entity.getSize())
                {
                    Hex tmp = entity.getHex();
                    if(tmp==null) // unit is just getting delployed, no need to check for neighbourhood
                    {
                        AddEntityProper(entity);
                        return true; //adding was succesful
                    }
                    else
                    {
                        if(CheckIfNeighbour(tmp)) // unit is already deployed
                        {
                            tmp.RemoveEntity(entity);
                            AddEntityProper(entity);
                            return true; //adding was succesful
                        }
                    }
                }
            }
            finally
            {
                lock.unlock();
            }
        }
        return false; // Unable to add due to either lack of slot or unit not being able to move
    }

    /**
     * Removes entity from hex
     * @param entity Entity to be removed from hex
     * @return True if succeeded, false if failed due to some reason
     */
    public boolean RemoveEntity(Entity entity)
    {
        if(entity.checkHex())
        {
            try
            {
                lock.lock();
                entities.remove(entity);
                playerUnitCount[entity.getPlayer().playerID]--;
                ChangeBorder();
                return true;
            }
            finally
            {
                lock.unlock();
            }
        }
        return false;
    }

    /**
     * Clears the entities from hex
     */
    protected void ClearEntities()
    {
        entities.clear();
        CheckFreeSlots();
        for( int i = 0 ; i < playerUnitCount.length ; ++i)
        {
            playerUnitCount[i] = 0;
        }
    }

    /**
     * Get all entities from the hex
     * @return EntityDescriptors of all the entities on the hex 
     */
    public EntityDescriptor[] getEntities() {
        int size = entities.size();
        if(size > 0)
        {
            EntityDescriptor[] tmp = new EntityDescriptor[size];
            int i = 0;
            for(Entity enty : entities) { tmp[i++] = new EntityDescriptor(enty); }
            return tmp;
        }
        return null; // no entities on this hex
    }

    /**
     * Check if this hex contains provided Entity
     * @param entity Entity to be checked for
     * @return True if yes, false if no
     */
    public boolean checkIfContainsEntity(Entity entity) { return this.entities.contains(entity); }

    /**
     * Check if hex in provided coordinates is neighbour of this one or not
     * @param coordinates Hex to be checked for
     * @return True if neighbour, false if not
     */
    public boolean CheckIfNeighbour(Point coordinates)
    {
        for(Direction i : Direction.values())
        {
            //use Direction.getNeighborCoordinates to get coordinates in that direction
            Point tmp = i.getNeighborCoordinates(this.coordinates);
            if( tmp.equals(coordinates) ) return true; //if its nearby, pass true
        }
        return false; //if none is next, pass false
    }
    
    /**
     * Check if hex in provided coordinates is neighbour of this one or not
     * @param column Hex to be checked for
     * @param row Hex to be checked for
     * @return True if neighbour, false if not
     */
    public boolean CheckIfNeighbour(int column, int row)
    {
        return CheckIfNeighbour(new Point(column, row));
    }
    
    /**
     * Check if hex in provided coordinates is neighbour of this one or not
     * @param hex Hex to be checked for
     * @return True if neighbour, false if not
     */
    public boolean CheckIfNeighbour(Hex hex)
    {
        return CheckIfNeighbour(hex.coordinates);
    }
    private void ChangeBorder()
    {
        int highest = -1;
        for(int i : playerUnitCount) if(i > 0 && i > highest) highest = i;
        borderColor = highest >= 0 ? Player.getPlayerColorByID(highest) : Color.BLACK;
    }

    /**
     * @return How many slots are still free for more entities
     */
    public int getFreeSlots() {
        return freeSlots;
    }

    /**
     * @return Get how many resources can be gathered per action
     */
    public int getResources() {
        return resources;
    }

    /**
     * @return Get inner color of the hex, represenenting Hex's type
     */
    public Color getBaseColor() {
        return baseColor;
    }

    /**
     * @return Get border color of the hex, representing Player's control
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * @return Get keys for Terrain types
     */
    public static Set<String> getKeys() { return makeMap.keySet(); }

    /**
     * Method to create more hexes
     * @param terrainType What terrain is hex to be
     * @param map What map will it belong
     * @param column What's the coordinates of hex
     * @param row What's the coordinates of hex
     * @return Resulting hex
     */
    public static Hex makeHex(String terrainType , Hex[][] map ,int column, int row)
    {
        return makeMap.get(terrainType).make(map, column, row);
    }
    
}
