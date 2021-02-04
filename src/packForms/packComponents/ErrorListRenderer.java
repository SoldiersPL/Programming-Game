/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packForms.packComponents;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Renderer for list of errors made during Player's code compilaton
 */
public class ErrorListRenderer  extends JLabel implements ListCellRenderer<String> {

    /**
     * @param list List to be rendered
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value);
        return this;
    }
    
}
