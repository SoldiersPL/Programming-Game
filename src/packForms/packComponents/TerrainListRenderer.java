package packForms.packComponents;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import packMap.Hex;
import packMap.HexagonalMap;
/**
 *
 * @author mariat
 */
public class TerrainListRenderer extends JLabel implements ListCellRenderer<Hex>
{
    @Override
    public Component getListCellRendererComponent(JList<? extends Hex> list,Hex hex,int index,boolean isSelected,boolean hasFocus) 
    {
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        int hexSize = 30;
        BufferedImage img;
        Color base = hex.getBaseColor();
        Color border = hex.getBorderColor();
        img = HexagonalMap.drawHexagon(hexSize, new Point(0,0), 0, 0,hex);
        
        setIconTextGap(2);
        setIcon(new ImageIcon(img));
        setText(hex.toString());
        setOpaque(isSelected); // since what's bellow this is basically not affecting color for some reason
        if(isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        return this;
    }
}
