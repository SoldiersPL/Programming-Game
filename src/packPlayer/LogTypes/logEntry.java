/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes;

import java.io.Serializable;
import java.time.Instant;

/**
 *
 * @author piotr
 */
public abstract class logEntry implements Comparable<logEntry> , Serializable{
    protected final Instant timestamp;
    public logEntry(Instant timestamp)
    {
        this.timestamp = timestamp;
    }
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(logEntry o) {
        return timestamp.compareTo(o.getTimestamp());
    }
}
