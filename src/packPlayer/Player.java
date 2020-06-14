package packPlayer;


import packPlayer.LogTypes.playerLogEntry;
import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import packEntities.Entity;
import packEvents.ResourcesEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */

public class Player implements Serializable {
    public final int playerID;
    private final Color playerColor;
    /*
    RED FF0303
    BLUE 0042FF
    TEAL 1CE6B9
    PURPLE 540081
    YELLOW FFFC00
    ORANGE FE8A0E
    GREEN 20C000
    PINKE 55BB0
    GRAY 959697
    LIGHT BLUE 7EBFF1
    DARK GREEN 106246
    BROWN 4A2A04
    */
    public static enum playerColors {
        RED, BLUE , TEAL, PURPLE, YELLOW, ORANGE, GREEN, PINK, GRAY, LIGHT_BLUE, DARK_GREEN, BROWN
    }
    public final static HashMap<playerColors , Color> playerColorsDispenser = new HashMap<>(){{
        put(playerColors.RED , Color.decode("#FF0303"));
        put(playerColors.BLUE , Color.decode("#0042FF"));
        put(playerColors.TEAL , Color.decode("#1CE6B9"));
        put(playerColors.PURPLE , Color.decode("#540081"));
        put(playerColors.YELLOW , Color.decode("#FFFC00"));
        put(playerColors.ORANGE , Color.decode("#FE8A0E"));
        put(playerColors.GREEN , Color.decode("#20C000"));
        put(playerColors.PINK , Color.decode("#55BB0"));
        put(playerColors.GRAY , Color.decode("#959697"));
        put(playerColors.LIGHT_BLUE , Color.decode("#7EBFF1"));
        put(playerColors.DARK_GREEN , Color.decode("#106246"));
        put(playerColors.BROWN , Color.decode("#4A2A04"));
    }};
    public static Color getPlayerColorByID(int id)
    {
        return playerColorsDispenser.get(playerColors.values()[id]);
    }
    public static int getIDbyPlayerColor(Color color)
    {
        Map<Color, playerColors> inversed = playerColorsDispenser.
                entrySet().
                stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return inversed.get(color).ordinal();
    }
    private final String playerName;
    private transient ArrayList<playerLogEntry> log = new ArrayList<>();
    private transient ReentrantLock logLock = new ReentrantLock();
    public final void addtoLog(playerLogEntry entry)
    {
        try
        {
            logLock.lock();
            log.add(entry);
        }
        finally
        {
            logLock.unlock();
        }
    }
    public final ArrayList<playerLogEntry> getLog()
    {
        return (ArrayList<playerLogEntry>)log.clone();
    }
    
    private int resources;
    
    public final static int maxPlayerCount = playerColorsDispenser.size();
    
    private final List<ResourcesEvent> listeners = new ArrayList<ResourcesEvent>();
    
    private final Set<Entity> units = new LinkedHashSet<>();
    private int nextID = 0;
    

    public Player(Color playerColor, String playerName, int playerID) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.playerID = playerID;
    }
    public Player(String playerName, int playerID) {
        this.playerColor = getPlayerColorByID(playerID);
        this.playerName = playerName;
        this.playerID = playerID;
    }

    public Player(Color playerColor, String playerName, int playerID, int resources) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.playerID = playerID;
        this.resources = resources;
    }

    public void setResources(int resources) 
    { 
        this.resources = resources;
        for(ResourcesEvent i : listeners) i.react(resources);
    }
    public void addResources(int resources) { setResources(this.resources + resources); }
    public void removeResources(int resources) { setResources(this.resources - resources); }
    public Color getPlayerColor() { return playerColor; }
    public String getPlayerName() { return playerName; }

    public int getResources() { return resources; }    
    
    public void addListener(ResourcesEvent listener) { listeners.add(listener); }
    public int addEntity(Entity entity ) {
        units.add(entity);
        return nextID++;
    }
    public void removeListener(ResourcesEvent listener) { listeners.remove(listener); }
    public int getID() {return playerID;}
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        log = new ArrayList<>();
        logLock = new ReentrantLock();
    }
    public void reset(String password)
    {
        if(password.equals("UshallNotpass"))
        {
            log.clear();
            units.clear();
            listeners.clear();
            
        }
    }
    public void removeUnit(String password, Entity entity)
    {
        if(password.equals("UshallNotpass")) units.remove(entity);
    }
}