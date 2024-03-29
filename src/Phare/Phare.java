package Phare;

import Classes.Bateau;
import Classes.DialogErreur;
import Classes.FichierLog;
import Classes.Fonctions;
import Network.Frame;
import Network.IInOutEvent;
import Network.NetworkBasicClient;
import Network.XMLFormatter;
import beans.BoatBean;
import beans.IUserNumber;
import beans.KindOfBoatBean;
import beans.NotifyBean;
import java.beans.Beans;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import javax.swing.DefaultListModel;
import threadsutils.ThreadRandomGenerator;

/**
 *
 * @author Florent & Wadi
 */
public class Phare extends javax.swing.JFrame implements IInOutEvent, IUserNumber {

    private NetworkBasicClient networkBC = null;
    private final ThreadRandomGenerator threadRandomGenerator;
    private String HOST;
    private int PORT;
    private int idKindOfBoatBean = 1;

    private LinkedList<Bateau> bateauxNonIdentifies = new LinkedList<>();
    private LinkedList<Bateau> bateauxIdentifies = new LinkedList<>();
    private LinkedList<Bateau> reponsesCapitainerie = new LinkedList<>();
    private LinkedList<Bateau> confirmationsCapitainerie = new LinkedList<>();
    private boolean showMessages = true;
    private int lowerBound;
    private int upperBound;
    private int triggerMultiple;
    private int waitingTime;

