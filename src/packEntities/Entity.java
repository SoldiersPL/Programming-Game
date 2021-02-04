/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import packEvents.Event;
import packMap.Hex;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.AttackEntry;
import packPlayer.LogTypes.playerLog.EntityEntryTypes.DiedEntry;
import packPlayer.Player;

/**
 * Class representing all units belonging to players
 * @serial 
 */
public abstract class Entity implements Serializable{

    /**
     * What player it belongs to
     */
    protected final Player player; // Player object

    /**
     * Remaining health of unit
     */
    protected int Hp;

    /**
     * Attack points of unit
     */
    protected int Att;

    /**
     * How many slots in hex does unit take
     */
    protected int size;

    /**
     * Hex that unit is currently in
     */
    protected Hex hex;

    /**
     * Image representing this unit
     */
    protected transient BufferedImage ilustration;

    /**
     * Returns how much would making this unit cost
     * @return Cost of production
     */
    public int getCost() { return Hp + 2*Att;} // Cost in resurces to make

    /**
     * @return Size of unit
     */
    public int getSize() { return size; }

    /**
     * ID of unit
     */
    public final int unitID;

    /**
     * Set of added listeners for reaction when unit is attacked
     */
    protected final HashSet<Event> attackedListeners = new HashSet<>();

    /**
     * Set of added listeners for reaction when unit had died
     */
    protected final HashSet<Event> diedListeners = new HashSet<>();
    
    /**
     * Adds listener to the list in case that unit is attacked
     * @param listener Listener to be added
     */
    public void addAttackedListener(Event listener){ attackedListeners.add(listener); }

    /**
     * Removes listener from the list in case that unit is attacked
     * @param listener Listener to be removed
     */
    public void removeAttackedListener(Event listener){ attackedListeners.remove(listener); }

    /**
     * Cleans set of listeners
     */
    public void clearAttackedListener(){ attackedListeners.clear(); }

    /**
     * Adds listener to the list in case that unit has died
     * @param listener Listener to be removed
     */
    public void addDiedListener(Event listener){ diedListeners.add(listener); }

    /**
     * Removes listener from the list in case that unit has died
     * @param listener Listener to be removed
     */
    public void removeDiedListener(Event listener){ diedListeners.remove(listener); }

    /**
     * Cleans set of listeners
     */
    public void clearDiedListener(){ diedListeners.clear(); }

    /**
     * Returns how much hp does this unit still have
     * @return Remaining Hp
     */
    public int getHp() { return Hp; }
    private void addHp(int Hp) {
        this.Hp = Hp;
        if(this.Hp <= 0) Die();
    }

    /**
     * Returns how many attack points does this unit have
     * @return Attack points
     */
    public int getAtt() { return Att; }
    
    /**
     * Check if unit is assigned to hex
     * @return True if unit is not assigned to hex, false if it is
     */
    public boolean checkHex()
    {
        if(hex != null) return hex.checkIfContainsEntity(this);
        else return true;
    }

    /**
     * Gets hex this unit is currently assigned to
     * @return Hex this unit is currently assigned to
     */
    public Hex getHex() { return hex; } 

    /**
     * Get player this unit belongs to
     * @return Player this unit belongs to
     */
    public Player getPlayer() { return player; }

    /**
     * Lock used so that this unit can attempt only one action at once
     */
    protected transient ReentrantLock lock = new ReentrantLock();

    /**
     * Constructor
     * @param player Player this unit belongs to
     */
    public Entity(Player player) {
        this.player = player;
        unitID = player.addEntity(this);
    }

    /**
     * Loop of attacks, once started, it will continue unless one of units dies or goes out of range
     * @param entity Entity to attack
     */
    public final void Attack(Entity entity) { //attack loops around between units until one of them goes out of range or dies
        // if unit is considered dead, dont act, safeguard in case player keeps the object
        if(Hp <= 0) return;
        // if its unit of same player, then don't attack
        if(this.player == entity.player) return; 
        if (hex.CheckIfNeighbour(entity.hex))
        {
            Instant timestamp = Instant.now();
            player.addtoLog(new AttackEntry(entity, entity.hex.coordinates, this, this.hex.coordinates, player, timestamp));
            entity.addHp(-Att);
            entity.Attack(this);
            for(Event i : attackedListeners) i.react(this);
        }
        postActionBreak();
    }
    
    /**
     * Unit dies, thus is removed from play
     */
    public final void Die()
    {
        if(Hp != 0)
        {
            Instant timestamp = Instant.now();
            player.addtoLog(new DiedEntry(this, hex.coordinates, player, timestamp));
            for(Event i : diedListeners) i.react(this); 
        }
        Hp = 0;
        hex.RemoveEntity(this);
    }

