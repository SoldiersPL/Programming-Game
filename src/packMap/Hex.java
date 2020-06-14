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
 *
 * @author user
 */
public abstract class Hex implements Serializable{
    protected final Set<Entity> entities = new HashSet();
    protected int freeSlots;
    protected int resources;
    protected Color baseColor;
    protected Color borderColor = Color.BLACK;
    protected int[] playerUnitCount = new int[12]; // amount of units of this id in a hex, crashes with using Player.maxPlayerCount for some reason
    protected ReentrantLock lock = new ReentrantLock();
    public final Hex[][] map;
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
    
    protected Hex(Hex[][] map ,Point coordinates) {
        this.coordinates = coordinates;
        this.map = map;
    }
    protected Hex(Hex[][] map ,int column, int row) {
        this.coordinates = new Point(column, row);
        this.map = map;
    }
    
    public int InitSlots() { return 0;}
    public int InitResources() { return 0; }
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
    protected void ClearEntities()
    {
        entities.clear();
        CheckFreeSlots();
        for( int i = 0 ; i < playerUnitCount.length ; ++i)
        {
            playerUnitCount[i] = 0;
        }
    }

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
    public boolean checkIfContainsEntity(Entity entity) { return this.entities.contains(entity); }
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
    
    public boolean CheckIfNeighbour(int column, int row)
    {
        return CheckIfNeighbour(new Point(column, row));
    }
    
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

    public int getFreeSlots() {
        return freeSlots;
    }

    public int getResources() {
        return resources;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    public static Set<String> getKeys() { return makeMap.keySet(); }
    public static Hex makeHex(String terrainType , Hex[][] map ,int column, int row)
    {
        return makeMap.get(terrainType).make(map, column, row);
    }
    
}
