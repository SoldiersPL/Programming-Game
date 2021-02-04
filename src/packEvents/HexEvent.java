/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEvents;

import packEntities.EntityDescriptor;
import packMap.Hex;

/**
 * Base interface for reacting to events connected to hex
 */
public interface HexEvent {

    /**
     * React to event
     * @param hex Hex at where it happened
     * @param entity Entity causing the event
     */
    public void react(Hex hex,EntityDescriptor entity);
}
