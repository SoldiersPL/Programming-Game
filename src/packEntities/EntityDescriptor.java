/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEntities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import packMap.Hex;
import packPlayer.Player;

/**
 *
 * @author user
 */
public final class EntityDescriptor {
    final Entity source;

    public EntityDescriptor(Entity source) {
        this.source = source;
    }
    private Player getPlayer() { return source.getPlayer(); }
    public Color getPlayerColor() { return getPlayer().getPlayerColor(); }
    public String getPlayerName() { return getPlayer().getPlayerName(); }
    public Hex getHex() { return source.hex; }
    public String getEntityType() { return source.getClass().getSimpleName(); }
    public BufferedImage getIcon() { return source.getIlustration(); }
    public void Attack(Entity entity) {entity.Attack(source);}
    public Player getPlayer(String password)
    {
        if(password.equals("UshallNotpass")) return source.getPlayer();
        return null;
    }
    public Entity getEntity(String password)
    {
        if(password.equals("UshallNotpass")) return source;
        return null;
    }
    public int getAttack() {return source.getAtt();}
    public int getHp() {return source.getHp();}
    public void setHex(Hex hex, String password) {source.setHex(hex, password);}
}