    /**
     * Creates new form Phare
     */
    public Phare() {
        initComponents();
        
        this.LDateHeure.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE).format(new Date()));
        
        FichierLog fl = new FichierLog();
        fl.ecrireLigne("Le Phare a été démarré.");
        
        Properties properties = new Properties();
        try {
            properties = Fonctions.chargerConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.HOST = properties.getProperty("network.host");
        this.PORT = Integer.parseInt(properties.getProperty("network.port"));
        this.showMessages = Integer.parseInt(properties.getProperty("boatGenerator.showMessages")) == 1;
        this.lowerBound = Integer.parseInt(properties.getProperty("boatGenerator.lowerBound"));
        this.upperBound = Integer.parseInt(properties.getProperty("boatGenerator.upperBound"));
        this.triggerMultiple = Integer.parseInt(properties.getProperty("boatGenerator.triggerMultiple"));
        this.waitingTime = Integer.parseInt(properties.getProperty("boatGenerator.waitingTime"));

        try {
            this.networkBC = new NetworkBasicClient(this.HOST, this.PORT, this, this.LOnOff, this.BtnSeConnecterAuServeur);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        this.LBBateauxNonIdentifies.setModel(new DefaultListModel());
        this.LBBateauxIdentifies.setModel(new DefaultListModel());
        this.LBReponsesCapitainerie.setModel(new DefaultListModel());
        this.LBConfirmationsCapitainerie.setModel(new DefaultListModel());
        
        loadSavedData();

        this.threadRandomGenerator = new ThreadRandomGenerator(this, showMessages, lowerBound, upperBound, waitingTime);
        this.threadRandomGenerator.start();
    }

    private void loadSavedData() {
        String sep = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + sep + "Donnees" + sep + "phare" + sep;
        File directory = new File(path);
        
        if (!directory.exists()) {
            path = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + "phare" + sep;
            directory = new File(path);
            if (!directory.exists()) {
                return;
            }
        }

        try {
            File toCheck = new File(path + "bateauxNonIdentifies.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauxNonIdentifies.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauxNonIdentifies = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.bateauxNonIdentifies.size(); i++) {
                    ((DefaultListModel) this.LBBateauxNonIdentifies.getModel()).addElement(this.bateauxNonIdentifies.get(i));
                }
                objectIn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            File toCheck = new File(path + "bateauxIdentifies.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauxIdentifies.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauxIdentifies = (LinkedList<Bateau>) objectIn.readObject();
                for (int i = 0; i < this.bateauxIdentifies.size(); i++) {
                    ((DefaultListModel) this.LBBateauxIdentifies.getModel()).addElement(this.bateauxIdentifies.get(i));
                }
                objectIn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            File toCheck = new File(path + "reponsesCapitainerie.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "reponsesCapitainerie.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.reponsesCapitainerie = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.reponsesCapitainerie.size(); i++) {
                    ((DefaultListModel) this.LBReponsesCapitainerie.getModel()).addElement(this.reponsesCapitainerie.get(i));
                }
                objectIn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            File toCheck = new File(path + "confirmationsCapitainerie.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "confirmationsCapitainerie.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.confirmationsCapitainerie = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.confirmationsCapitainerie.size(); i++) {
                    ((DefaultListModel) this.LBConfirmationsCapitainerie.getModel()).addElement(this.confirmationsCapitainerie.get(i));
                }
                objectIn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void AjouterBateauIdentifier(Bateau bateau) {
        //Supprimer de la liste non identifier et rajouter dans la liste des bateaux identifies
        bateauxIdentifies.add(bateau);
        bateauxNonIdentifies.remove(bateau);
        ((DefaultListModel) this.LBBateauxNonIdentifies.getModel()).removeElement(bateau);
        ((DefaultListModel) this.LBBateauxIdentifies.getModel()).addElement(bateau);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        PanelHeader = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        BtnSeConnecterAuServeur = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        LOnOff = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        LBBateauxNonIdentifies = new javax.swing.JList<>();
        BtnIdentifierLeBateau = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        LBBateauxIdentifies = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        BtnDemanderAutorisationEntrer = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        LBReponsesCapitainerie = new javax.swing.JList<>();
        BtnBateauEntrerDansLaRade = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        LBConfirmationsCapitainerie = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        viderListeBateauxNonIdentifies = new javax.swing.JButton();
        LDateHeure = new javax.swing.JLabel();

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Le phare");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanelHeader.setBackground(new java.awt.Color(220, 220, 220));

        jLabel9.setBackground(new java.awt.Color(220, 220, 220));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Le phare");
        jLabel9.setAutoscrolls(true);

        javax.swing.GroupLayout PanelHeaderLayout = new javax.swing.GroupLayout(PanelHeader);
        PanelHeader.setLayout(PanelHeaderLayout);
        PanelHeaderLayout.setHorizontalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelHeaderLayout.setVerticalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        BtnSeConnecterAuServeur.setText("Se connecter au serveur");
        BtnSeConnecterAuServeur.setActionCommand("Se connecter serveur");
        BtnSeConnecterAuServeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeConnecterAuServeurActionPerformed(evt);
            }
        });

        jLabel2.setText("État de la connexion:");

        LOnOff.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LOnOff.setForeground(new java.awt.Color(255, 0, 0));
        LOnOff.setText("OFF");

        jLabel7.setText("Bateau(x) non-identifié(s) :");

        jScrollPane3.setViewportView(LBBateauxNonIdentifies);

        BtnIdentifierLeBateau.setText("Identifier le bateau");
        BtnIdentifierLeBateau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIdentifierLeBateauActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(LBBateauxIdentifies);

        jLabel15.setText("Bateau(x) identifié(s) :");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Bateau(x) en attente de rentrer dans la rade");

        BtnDemanderAutorisationEntrer.setText("Demander autorisation d'entrer");
        BtnDemanderAutorisationEntrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDemanderAutorisationEntrerActionPerformed(evt);
            }
        });

        jLabel17.setText("Réponse(s) de la capitainerie :");

        jScrollPane5.setViewportView(LBReponsesCapitainerie);

        BtnBateauEntrerDansLaRade.setText("Bateau entré dans la rade");
        BtnBateauEntrerDansLaRade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBateauEntrerDansLaRadeActionPerformed(evt);
            }
        });

        jLabel18.setText("Confirmation(s) de la capitainerie :");

        jScrollPane6.setViewportView(LBConfirmationsCapitainerie);

        viderListeBateauxNonIdentifies.setText("Vider la liste");
        viderListeBateauxNonIdentifies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viderListeBateauxNonIdentifiesActionPerformed(evt);
            }
        });

        LDateHeure.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LDateHeure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LDateHeure.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtnSeConnecterAuServeur, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LOnOff)
                                .addGap(80, 80, 80)
                                .addComponent(LDateHeure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(viderListeBateauxNonIdentifies)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BtnIdentifierLeBateau))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(BtnDemanderAutorisationEntrer, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BtnBateauEntrerDansLaRade)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSeConnecterAuServeur)
                    .addComponent(jLabel2)
                    .addComponent(LOnOff)
                    .addComponent(LDateHeure))
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnIdentifierLeBateau)
                        .addComponent(viderListeBateauxNonIdentifies))
                    .addComponent(BtnDemanderAutorisationEntrer))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnBateauEntrerDansLaRade)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSeConnecterAuServeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeConnecterAuServeurActionPerformed
        if (this.networkBC.isDisconnected()) {
            try {
                this.networkBC.connect();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            this.networkBC.disconnect();
        }
    }//GEN-LAST:event_BtnSeConnecterAuServeurActionPerformed

    private void BtnIdentifierLeBateauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIdentifierLeBateauActionPerformed
        int selectedIndex = this.LBBateauxNonIdentifies.getSelectedIndex();
        if (selectedIndex != -1) {
            Bateau bateau = this.bateauxNonIdentifies.get(selectedIndex);

            IdentificationBateau identificationBateau = new IdentificationBateau(this, bateau);
            identificationBateau.setVisible(true);
        }
    }//GEN-LAST:event_BtnIdentifierLeBateauActionPerformed

    private void BtnDemanderAutorisationEntrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDemanderAutorisationEntrerActionPerformed
        if (this.networkBC.isConnected()) {
            int selectedIndex = this.LBBateauxIdentifies.getSelectedIndex();
            if (selectedIndex != -1) {
                Bateau bateau = this.bateauxIdentifies.get(selectedIndex);

                try {
                    String xmlBateau = XMLFormatter.toXML(bateau);

                    // Envois bateau dans capitainerie
                    Frame.send(this.networkBC, new String[]{"capitainerie_ajouter_bateau_liste", "bateau_attente_entree", xmlBateau});

                    // Retrait bateau phare
                    bateauxIdentifies.remove(bateau);
                    ((DefaultListModel) this.LBBateauxIdentifies.getModel()).removeElement(bateau);
                } catch (Exception e) {
                    System.err.println("[Phare | Error] \"" + e.getMessage() + "\"");
                }
            }
        } else {
            DialogErreur de = new DialogErreur("Erreur", "Le phare n'est pas connecté au serveur.");
            de.setVisible(true);
        }
    }//GEN-LAST:event_BtnDemanderAutorisationEntrerActionPerformed

    private void BtnBateauEntrerDansLaRadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBateauEntrerDansLaRadeActionPerformed
        if (this.networkBC.isConnected()) {
            int selectedIndex = this.LBReponsesCapitainerie.getSelectedIndex();
            if (selectedIndex != -1) {
                Bateau bateau = this.reponsesCapitainerie.get(selectedIndex);
                try {
                    String xmlBateau = XMLFormatter.toXML(bateau);

                    // Envois bateau dans capitainerie
                    Frame.send(this.networkBC, new String[]{"capitainerie_ajouter_bateau_liste", "bateau_entre_rade", xmlBateau});

                    // Retrait bateau phare
                    reponsesCapitainerie.remove(bateau);
                    ((DefaultListModel) this.LBReponsesCapitainerie.getModel()).removeElement(bateau);
                } catch (Exception e) {
                    System.err.println("[Phare | Error] \"" + e.getMessage() + "\"");
                }
            }
        } else {
            DialogErreur de = new DialogErreur("Erreur", "Le phare n'est pas connecté au serveur.");
            de.setVisible(true);
        }
    }//GEN-LAST:event_BtnBateauEntrerDansLaRadeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String sep = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + sep + "Donnees" + sep + "phare" + sep;
        File directory = new File(path);
        if (!directory.exists()) {
            path = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + "phare" + sep;
            directory = new File(path);
            if (!directory.exists()) {
                return;
            }
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "bateauxNonIdentifies.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauxNonIdentifies);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(path + "bateauxIdentifies.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauxIdentifies);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "reponsesCapitainerie.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.reponsesCapitainerie);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "confirmationsCapitainerie.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.confirmationsCapitainerie);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        FichierLog fl = new FichierLog();
        fl.ecrireLigne("Le Phare a été fermé.");
    }//GEN-LAST:event_formWindowClosing

    private void viderListeBateauxNonIdentifiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viderListeBateauxNonIdentifiesActionPerformed
        this.bateauxNonIdentifies.clear();
        this.LBBateauxNonIdentifies.setModel(new DefaultListModel());
    }//GEN-LAST:event_viderListeBateauxNonIdentifiesActionPerformed

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
            java.util.logging.Logger.getLogger(Phare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Phare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Phare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Phare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Phare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBateauEntrerDansLaRade;
    private javax.swing.JButton BtnDemanderAutorisationEntrer;
    private javax.swing.JButton BtnIdentifierLeBateau;
    private javax.swing.JButton BtnSeConnecterAuServeur;
    private javax.swing.JList<String> LBBateauxIdentifies;
    private javax.swing.JList<String> LBBateauxNonIdentifies;
    private javax.swing.JList<String> LBConfirmationsCapitainerie;
    private javax.swing.JList<String> LBReponsesCapitainerie;
    private javax.swing.JLabel LDateHeure;
    private javax.swing.JLabel LOnOff;
    private javax.swing.JPanel PanelHeader;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton viderListeBateauxNonIdentifies;
    // End of variables declaration//GEN-END:variables

    public void AddBoatNoIdentified(Bateau b) {
        bateauxNonIdentifies.add(b);
        ((DefaultListModel) this.LBBateauxNonIdentifies.getModel()).addElement(b);
    }
    
    // IInOutEvent
    @Override
    public String getMessage() {
        return this.networkBC.getMessage();
    }

    @Override
    public String readMessage() {
        return this.networkBC.readMessage();
    }

    @Override
    public void sendMessage(String message) {
        this.networkBC.sendMessage(message);
    }

    @Override
    public void messageReceived() {
        String message = this.getMessage();
        System.out.println("[Phare | Info] [<<<] " + message);
        try {
            Frame frame = (Frame) XMLFormatter.fromXML(message);
            String action = frame.getAction();
            if (action.equals("phare_ajouter_bateau_liste")) {
                String list = frame.getArg(1);
                String objectXml = XMLFormatter.decode(frame.getArg(2));
                Object object = XMLFormatter.fromXML(objectXml);
                if (list.equals("reponse_capitainerie")) {
                    if (object instanceof Bateau) {
                        ((DefaultListModel) this.LBReponsesCapitainerie.getModel()).addElement((Bateau) object);
                        this.reponsesCapitainerie.add((Bateau) object);
                    } else {
                        System.err.println("[Phare | Error] Couldn't cast object received as \"Bateau\"");
                    }
                } else if (list.equals("confirmation_capitainerie")) {
                    Bateau bateau = (Bateau) object;
                    this.reponsesCapitainerie.remove(bateau);
                    ((DefaultListModel) this.LBReponsesCapitainerie.getModel()).removeElement(bateau);
                    this.confirmationsCapitainerie.add(bateau);
                    ((DefaultListModel) this.LBConfirmationsCapitainerie.getModel()).addElement(bateau);
                }
            } else if (action.equals("capitainerie_supprimer_bateau_liste")) {

            } else {
                System.err.println("Aucune action...");
            }
        } catch (Exception e) {
            System.err.println("[Phare | Error] \"" + e.getMessage() + "\"");
        }
    }

    @Override
    public String getId() {
        return "Phare";
    }

    @Override
    public void processNumber(int n) {
        if (n % this.triggerMultiple == 0) {
            try {
                KindOfBoatBean kindOfBoatBean = (KindOfBoatBean) Beans.instantiate(null, "beans.KindOfBoatBean");
                BoatBean boatBean = (BoatBean) Beans.instantiate(null, "beans.BoatBean");
                NotifyBean notifyBean = (NotifyBean) Beans.instantiate(null, "beans.NotifyBean");

                notifyBean.setPhare(this);
                kindOfBoatBean.setIsRunning(true);
                kindOfBoatBean.setId("Bean" + this.idKindOfBoatBean);
                kindOfBoatBean.addPropertyChangeListener(boatBean);
                boatBean.addListener(notifyBean);
                kindOfBoatBean.start();

                this.idKindOfBoatBean++;
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
