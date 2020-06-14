/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes.playerLog;

import java.time.Instant;
import packPlayer.LogTypes.logEntry;
import packPlayer.LogTypes.playerLogEntry;
import packPlayer.Player;

/**
 *
 * @author piotr
 */
public class PlayerComment extends playerLogEntry {
    String comment;

    public PlayerComment(String comment, Player player, Instant timestamp) {
        super(player, timestamp);
        this.comment = comment;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        //<html><font>Player name</font> (timestamp) -> message </html>
        sb.append(comment);
        sb.append("</html>");
        return sb.toString();
    }

    @Override
    public int compareTo(logEntry o) {
        int result = super.compareTo(o);
        if(result == 0)
        {
            if(o instanceof PlayerComment)
            {
                PlayerComment tmp = (PlayerComment)o;
                result = ((PlayerComment) o).comment.compareTo(comment);;
            }
            //else it belongs to EntityEntityBase, move it down
            else result = -1;
        }
        return result;
    }
    
}
