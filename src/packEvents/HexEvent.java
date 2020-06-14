/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packEvents;

import packEntities.EntityDescriptor;
import packMap.Hex;

/**
 *
 * @author user
 */
public interface HexEvent {
    public void react(Hex hex,EntityDescriptor entity);
}
