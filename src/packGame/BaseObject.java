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
 *
 * @author piotr
 */
public abstract class BaseObject implements Runnable {
    protected PlayerGetter player;
    protected Hex[][] map;
    protected City hq;

    public void setPlayer(PlayerGetter player, String password) {
        if(password.equals("UshallNotpass")) this.player = player;
    }

    public void setMap(Hex[][] map, String password) {
        if(password.equals("UshallNotpass")) this.map = map;
    }

    public void setHq(City hq, String password) {
        if(password.equals("UshallNotpass")) this.hq = hq;
    }
    
    public abstract void run();
}
