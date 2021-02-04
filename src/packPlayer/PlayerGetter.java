/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer;

import java.awt.Color;
import java.security.Timestamp;
import java.time.Instant;
import packEvents.ResourcesEvent;
import packPlayer.LogTypes.playerLog.PlayerComment;

/**
 * Class for interaction with other players indirrectly
 */
public final class PlayerGetter {
    private final Player player;

    /**
     * Class constructor
     * @param player Player to be represented
     */
    public PlayerGetter(Player player) { this.player = player; }
    
    /**
     * @return Player's color
     */
    public Color getPlayerColor() { return player.getPlayerColor(); }

    /**
     * @return Player's name
     */
    public String getPlayerName() { return player.getPlayerName(); }

    /**
     * @return Player's resources
     */
    public int getResources() { return player.getResources(); }
    
    /**
     * @param listener Resource Listener to be added
     */
    public void addResourceListener(ResourcesEvent listener) { player.addListener(listener);}

    /**
     * @param listener Resource Listener to be removed
     */
    public void removeResourceListener(ResourcesEvent listener) { player.removeListener(listener); }
    
    /**
     * Method to add comment to Player's logs
     * @param comment Comment to be added
     */
    public void addCommentToLog(String comment)
    {
        Instant timestamp = Instant.now();
        PlayerComment entry = new PlayerComment(comment, player, timestamp);
        player.addtoLog(entry);
    }

    /**
     * Debug command to get direct pointer to represented player
     * @param password
     * @return
     */
    public Player getPlayer(String password)
    {
        if(password.equals("UshallNotpass")) return player;
        return null;
    }

    /**
     * @return Size of player's log
     */
    public int getLogLenght() {return player.getLog().size();}
}
