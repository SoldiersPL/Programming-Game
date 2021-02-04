/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEvents;

/**
 * Base interface for reacting to events connected to player's resources
 */
public interface ResourcesEvent {

    /**
     * React to event
     * @param resources How much resources does player possesed at point of event
     */
    public void react(int resources);
}
