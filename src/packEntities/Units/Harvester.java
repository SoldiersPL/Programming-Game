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
import packEntities.Buildings.Building;
import packEntities.Buildings.Castle;
import packEntities.Buildings.City;
import packEntities.EntityDescriptor;
import packMap.Direction;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.HarvestEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.MakeEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.ReturnHarvestEntry;
import packPlayer.Player;

/**
 *
 * @author user
 */
public class Harvester extends Unit {

    private int resources;
    private final int maxResources = 20;
    protected final static HashMap<String , make> buildableUnits = new HashMap<>(){{
        put("City", (player) -> new City(player));
        put("Castle", (player) -> new Castle(player));
    }};
    protected interface make { public Building make(Player player); }
    
    public Harvester(Player player) {
        super(player);
        Hp = 5;
        Att = 1;
    }
    public final int Harvest()
    {
        if(Hp <= 0 || hex == null) return 0;
        Instant timestamp = Instant.now();
        player.addtoLog(new HarvestEntry(this, this.hex.coordinates, player, timestamp));
        int harvestAmount = hex.getResources();
        if(resources + harvestAmount <= maxResources) resources += harvestAmount;
        postActionBreak();
        return resources;
    }
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
    public Building Construct(String unitType , Point coordinates)
    {
        return Construct(unitType, coordinates.x, coordinates.y);
    }
    public Building Construct(String buildingType , Direction direction)
    {
        return Construct(buildingType, direction.getNeighborCoordinates(hex.coordinates));
    }

    @Override
    public BufferedImage getIlustration() {
        return getIlustration("pickaxe.png");
    }
    @Override
    public String toString() {
        return "Harvester";
    }
}
