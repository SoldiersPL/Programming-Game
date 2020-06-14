/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import packPlayer.LogTypes.logEntry;

/**
 *
 * @author piotr
 */
public final class fileOperations {
    final public static Hex[][] Load(File file)
    {
        Hex[][] map = null;
        try{
        FileInputStream stream = new FileInputStream(file);
        ObjectInputStream reader = new ObjectInputStream(stream);
        map = (Hex[][]) reader.readObject();
        reader.close();
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(
                    null, 
                    String.format("Program was unable to find this fille \n%s",
                            file.getPath()));
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(
                    null, 
                    String.format("Program was unable to read this fille \n%s\n%s",
                            file.getPath(), e.toString()));
        }
        catch(ClassNotFoundException e){}
        return map;
    }
    final public static void Save(File file , Hex[][] map)
    {
        try{
        FileOutputStream stream = new FileOutputStream(file);
        ObjectOutputStream writer = new ObjectOutputStream(stream);
        writer.writeObject(map);
        writer.flush();
        writer.close();
        }
        catch(FileNotFoundException e)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Program was unable to find this fille");
            sb.append(System.lineSeparator());
            sb.append(file.getPath());
            sb.append(System.lineSeparator());
            sb.append(e.toString());
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        catch(IOException e)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Program was unable to write this fille");
            sb.append(System.lineSeparator());
            sb.append(file.getPath());
            sb.append(System.lineSeparator());
            sb.append(e.toString());
            JOptionPane.showMessageDialog(null,sb.toString());
        }
    }
    final public static TreeSet<logEntry> LoadRaport(File file)
    {
        TreeSet<logEntry> report = null;
        try{
        FileInputStream stream = new FileInputStream(file);
        ObjectInputStream reader = new ObjectInputStream(stream);
        report = (TreeSet<logEntry>) reader.readObject();
        reader.close();
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(
                    null, 
                    String.format("Program was unable to find this fille \n%s",
                            file.getPath()));
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(
                    null, 
                    String.format("Program was unable to read this fille \n%s\n%s",
                            file.getPath(), e.toString()));
        }
        catch(ClassNotFoundException e){}
        return report;
    }
    final public static void SaveRaport(File file , TreeSet<logEntry> report)
    {
        try{
        FileOutputStream stream = new FileOutputStream(file);
        ObjectOutputStream writer = new ObjectOutputStream(stream);
        writer.writeObject(report);
        writer.flush();
        writer.close();
        }
        catch(FileNotFoundException e)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Program was unable to find this fille");
            sb.append(System.lineSeparator());
            sb.append(file.getPath());
            sb.append(System.lineSeparator());
            sb.append(e.toString());
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        catch(IOException e)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Program was unable to write this fille");
            sb.append(System.lineSeparator());
            sb.append(file.getPath());
            sb.append(System.lineSeparator());
            sb.append(e.toString());
            JOptionPane.showMessageDialog(null,sb.toString());
        }
    }
}
