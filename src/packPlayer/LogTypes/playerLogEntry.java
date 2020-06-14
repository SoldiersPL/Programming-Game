/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes;

import java.awt.Color;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import packPlayer.Player;

/**
 *
 * @author piotr
 */
public abstract class playerLogEntry extends logEntry{
    protected final Player player;

    public playerLogEntry(Player player, Instant timestamp) {
        super(timestamp);
        this.player = player;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Color playerColor = player.getPlayerColor();
        sb.append(
                String.format("<html><font size='5' color='rgb(%d,%d,%d)'>",
                        playerColor.getRed() ,
                        playerColor.getGreen() ,
                        playerColor.getBlue()));
        sb.append(player.getPlayerName());
        sb.append("</font>");
        DateTimeFormatter formator = DateTimeFormatter.ofPattern("HH:mm:ss:nn").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        sb.append(String.format(" (%s) -> ", formator.format(timestamp)));
        //<html><font>Player name</font> (timestamp) -> message </html>
        return sb.toString() ;
    }

    @Override
    public int compareTo(logEntry o) {
        int result = super.compareTo(o);
        if(result == 0)
        {
            if(o instanceof playerLogEntry)
            {
                //Compare player IDs
                playerLogEntry tmp = (playerLogEntry)o;
                Integer id = tmp.player.getID();
                result = id.compareTo(player.getID());
            }
            //if its not of this class, then it belongs to system log, move it down in list
            else result = -1;
        }
        return result;
    }
    

    
    
}
