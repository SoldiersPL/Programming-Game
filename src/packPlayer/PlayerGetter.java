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
 *
 * @author user
 */
public final class PlayerGetter {
    private final Player player;

    public PlayerGetter(Player player) { this.player = player; }
    
    public Color getPlayerColor() { return player.getPlayerColor(); }
    public String getPlayerName() { return player.getPlayerName(); }
    public int getResources() { return player.getResources(); }
    
    public void addResourceListener(ResourcesEvent listener) { player.addListener(listener);}
    public void removeResourceListener(ResourcesEvent listener) { player.removeListener(listener); }
    
    public void addCommentToLog(String comment)
    {
        Instant timestamp = Instant.now();
        PlayerComment entry = new PlayerComment(comment, player, timestamp);
        player.addtoLog(entry);
    }
    public Player getPlayer(String password)
    {
        if(password.equals("UshallNotpass")) return player;
        return null;
    }
    public int getLogLenght() {return player.getLog().size();}
}
