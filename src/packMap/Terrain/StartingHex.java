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
 * Class representing Player's starting position on map, containing the first city
 * @see Plains
 */
public class StartingHex extends Plains{
    private Player player;

    /**
     * Class Constructor
     * @param map Map this hex belongs to
     * @param coordinates Where is this Hex
     */
    public StartingHex(Hex[][] map, Point coordinates) {
        super(map, coordinates);
    }

    /**
     * Class Constructor
     * @param map Map this hex belongs to
     * @param column Where is this Hex
     * @param row Where is this Hex
     */
    public StartingHex(Hex[][] map, int column, int row) {
        super(map, column, row);
    }

    /**
     * Class Constructor
     * @param player Player that starts on this Hex
     * @param map Map this hex belongs to
     * @param coordinates Where is this Hex
     */
    public StartingHex(Player player, Hex[][] map, Point coordinates) {
        super(map, coordinates);
        this.player = player;
        initiateStart();
    }

    /**
     * Class Constructor
     * @param player Player that starts on this Hex
     * @param map Map this hex belongs to
     * @param column Where is this Hex
     * @param row Where is this Hex
     */
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

    /**
     * Method to set player for this hex
     * @param player Player that starts on this Hex
     */
    public void setPlayer(Player player)
    {
        this.player = player;
        initiateStart();
    }

    /**
     * Method to get player for this hex
     * @return Player that starts on this Hex
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Class name
     */
    @Override
    public String toString() {
        return "Starting Hex";
    }
    
}
