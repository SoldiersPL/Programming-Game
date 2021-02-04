/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities.Units;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import packEntities.Buildings.Building;
import packEntities.Buildings.Castle;
import packEntities.Buildings.City;
import packEntities.EntityDescriptor;
import packEvents.Event;
import packMap.Direction;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.HarvestEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.MakeEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.ReturnHarvestEntry;
import packPlayer.Player;

/**
 * Class representing Harvester type of unit.
 * It can harvest resources from different hexes on map and can construct buildings, fairy weak in combat
 */
public class Harvester extends Unit {

    private int resources;
    private final int maxResources = 20;

    /**
     * Hashmap that posseses all buildable buildings in game,
     * when creating more types of units, add units to this Hashmap
     */
    protected final static HashMap<String , make> buildableUnits = new HashMap<>(){{
        put("City", (player) -> new City(player));
        put("Castle", (player) -> new Castle(player));
    }};

    /**
     * Serves as base interface for make series of methods
     */
    protected interface make { 

        /**
         * Serves as base interface for make series of methods
         * @param player Player made unit will belong to
         * @return Created unit
         */
        public Building make(Player player); }
    
    /**
     * Set of added listeners for reaction when unit is at limit of resources
     */
    protected final HashSet<Event> fullListeners = new HashSet<>();
    
    /**
     * Adds listener to the list in case that unit is at limit of resources it can gather
     * @param listener Listener to be added
     */
    public void addFullListener(Event listener){ fullListeners.add(listener); }

    /**
     * Removes listener from the list in case that unit is at limit of resources it can gather
     * @param listener Listener to be added
     */
    public void removeFullListener(Event listener){ fullListeners.remove(listener); }

    /**
     * Cleans the set of listeners
     */
    public void clearFullListener(){ fullListeners.clear(); }
    
    /**
     * Constructor
     * @param player Player that this entity belongs to 
     */
    public Harvester(Player player) {
        super(player);
        Hp = 5;
        Att = 1;
    }

    /**
     * Harvests resources from hex this unit is on and adds it to this unit
     * @return How much resources have been harvested
     */
    public final int Harvest()
    {
        if(Hp <= 0 || hex == null) return 0;
        Instant timestamp = Instant.now();
        player.addtoLog(new HarvestEntry(this, this.hex.coordinates, player, timestamp));
        int harvestAmount = hex.getResources();
        if(resources + harvestAmount <= maxResources) resources += harvestAmount;
        else
        {
            resources = maxResources;
            for(Event i : fullListeners) i.react(this);
        }
        postActionBreak();
        return resources;
    }

    /**
     * Attempts returning resources to player, has to be on same hex as one of friendly buildings 
     * @return True if succeeded, false if failed due to lack of friendly building
     */
    public final boolean ReturnHarvest()
    {
        if(Hp <= 0 || hex == null) return false;
        EntityDescriptor[] entities = hex.getEntities();
        String name;
        for(EntityDescriptor enty : entities)
        {
            name = enty.getEntityType();
            // check if hex contains one of production buildings
            if(name.equals(City.class.getSimpleName()) ||
               name.equals(Castle.class.getSimpleName())) 
            {
                //check if it actually belongs to player
                if(enty.getPlayerColor().equals(player.getPlayerColor()))
                {
                    player.addResources(resources); // add resources to player
                    Instant timestamp = Instant.now();
                    player.addtoLog(new ReturnHarvestEntry(this, this.hex.coordinates, player, timestamp));
                    resources = 0; // set harvester's resource count to 0
                    postActionBreak();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Attempt constructing a unitType building in provided coordinates
     * @param unitType What building to construct
     * @param column Where to make building
     * @param row Where to make building
     * @return True if succeeded, false if failed due one of various reasons
     */
    public Building Construct(String unitType , int column, int row)
    {
        Building building = null;
        if(!inMapBounds(column, row)) return building;
        building = buildableUnits.get(unitType).make(player);
        if(building.getSize() > hex.map[column][row].getFreeSlots() || building.getCost() > player.getResources())
        {
            player.removeUnit("UshallNotpass", building);
            building = null;
        }
        else
        {
            if(hex.map[column][row].AddEntity(building))
                player.addtoLog(new MakeEntry(building, this, this.hex.coordinates, player, Instant.now()));
            else building = null;
        }
        return building;
    }

    /**
     * Attempt constructing a unitType building in provided coordinates
     * @param unitType What building to construct
     * @param coordinates Where to make building
     * @return True if succeeded, false if failed due one of various reasons
     */
    public Building Construct(String unitType , Point coordinates)
    {
        return Construct(unitType, coordinates.x, coordinates.y);
    }

    /**
     * Attempt constructing a unitType building in provided coordinates
     * @param buildingType What building to construct
     * @param direction Where to make building
     * @return True if succeeded, false if failed due one of various reasons
     */
    public Building Construct(String buildingType , Direction direction)
    {
        return Construct(buildingType, direction.getNeighborCoordinates(hex.coordinates));
    }

    /**
     * Generates ilustration for this unit
     * @return Image representing this unit on map
     */
    @Override
    public BufferedImage getIlustration() {
        return getIlustration("pickaxe.png");
    }

    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Harvester";
    }
}
