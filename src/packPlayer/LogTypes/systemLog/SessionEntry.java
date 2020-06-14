/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes.systemLog;

import java.time.Instant;
import packPlayer.LogTypes.logEntry;
import packPlayer.LogTypes.systemLogEntry;

/**
 *
 * @author piotr
 */
public class SessionEntry extends systemLogEntry {
    String comment;

    public SessionEntry(String comment, Instant timestamp) {
        super(timestamp);
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
            if(o instanceof SessionEntry)
            {
                //If both time and comment are exactly the same, then they are the same
                SessionEntry tmp = (SessionEntry)o;
                result = ((SessionEntry) o).comment.compareTo(comment);
            }
            //if its not of this class, then it belongs to player log, move it down in list
            else result = 1;
        }
        return result;
    }
    
}
