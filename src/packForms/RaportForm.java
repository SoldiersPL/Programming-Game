/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packForms;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.io.File;
import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import packMap.fileOperations;
import packPlayer.LogTypes.logEntry;

/**
 *
 * @author piotr
 */
public class RaportForm extends BaseForm {

    /**
     * Creates new form RaportForm
     */
    private final TreeSet<logEntry> report;

    public RaportForm(TreeSet<logEntry> report) throws HeadlessException {
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, GraphicsConfiguration gc) {
        super(gc);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, String title) throws HeadlessException {
        super(title);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, JFrame prev) throws HeadlessException {
        super(prev);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, JFrame prev, GraphicsConfiguration gc) {
        super(prev, gc);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, JFrame prev, String title) throws HeadlessException {
        super(prev, title);
        this.report = report;
        Init();
    }

    public RaportForm(TreeSet<logEntry> report, JFrame prev, String title, GraphicsConfiguration gc) {
        super(prev, title, gc);
        this.report = report;
        Init();
    }
    
    private void Init()
    {
        initComponents();
        DefaultListModel<logEntry> model = new DefaultListModel<>();
        model.addAll(report);
        jListRaport.setModel(model);
        InitFileChosers();
    }
    private void InitFileChosers()
    {
        File home = new File(System.getProperty("user.dir") + "/raports");
        if(! home.exists()) home.mkdir();
        jFileChooserSave.setCurrentDirectory(home);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Raport files", "rap");
        jFileChooserSave.setFileFilter(filter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooserSave = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListRaport = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        jFileChooserSave.setAcceptAllFileFilterUsed(false);
        jFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                jSplitPane1AncestorResized(evt);
            }
        });

        jScrollPane1.setViewportView(jListRaport);

        jSplitPane1.setTopComponent(jScrollPane1);

        jButtonSave.setText("Save Raport");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonBack.setText("Go back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addComponent(jButtonBack)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack)
                    .addComponent(jButtonSave))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        int result = jFileChooserSave.showSaveDialog(null);
        if(result != JFileChooser.APPROVE_OPTION) return;
        File targetFile = jFileChooserSave.getSelectedFile();
        //check if file exists, if it do, can program write on it?
        if (targetFile.exists() && ! targetFile.canWrite()) return;
        String path = targetFile.getPath();
        if(!path.endsWith(".rap")) path += ".rap";
        report.comparator();
        fileOperations.SaveRaport(new File(path), report);
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        Back();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jSplitPane1AncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jSplitPane1AncestorResized
        jSplitPane1.setDividerLocation(0.8);
    }//GEN-LAST:event_jSplitPane1AncestorResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JFileChooser jFileChooserSave;
    private javax.swing.JList<logEntry> jListRaport;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
