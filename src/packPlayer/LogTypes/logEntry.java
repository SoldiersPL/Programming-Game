/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packPlayer.LogTypes;

import java.io.Serializable;
import java.time.Instant;

/**
 * Class serving as base for all entries made in log, later assembled in to post game raport
 * @serial 
 */
public abstract class logEntry implements Comparable<logEntry> , Serializable{

    /**
     * When was this entry made
     */
    protected final Instant timestamp;

    /**
     * Class constructor
     * @param timestamp When was it made
     */
    public logEntry(Instant timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * @return When was this entry made
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(logEntry o) {
        return timestamp.compareTo(o.getTimestamp());
    }
}
