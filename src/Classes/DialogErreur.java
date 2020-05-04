/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author WADI
 */
public class DialogErreur extends javax.swing.JDialog {

    /**
     * Creates new form DialogErreur
     */
    public DialogErreur(String titreDialog, String messageErreur) {
        super();
        initComponents();
        initFrame(titreDialog);
        LMessageErreur.setText(messageErreur);
    }

    public DialogErreur(String titreDialog, String messageErreur, boolean modal) {
        super();
        initComponents();
        initFrame(titreDialog);
        this.setModal(modal);
        LMessageErreur.setText(messageErreur);
    }

    private DialogErreur(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initFrame("Erreur");
        LMessageErreur.setText("Erreur.");
    }

    private void initFrame(String titreErreur) {
        this.setTitle(titreErreur);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        String sep = System.getProperty("file.separator");
        ImageIcon a = new ImageIcon(System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "java" + sep + "Donnees" + sep + "error" + ".png");
        this.LImage.setIcon(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        LMessageErreur = new javax.swing.JLabel();
        LImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Erreur");
        setResizable(false);

        jButton1.setText("Fermer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        LMessageErreur.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LMessageErreur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LMessageErreur.setText("Erreur.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LMessageErreur, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(LImage, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LMessageErreur)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 115, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(LImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LImage;
    private javax.swing.JLabel LMessageErreur;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
