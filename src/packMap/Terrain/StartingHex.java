/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packMap.Terrain;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JOptionPane;
import packMap.Hex;
import packEntities.Buildings.City;
import packPlayer.Player;

/**
 *
 * @author piotr
 */
public class StartingHex extends Plains{
    private Player player;

    public StartingHex(Hex[][] map, Point coordinates) {
        super(map, coordinates);
    }

    public StartingHex(Hex[][] map, int column, int row) {
        super(map, column, row);
    }

    public StartingHex(Player player, Hex[][] map, Point coordinates) {
        super(map, coordinates);
        this.player = player;
        initiateStart();
    }

    public StartingHex(Player player, Hex[][] map, int column, int row) {
        super(map, column, row);
        this.player = player;
        initiateStart();
    }
    
    
    private void initiateStart()
    {
        if(player == null)
        {
            JOptionPane.showMessageDialog(null, "Player is null, stopping", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        player.reset("UshallNotpass");
        City HQ = new City(player);
        ClearEntities();
        AddEntity(HQ);
    }
    private Color getPlayerColor()
    {
        return player.getPlayerColor();
    }
    public void setPlayer(Player player)
    {
        this.player = player;
        initiateStart();
    }

    public Player getPlayer() {
        return player;
    }
    @Override
    public String toString() {
        return "Starting Hex";
    }
    
}
