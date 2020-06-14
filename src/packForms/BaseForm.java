/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packForms;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author piotr
 */
public class BaseForm extends JFrame {
    private JFrame prev;

    //Without prev in case that there is no parent, like for Main Menu
    public BaseForm() throws HeadlessException {
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(GraphicsConfiguration gc) {
        super(gc);
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(String title) throws HeadlessException {
        super(title);
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.setMinimumSize(new Dimension(500, 300));
    }

    //with prev so you can go back
    public BaseForm(JFrame prev) throws HeadlessException {
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(JFrame prev, GraphicsConfiguration gc) {
        super(gc);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(JFrame prev, String title) throws HeadlessException {
        super(title);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    public BaseForm(JFrame prev, String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }
    public void Replace(JFrame frame)
    {
        frame.setEnabled(true);
        frame.setLocation(this.getLocation());
        frame.setSize(this.getSize());
        this.setVisible(false);
        frame.setVisible(true);
    }
    public JFrame Back()
    {
        prev.setEnabled(true);
        prev.setLocation(this.getLocation());
        prev.setSize(this.getSize());
        this.setVisible(false);
        prev.setVisible(true);
        return this;
    }
}