    /**
     * Creates image by taking appopriate from Icons folder and then recolloring black pixels to Player colors
     * @param imageLocation Path to base image's location, including image name and extension
     * @return Image representing unit on map in Player's colors
     */
    protected final BufferedImage createIlustration(String imageLocation)
    {
        BufferedImage image;
        try{
            URL path = getClass().getResource("Icons/"+imageLocation);
            image = ImageIO.read(path);
        }
        catch(IOException e)
        {
            System.out.print(imageLocation + "was not found");
            return null;
        }
        //byte[] rgbRaster = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int width = image.getWidth() , height = image.getHeight();
        short[][] replaceTable = new short[height][width]; // to operate without actualy changing anything in image
        // 0 - white
        // 1 - black on border
        // 2 - black inside
        for(int h = 0; h < height ; ++h)
        {
            for(int w = 0 ; w < width ; ++w )
            {
                if(image.getRGB(w,h) == 0xFF000000)
                {
                    if( (w-1 > 0 && image.getRGB(w - 1,h) != 0xFF000000) ||
                        (w+1 < image.getHeight() && image.getRGB(w + 1,h) != 0xFF000000) ||
                        (h-1 > 0 && image.getRGB(w,h - 1) != 0xFF000000) ||
                        (h+1 < image.getHeight() && image.getRGB(w,h + 1) != 0xFF000000)) replaceTable[h][w] = 1; // if one of surrounding pixels is white, leave it black to create border
                    else replaceTable[h][w] = 2; // if not, change to player's color
                }
                else replaceTable[h][w] = 0; // if pixel is white, change to transparent
            }
        }
        
        int playerColor = player.getPlayerColor().getRGB();
        int transparent = 0; // 0 alpha + 0 red + 0 green + 0 blue
        for(int h = 0; h < image.getHeight() ; ++h)
        {
            for(int w = 0 ; w < image.getWidth() ; ++w )
            {
                if(replaceTable[h][w] == 0) image.setRGB(w, h, transparent);
                else if(replaceTable[h][w] == 2) image.setRGB(w, h, playerColor);
            }
        }
       
        //more cost effective method, but needs more work to actually work
        /*for(int i = 0; i < rgbRaster.length ; ++i)
        {
            //0xFF000000 = pure black with no alpha
            rgbRaster[i] = rgbRaster[i] > 0xFF000000 ? transparent : unitColor;
        }*/
        /*
        // Saves resulting image in folder for debug porpuses
        File dump = new File("dump.gif");
        try{
            ImageIO.write(image, "gif", dump);
        }
        catch(Exception e) {}
        */
        return image;
    }

    /**
     * Gets image representing unit on map, or creates it if it doesn't exist yet
     * @param imageLocation Path to base image's location, including image name and extension
     * @return Image representing unit on map in Player's colors
     */
    protected final BufferedImage getIlustration(String imageLocation)
    {
        if(ilustration == null) ilustration = createIlustration(imageLocation);
        if(ilustration == null) JOptionPane.showConfirmDialog(null, "Error while reading file\n" + imageLocation);
        return ilustration;
    }

    /**
     * @return Unit size
     */
    public abstract int InitSize();

    /**
     * Method used by EntityDescriptor class to get image representing unit
     * @return Image representing unit
     */
    public abstract BufferedImage getIlustration();
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        lock = new ReentrantLock();
    }

    /**
     * Debug command to set unit to specific hex regardless of movement range
     * @param hex Hex to set unit to
     * @param password Required password
     */
    public void setHex(Hex hex, String password)
    {
        if(checkHex() && password.equals("UshallNotpass")) this.hex = hex;
    }

    /**
     * Method to check if unit is about to walk / attack outside of the map
     * @param column Coordinates to check
     * @param row Coordinates to check
     * @return True means that that it is within the map's bounds, false means its not
     */
    protected boolean inMapBounds(int column, int row)
    {
        boolean result = (0 <= column && column < hex.map.length);
        Hex[] tmp = hex.map[0];
        result = result && (0 <= row && row < tmp.length);
        return  result;
    }
    
    /**
     * Method occuring after every action taken by entity, pausing code's execution for greater raport clarity
     */
    protected void postActionBreak()
    {
        try
        {
            Thread.sleep(5);
        }
        catch(Exception e)
        { }
    }
    
    /**
     * @return Unit name
     */
    @Override
    public String toString() {
        return "Entity";
    }

    
    
}
