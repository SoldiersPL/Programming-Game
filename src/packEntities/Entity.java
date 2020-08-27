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
 *
 * @author user
 */
public abstract class Entity implements Serializable{
    protected final Player player; // Player object
    protected int Hp; // Remaining health of unit
    protected int Att; // Attack of unit
    protected int size; // How many slots unit take
    protected Hex hex; // Hex that unit is currently in
    protected transient BufferedImage ilustration;
    public int getCost() { return Hp + 2*Att;} // Cost in resurces to make
    public int getSize() { return size; }
    public final int unitID;
    protected final HashSet<Event> attackedListeners = new HashSet<>();
    protected final HashSet<Event> diedListeners = new HashSet<>();
    
    public void addAttackedListener(Event listener){ attackedListeners.add(listener); }
    public void removeAttackedListener(Event listener){ attackedListeners.remove(listener); }
    public void clearAttackedListener(Event listener){ attackedListeners.clear(); }
    public void addDiedListener(Event listener){ diedListeners.add(listener); }
    public void removeDiedListener(Event listener){ diedListeners.remove(listener); }
    public void clearDiedListener(Event listener){ diedListeners.clear(); }

    public int getHp() { return Hp; }
    private void addHp(int Hp) {
        this.Hp = Hp;
        if(this.Hp <= 0) Die();
    }
    public int getAtt() { return Att; }
    
    public boolean checkHex()
    {
        if(hex != null) return hex.checkIfContainsEntity(this);
        else return true;
    }
    public Hex getHex() { return hex; } 
    public Player getPlayer() { return player; }
    protected transient ReentrantLock lock = new ReentrantLock();

    public Entity(Player player) {
        this.player = player;
        unitID = player.addEntity(this);
    }

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
        File dump = new File("dump.gif");
        try{
            ImageIO.write(image, "gif", dump);
        }
        catch(Exception e) {}
        return image;
    }
    protected final BufferedImage getIlustration(String imageLocation)
    {
        if(ilustration == null) ilustration = createIlustration(imageLocation);
        if(ilustration == null) JOptionPane.showConfirmDialog(null, "Error while reading file\n" + imageLocation);
        return ilustration;
    }
    public abstract int InitSize();
    public abstract BufferedImage getIlustration();
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException 
    {
        in.defaultReadObject();
        lock = new ReentrantLock();
    }
    public void setHex(Hex hex, String password)
    {
        if(checkHex() && password.equals("UshallNotpass")) this.hex = hex;
    }
    protected boolean inMapBounds(int column, int row)
    {
        boolean result = (0 <= column && column < hex.map.length);
        Hex[] tmp = hex.map[0];
        result = result && (0 <= row && row < tmp.length);
        return  result;
    }
    
    protected void postActionBreak()
    {
        try
        {
            Thread.sleep(5);
        }
        catch(Exception e)
        { }
    }
    
    @Override
    public String toString() {
        return "Entity";
    }

    
    
}
