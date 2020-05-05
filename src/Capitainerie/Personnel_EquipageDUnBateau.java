package Capitainerie;

import Classes.Amarrage;
import Classes.Bateau;
import Classes.BateauPeche;
import Classes.BateauPlaisance;
import Classes.Ponton;
import Classes.Quai;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Florent & Wadi
 */
public class Personnel_EquipageDUnBateau extends javax.swing.JFrame {

    private LinkedList<Ponton> pontons = new LinkedList<Ponton>();
    private LinkedList<Quai> quais = new LinkedList<Quai>();
    
    /**
     * Creates new form ListeBateaux
     *
     * @param pontons
     */
    public Personnel_EquipageDUnBateau(LinkedList<Ponton> pontons, LinkedList<Quai> quais) {
        initComponents();
        
        this.pontons = pontons;
        this.quais = quais;

        DefaultTableModel tableModelBateauPlaisance = new DefaultTableModel();
        String[] columnNames = {"Nom", "Pavillon", "Emplacement"};

        for (String columnName : columnNames) {
            tableModelBateauPlaisance.addColumn(columnName);
        }

        BateauPlaisance tempBateauPlaisance;
        for (int i = 0; i < pontons.size(); i++) {
            for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {
                tempBateauPlaisance = (BateauPlaisance) pontons.get(i).getListe(1)[j];
                if (tempBateauPlaisance != null) {
                    tableModelBateauPlaisance.addRow(new Object[]{tempBateauPlaisance.getNom(), tempBateauPlaisance.getPavillon(), "P" + i + "1*" + j});
                }
            }
            for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                tempBateauPlaisance = (BateauPlaisance) pontons.get(i).getListe(2)[j];
                if (tempBateauPlaisance != null) {
                    tableModelBateauPlaisance.addRow(new Object[]{tempBateauPlaisance.getNom(), tempBateauPlaisance.getPavillon(), "P" + i + "2*" + j});
                }
            }
        }

        TableBateauxPlaisance.setModel(tableModelBateauPlaisance);

        BateauPeche tempBateauPeche;

        DefaultTableModel tableModelBateauPeche = new DefaultTableModel();

        for (String columnName : columnNames) {
            tableModelBateauPeche.addColumn(columnName);
        }

        for (int i = 0; i < quais.size(); i++) {
            for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length; j++) {
                tempBateauPeche = (BateauPeche) quais.get(i).getListeBateauxAmarres()[j];
                if (tempBateauPeche != null) {
                    tableModelBateauPeche.addRow(new Object[]{tempBateauPeche.getNom(), tempBateauPeche.getPavillon(), "Q" + i + "*" + j});
                }
            }
        }

        TableBateauxPeche.setModel(tableModelBateauPeche);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        LListeDesBateaux = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBateauxPlaisance = new javax.swing.JTable();
        BtnFermer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableBateauxPeche = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnAffichierEquipage2 = new javax.swing.JButton();
        BtnAffichierEquipage1 = new javax.swing.JButton();

        jLabel6.setBackground(new java.awt.Color(220, 220, 220));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Aide");
        jLabel6.setAutoscrolls(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Liste des bateaux");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));
        jPanel1.setToolTipText("");

        LListeDesBateaux.setBackground(new java.awt.Color(220, 220, 220));
        LListeDesBateaux.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LListeDesBateaux.setText("Liste compl�te des bateaux");
        LListeDesBateaux.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LListeDesBateaux, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LListeDesBateaux, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        TableBateauxPlaisance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TableBateauxPlaisance);

        BtnFermer.setText("Fermer");
        BtnFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFermerActionPerformed(evt);
            }
        });

        TableBateauxPeche.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TableBateauxPeche);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Bateau(x) de p�che :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Bateau(x) de plaisance :");

        BtnAffichierEquipage2.setText("Afficher l'�quipage");
        BtnAffichierEquipage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAffichierEquipage2ActionPerformed(evt);
            }
        });

        BtnAffichierEquipage1.setText("Afficher l'�quipage");
        BtnAffichierEquipage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAffichierEquipage1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnFermer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnAffichierEquipage2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnAffichierEquipage1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BtnAffichierEquipage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BtnAffichierEquipage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnFermer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFermerActionPerformed
        dispose();
    }//GEN-LAST:event_BtnFermerActionPerformed

    private void BtnAffichierEquipage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAffichierEquipage1ActionPerformed
        int selectedIndex = this.TableBateauxPeche.getSelectedRow();
        if (selectedIndex != -1) {
            BateauPeche bp = null;
            for (int i = 0; i < quais.size(); i++) {
                for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length; j++) {
                    BateauPeche tempBateauPeche = (BateauPeche) quais.get(i).getListeBateauxAmarres()[j];
                    if (tempBateauPeche != null) {
                        if (selectedIndex == 0) {
                            bp = tempBateauPeche;
                        }
                        selectedIndex--;
                    }
                }
            }
            if (bp != null) {
                Personnel_EquipageDUnBateau_Liste pebl = new Personnel_EquipageDUnBateau_Liste(bp);
                pebl.setAlwaysOnTop(true);
                pebl.setTitle("�quipage du bateau � " + bp.getNom() + " �");
                pebl.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnAffichierEquipage1ActionPerformed

    private void BtnAffichierEquipage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAffichierEquipage2ActionPerformed
        int selectedIndex = this.TableBateauxPlaisance.getSelectedRow();
        if (selectedIndex != -1) {
            BateauPlaisance bp = null;
            for (int i = 0; i < pontons.size(); i++) {
                for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {
                    BateauPlaisance tempBateauPlaisance = (BateauPlaisance) pontons.get(i).getListe(1)[j];
                    if (tempBateauPlaisance != null) {
                        if (selectedIndex == 0) {
                            bp = tempBateauPlaisance;
                        }
                        selectedIndex--;
                    }
                }
                for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                    BateauPlaisance tempBateauPlaisance = (BateauPlaisance) pontons.get(i).getListe(2)[j];
                    if (tempBateauPlaisance != null) {
                        if (selectedIndex == 0) {
                            bp = tempBateauPlaisance;
                        }
                        selectedIndex--;
                    }
                }
            }
            if (bp != null) {
                Personnel_EquipageDUnBateau_Liste pebl = new Personnel_EquipageDUnBateau_Liste(bp);
                pebl.setAlwaysOnTop(true);
                pebl.setTitle("�quipage du bateau � " + bp.getNom() + " �");
                pebl.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnAffichierEquipage2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Personnel_EquipageDUnBateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Personnel_EquipageDUnBateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Personnel_EquipageDUnBateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Personnel_EquipageDUnBateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAffichierEquipage1;
    private javax.swing.JButton BtnAffichierEquipage2;
    private javax.swing.JButton BtnFermer;
    private javax.swing.JLabel LListeDesBateaux;
    private javax.swing.JTable TableBateauxPeche;
    private javax.swing.JTable TableBateauxPlaisance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
