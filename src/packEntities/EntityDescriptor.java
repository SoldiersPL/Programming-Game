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
 * Class mostly for interaction with units that Player doesn't have direct access to.
 * This might be either units belonging to enemy Player, or just ones that Player doesn't have pointer to.
 */
public final class EntityDescriptor {
    final Entity source;

    /**
     * Constructor
     * @param source Entity this object is to be link to
     */
    public EntityDescriptor(Entity source) {
        this.source = source;
    }
    private Player getPlayer() { return source.getPlayer(); }

    /**
     * @return Color of player that this entity belongs to
     */
    public Color getPlayerColor() { return getPlayer().getPlayerColor(); }

    /**
     * @return Name of player that this entity belongs to
     */
    public String getPlayerName() { return getPlayer().getPlayerName(); }

    /**
     * @return At what hex is this entity
     */
    public Hex getHex() { return source.hex; }

    /**
     * @return Name of entity's class
     */
    public String getEntityType() { return source.getClass().getSimpleName(); }

    /**
     * @return Icon representing this entity
     */
    public BufferedImage getIcon() { return source.getIlustration(); }

    /**
     * Initiate attack on this entity
     * @param entity Entity to be attacked
     */
    public void Attack(Entity entity) {entity.Attack(source);}

    /**
     * Debug command allowing access to object representing player owning this entity
     * @param password Required password
     * @return Player owning this entity
     */
    public Player getPlayer(String password)
    {
        if(password.equals("UshallNotpass")) return source.getPlayer();
        return null;
    }

    /**
     * Debug command allowing access to object this entity
     * @param password Required password
     * @return Entity linked by this object
     */
    public Entity getEntity(String password)
    {
        if(password.equals("UshallNotpass")) return source;
        return null;
    }

    /**
     * @return How many attack points this unit has
     */
    public int getAttack() {return source.getAtt();}

    /**
     * @return How many health points this unit has
     */
    public int getHp() {return source.getHp();}

    /**
     * Debug command allowing to move entity to provided hex, regardless of movement range
     * @param hex Where to move it
     * @param password Required password
     */
    public void setHex(Hex hex, String password) {source.setHex(hex, password);}
}
