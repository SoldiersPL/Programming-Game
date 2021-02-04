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
 * Class representing in-game Player
 * @serial 
 */

public class Player implements Serializable {

    /**
     * Player's ID
     */
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

    /**
     * Enumerator serving as Player's colors
     * @see #playerColorsDispenser
     */

    public static enum playerColors {

        /**
         * @see #playerColorsDispenser
         */
        RED,

        /**
         * @see #playerColorsDispenser
         */
        BLUE ,

        /**
         * @see #playerColorsDispenser
         */
        TEAL,

        /**
         * @see #playerColorsDispenser
         */
        PURPLE,

        /**
         * @see #playerColorsDispenser
         */
        YELLOW,

        /**
         * @see #playerColorsDispenser
         */
        ORANGE,

        /**
         * @see #playerColorsDispenser
         */
        GREEN,

        /**
         * @see #playerColorsDispenser
         */
        PINK,

        /**
         * @see #playerColorsDispenser
         */
        GRAY,

        /**
         * @see #playerColorsDispenser
         */
        LIGHT_BLUE,

        /**
         * @see #playerColorsDispenser
         */
        DARK_GREEN,

        /**
         * @see #playerColorsDispenser
         */
        BROWN
    }

    /**
     * Hashmap allowing for conversion from provided player colors in PlayerColors in to colors that will be displayed in-game 
     */
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

    /**
     * Method of getting color by Player's ID
     * @param id Player's ID
     * @return Provided Player's color
     * @see #playerColorsDispenser
     */
    public static Color getPlayerColorByID(int id)
    {
        return playerColorsDispenser.get(playerColors.values()[id]);
    }

    /**
     * Method of getting Player's ID from his color
     * @param color Player's color
     * @return Player's ID
     */
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

    /**
     * Add entry to Player's log
     * @param entry Entry to be added
     */
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

    /**
     * @return Player's log
     */
    public final ArrayList<playerLogEntry> getLog()
    {
        return (ArrayList<playerLogEntry>)log.clone();
    }
    
    private int resources;
    
    /**
     * How many players there can be at maximum, based on amount of colors
     */
    public final static int maxPlayerCount = playerColorsDispenser.size();
    
    private final List<ResourcesEvent> listeners = new ArrayList<ResourcesEvent>();
    
    private final Set<Entity> units = new LinkedHashSet<>();
    private int nextID = 0;
    
    /**
     * Class constructor
     * @param playerColor What color will Player be
     * @param playerName What is Player's name
     * @param playerID What will be his ID
     */
    public Player(Color playerColor, String playerName, int playerID) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.playerID = playerID;
    }

    /**
     * Class constructor
     * @param playerName What is Player's name
     * @param playerID What will be his ID
     */
    public Player(String playerName, int playerID) {
        this.playerColor = getPlayerColorByID(playerID);
        this.playerName = playerName;
        this.playerID = playerID;
    }

    /**
     * Class constructor
     * @param playerColor What color will Player be
     * @param playerName What is Player's name
     * @param playerID What will be his ID
     * @param resources How many resources does he have
     */
    public Player(Color playerColor, String playerName, int playerID, int resources) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.playerID = playerID;
        this.resources = resources;
    }

    /**
     * @param resources How many resources to set to
     */
    public void setResources(int resources) 
    { 
        this.resources = resources;
        for(ResourcesEvent i : listeners) i.react(resources);
    }

    /**
     * @param resources How many resources to add
     */
    public void addResources(int resources) { setResources(this.resources + resources); }

    /**
     * @param resources How many resources to remove
     */
    public void removeResources(int resources) { setResources(this.resources - resources); }

    /**
     * @return Player's color
     */
    public Color getPlayerColor() { return playerColor; }

    /**
     * @return Player's name
     */
    public String getPlayerName() { return playerName; }

    /**
     * @return Player's resources
     */
    public int getResources() { return resources; }    
    
    /**
     * @param listener Resource Listener to be added
     */
    public void addListener(ResourcesEvent listener) { listeners.add(listener); }

    /**
     * Add entity to Player's list
     * @param entity Entity to be added
     * @return ID of next entity
     */
    public int addEntity(Entity entity ) {
        units.add(entity);
        return nextID++;
    }

    /**
     * @param listener Resource Listener to be removed
     */
    public void removeListener(ResourcesEvent listener) { listeners.remove(listener); }

    /**
     * @return Player's ID
     */
    public int getID() {return playerID;}
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        log = new ArrayList<>();
        logLock = new ReentrantLock();
    }

    /**
     * Debug method to completely reset all game related parameters of this Player
     * @param password Required password
     */
    public void reset(String password)
    {
        if(password.equals("UshallNotpass"))
        {
            log.clear();
            units.clear();
            listeners.clear();
            
        }
    }

    /**
     * Debug method to completely remove entity from Player's list
     * @param password Required password
     * @param entity Entity to be removed
     */
    public void removeUnit(String password, Entity entity)
    {
        if(password.equals("UshallNotpass")) units.remove(entity);
    }
}