package Capitainerie;

import Classes.Bateau;
import Classes.Equipage;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Florent & Wadi
 */
public class Personnel_EquipageDUnBateau_Liste extends javax.swing.JDialog {

    /**
     * Creates new form Personnel_EquipageDUnBateau_Liste
     */
    public Personnel_EquipageDUnBateau_Liste(Bateau bateau) {
        initComponents();
        
        this.LNomBateau.setText(bateau.getNom());
        
        DefaultTableModel tableModelEquipage = new DefaultTableModel();
        String[] columnNames = {"Nom", "Prénom", "Fonction", "Date de naissance"};

        for (String columnName : columnNames) {
            tableModelEquipage.addColumn(columnName);
        }
        
        Equipage equipage = bateau.getEquipage();
        
        tableModelEquipage.addRow(new Object[]{equipage.getCapitaine().getNom(), equipage.getCapitaine().getPrenom(), equipage.getCapitaine().getFonction(), equipage.getCapitaine().getDateNaissanceString()});
        tableModelEquipage.addRow(new Object[]{equipage.getSecond().getNom(), equipage.getSecond().getPrenom(), equipage.getSecond().getFonction(), equipage.getSecond().getDateNaissanceString()});
            
        for(int i = 0; i < equipage.getMarins().size(); i++) {
            tableModelEquipage.addRow(new Object[]{equipage.getMarins().get(i).getNom(), equipage.getMarins().get(i).getPrenom(), equipage.getMarins().get(i).getFonction(), equipage.getMarins().get(i).getDateNaissanceString()});
        }
        
        TableEquipage.setModel(tableModelEquipage);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        LNomBateau = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableEquipage = new javax.swing.JTable();
        BtnFermer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Equipage d'un bateau");

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));

        jLabel9.setBackground(new java.awt.Color(220, 220, 220));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Équipage d'un bateau");
        jLabel9.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        LNomBateau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LNomBateau.setText("...");

        TableEquipage.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TableEquipage);

        BtnFermer.setText("Fermer");
        BtnFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFermerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LNomBateau)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnFermer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LNomBateau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnFermer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFermerActionPerformed
        dispose();
    }//GEN-LAST:event_BtnFermerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnFermer;
    private javax.swing.JLabel LNomBateau;
    private javax.swing.JTable TableEquipage;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
