/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packGame;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import packForms.packComponents.PlayerSlot;
import packPlayer.LogTypes.logEntry;
import packPlayer.LogTypes.playerLogEntry;
import packPlayer.LogTypes.systemLog.SessionEntry;

/**
 * Class responsible for running Player's code, keeping up the set rules and assembling raports
 */
public class Session {
    private final int timeLimit;
    private final LinkedHashSet<PlayerSlot> slots;
    private final LinkedHashSet<Object> codes;
    private final TreeSet<logEntry> raport = new TreeSet<>();;

    /**
     * Class constructor
     * @param timeLimit How long will this round be
     * @param slots How many players will there be
     * @param codes Player's codes
     */
    public Session(int timeLimit, LinkedHashSet<PlayerSlot> slots, LinkedHashSet<Object> codes) {
        this.timeLimit = timeLimit;
        this.slots = slots;
        this.codes = codes;
    }
    
    private void fillInRaport()
    {
        
        for(PlayerSlot i : slots)
        {
            ArrayList<playerLogEntry> log = i.getPlayer().getLog();
            raport.addAll(log);
        }
    }
    synchronized private void systemRaport(String comment)
    {
        raport.add(new SessionEntry(comment, Instant.now()));
    }
    
    /**
     * Method to start the match
     */
    public void run()
    {
        //Preparations for running the code
        Thread[] th = new Thread[slots.size()];
        int i = 0;
        for(Object cd : codes)
        {
            Runnable runner = null;
            //Since i can't force compiler to percieve compiled code as extension of BaseObject.java, so its not runneable
            runner = new Runnable() {
                @Override
                public void run() {
                    try{
                        cd.getClass().getMethod("run",(Class[])null).invoke(cd,(Object[])null);
                    }
                    catch(Exception e)
                    {
                        systemRaport(e.toString());
                        System.out.append(e.toString());
                        e.printStackTrace();
                        System.out.append("Cause");
                        System.out.append(e.getCause().toString());
                    }
                }
            };
            th[i++] = new Thread(runner);
        }
        for(Thread j : th) j.start();
        try
        {
            Thread.sleep(timeLimit * 1000);
        }
        catch(Exception e)
        {
            systemRaport(e.toString());
        }
        // stop all the threads
        boolean repeat = false;
        for(int j = 0 ; j < th.length ; ++j)
        {
            //don't bother with already stopped threads
            if(th[j].isInterrupted()) continue;
            th[j].interrupt(); 
            //if one of threads did not stop for whatever reason, loop through again
            if(!th[j].isInterrupted())
            {
                repeat = true;
            }
            if(j == th.length && repeat)
            {
                j = 0;
            }
        }
        fillInRaport();
    }

    /**
     * Get compiled raports created after using Run method
     * @return Compiled raports from session
     */
    public TreeSet<logEntry> getRaport() {
        return raport;
    }
    
}
