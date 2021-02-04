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
 * Form serving as base for other custom forms, like LobbyForm
 * Its main purpose is to allow easier transition from Form to Form, by sharing sizes and such
 */
public class BaseForm extends JFrame {
    private JFrame prev;

    //Without prev in case that there is no parent, like for Main Menu

    /**
     * @see javax.swing.JFrame#JFrame() 
     */
    public BaseForm() throws HeadlessException {
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.awt.GraphicsConfiguration)
     */
    public BaseForm(GraphicsConfiguration gc) {
        super(gc);
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.lang.String)
     */
    public BaseForm(String title) throws HeadlessException {
        super(title);
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.lang.String, java.awt.GraphicsConfiguration)
     */
    public BaseForm(String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.setMinimumSize(new Dimension(500, 300));
    }

    //with prev so you can go back

    /**
     * @see javax.swing.JFrame#JFrame() 
     * @param prev Parent Form
     */
    public BaseForm(JFrame prev) throws HeadlessException {
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.awt.GraphicsConfiguration)  
     * @param prev Parent Form
     */
    public BaseForm(JFrame prev, GraphicsConfiguration gc) {
        super(gc);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.lang.String) 
     * @param prev Parent Form
     */
    public BaseForm(JFrame prev, String title) throws HeadlessException {
        super(title);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * @see javax.swing.JFrame#JFrame(java.lang.String, java.awt.GraphicsConfiguration) 
     * @param prev Parent Form
     */
    public BaseForm(JFrame prev, String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.prev = prev;
        this.setMinimumSize(new Dimension(500, 300));
    }

    /**
     * Replace this Frame with provided Frame
     * @param frame Next Frame
     */
    public void Replace(JFrame frame)
    {
        frame.setEnabled(true);
        frame.setLocation(this.getLocation());
        frame.setSize(this.getSize());
        this.setVisible(false);
        frame.setVisible(true);
    }

    /**
     * Return control to Parent Frame
     * @return Pointer to this Frame
     */
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
