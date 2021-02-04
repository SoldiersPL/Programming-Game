/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEvents;

import packEntities.Entity;

/**
 * Base interface for reacting to miscellaneous events
 */
public interface Event {

    /**
     * React to event
     * @param entity Entity to be source of event
     */
    public void react(Entity entity);
}
