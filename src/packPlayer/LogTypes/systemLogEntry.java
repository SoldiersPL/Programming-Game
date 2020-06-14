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

/**
 *
 * @author piotr
 */
public abstract class systemLogEntry extends logEntry{
    
    public systemLogEntry(Instant timestamp) {
        super(timestamp);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Color playerColor = Color.BLACK;
        sb.append(
                String.format("<html><font size='5' color='rgb(%d,%d,%d)'>",
                        playerColor.getRed() ,
                        playerColor.getGreen() ,
                        playerColor.getBlue()));
        sb.append("System");
        sb.append("</font>");
        DateTimeFormatter formator = DateTimeFormatter.ofPattern("hh:mm:ss").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        sb.append(String.format(" (%s) -> ", formator.format(timestamp)));
        //<html><font>Player name</font> (timestamp) -> message </html>
        return sb.toString() ;
    }
}
