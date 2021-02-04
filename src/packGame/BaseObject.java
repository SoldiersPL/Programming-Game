/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packGame;

import packEntities.Buildings.City;
import packMap.Hex;
import packPlayer.PlayerGetter;

/**
 * Class serving as base for Player's class
 * It contains methods to allow easier start for writing of code, by already providing them with some resources
 */
public abstract class BaseObject implements Runnable {

    /**
     * Pointer to Player, for Player its mostly for access to his resources and units
     */
    protected PlayerGetter player;

    /**
     * Pointer to Map
     */
    protected Hex[][] map;

    /**
     * Pointer to Player's starting city
     */
    protected City hq;

    /**
     * Debug method to set Player to this class
     * @param player Player to be assigned to the class
     * @param password Required password
     */
    public void setPlayer(PlayerGetter player, String password) {
        if(password.equals("UshallNotpass")) this.player = player;
    }

    /**
     * Debug method to set Map to this class
     * @param map Map to be assigned to the class
     * @param password Required password
     */
    public void setMap(Hex[][] map, String password) {
        if(password.equals("UshallNotpass")) this.map = map;
    }

    /**
     * Debug method to set HQ to this class
     * @param hq HQ to be assigned to the class
     * @param password Required password
     */
    public void setHq(City hq, String password) {
        if(password.equals("UshallNotpass")) this.hq = hq;
    }
    
    /**
     * Class to be ran by the program, is to be overwriten by Player's code
     */
    public abstract void run();
}
