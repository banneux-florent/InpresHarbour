/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capitainerie;

import Classes.*;
import Exceptions.CapitainerieException;
import Network.NetworkBasicServer;
import Utilisateurs.Connexion;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author flore
 */
public class Capitainerie extends javax.swing.JFrame {

    private boolean isLoggedIn = false;

    private LinkedList<Bateau> bateauAttenteEntrer = new LinkedList<Bateau>();
    private LinkedList<Bateau> bateauEnCoursDAmarrage = new LinkedList<Bateau>();
    private LinkedList<Bateau> bateauEntresDansLaRade = new LinkedList<Bateau>();
    private LinkedList<Ponton> pontons = new LinkedList<Ponton>();
    private LinkedList<Quai> quais = new LinkedList<Quai>();
    private NetworkBasicServer networkBS;
    private final int PORT = 50000;

    /**
     * Creates new form Capitainerie
     */
    public Capitainerie() {
        initComponents();
        Date now = new Date();
        setDateHeure(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE).format(now));

        pontons.add(new Ponton("1", 12, 15));
        quais.add(new Quai("1", 12, 15));

        DefaultListModel listModelBateauAttenteEntrer = new DefaultListModel();
        DefaultListModel listModelBateauEnCoursDAmarrage = new DefaultListModel();
        DefaultListModel listModelBateauEntresDansLaRade = new DefaultListModel();
        DefaultListModel listModelBateauxAmarres = new DefaultListModel();

        for (int i = 0; i < 10; i++) {
            try {
                Marin capitaine = new Marin("Mokh", "Wad", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Capitaine);
                Marin second = new Marin("Flo", "Bann", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Second);
                Marin tempMarin = new Marin("Air", "29", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Bosco);
                Marin tempMarin2 = new Marin("Oussama", "Achour", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.MaitreMecanicien);

                Equipage equipageTemporaire = new Equipage(capitaine, second);
                equipageTemporaire.getMarins().add(tempMarin);
                equipageTemporaire.getMarins().add(tempMarin2);

                BateauPlaisance tempBateauPlaisance = new BateauPlaisance("Bateau" + i, "Exeter", 200, 5, BateauPlaisance.TypePermis.PlaisanceExtentionHauturiere, "BE");
                System.out.println(tempBateauPlaisance);
                tempBateauPlaisance.setEquipage(equipageTemporaire);
                bateauAttenteEntrer.add(tempBateauPlaisance);
                bateauEntresDansLaRade.add(tempBateauPlaisance);
                bateauEnCoursDAmarrage.add(tempBateauPlaisance);
                pontons.get(0).addMTSE(tempBateauPlaisance);

                BateauPeche tempBateauPeche = new BateauPeche("Bateaupeche" + i, "Liege", 100, 10, BateauPeche.TypeDePeche.Thonier, "FR");
                System.out.println(tempBateauPeche);
                tempBateauPeche.setEquipage(equipageTemporaire);
                bateauAttenteEntrer.add(tempBateauPeche);
                bateauEntresDansLaRade.add(tempBateauPeche);
                bateauEnCoursDAmarrage.add(tempBateauPeche);
                quais.get(0).addMTSE(tempBateauPeche);

                listModelBateauAttenteEntrer.addElement(bateauAttenteEntrer.get(i));
                listModelBateauEntresDansLaRade.addElement(bateauEntresDansLaRade.get(i));
                listModelBateauEnCoursDAmarrage.addElement(bateauEnCoursDAmarrage.get(i));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0; i < pontons.size(); i++) {
            for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {
                listModelBateauxAmarres.addElement(pontons.get(i).getListe(1)[j]);
            }
            for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                //System.out.println(pontons.get(i).getListe(2)[j]);
                listModelBateauxAmarres.addElement(pontons.get(i).getListe(2)[j]);
            }
        }

        this.LBBateauAttenteEntrer.setModel(listModelBateauAttenteEntrer);
        this.LBBateauEnCoursDAmarrage.setModel(listModelBateauEnCoursDAmarrage);
        this.LBBateauEntresDansLaRade.setModel(listModelBateauEntresDansLaRade);
        this.LBBateauxAmarres.setModel(listModelBateauxAmarres);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LDateHeure = new javax.swing.JLabel();
        BtnDemarrerServeur = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LBBateauAttenteEntrer = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        LBBateauxAmarres = new javax.swing.JList<>();
        BtnRemplirInformationsBateau = new javax.swing.JButton();
        BtnChoisirEmplacement = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BtnEnvoyerEmplacement = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        LBBateauEnCoursDAmarrage = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LBBateauEntresDansLaRade = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        BtnEnvoyerConfirmation = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MILogin = new javax.swing.JMenuItem();
        MILogout = new javax.swing.JMenuItem();
        MINouveau = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MIPlaisance = new javax.swing.JMenuItem();
        MIPeche = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        MIListeComplete = new javax.swing.JMenuItem();
        MIRechercherUnBateau = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        MIEquipageBateau = new javax.swing.JMenuItem();
        MIRechercheUnMarin = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        MIFormatDate = new javax.swing.JMenuItem();
        MIFichierLog = new javax.swing.JMenuItem();
        MIAffichageDateHeureCourante = new javax.swing.JCheckBoxMenuItem();
        jMenu5 = new javax.swing.JMenu();
        MIAuteurs = new javax.swing.JMenuItem();
        MIAide = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        LDateHeure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LDateHeure.setText("...");

        BtnDemarrerServeur.setText("D�marrer le serveur");
        BtnDemarrerServeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDemarrerServeurActionPerformed(evt);
            }
        });

        jLabel2.setText("�tat du serveur:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("OFF");

        jLabel4.setText("Bateau(x) en attente pour entrer :");

        jScrollPane1.setViewportView(LBBateauAttenteEntrer);

        jScrollPane2.setViewportView(LBBateauxAmarres);

        BtnRemplirInformationsBateau.setText("Remplir informations du bateau");
        BtnRemplirInformationsBateau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRemplirInformationsBateauActionPerformed(evt);
            }
        });

        BtnChoisirEmplacement.setText("Choisir un emplacement");

        jLabel5.setText("Bateau(x) amarr�(s) :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Emplacement :");

        BtnEnvoyerEmplacement.setText("Envoyer");
        BtnEnvoyerEmplacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEnvoyerEmplacementActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(LBBateauEnCoursDAmarrage);

        jLabel7.setText("Bateau(x) en cours d'amarrage :");
        jLabel7.setToolTipText("");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Aucun");

        jScrollPane4.setViewportView(LBBateauEntresDansLaRade);

        jLabel9.setText("Bateau(x) entr�(s) dans la rade:");

        BtnEnvoyerConfirmation.setText("Envoyez la confirmation");
        BtnEnvoyerConfirmation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEnvoyerConfirmationActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Phare");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Capitainerie");

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));

        jLabel12.setBackground(new java.awt.Color(220, 220, 220));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("La capitainerie");
        jLabel12.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("Utilisateurs");

        MILogin.setText("Login");
        MILogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MILoginActionPerformed(evt);
            }
        });
        jMenu1.add(MILogin);

        MILogout.setText("Logout");
        MILogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MILogoutActionPerformed(evt);
            }
        });
        jMenu1.add(MILogout);

        MINouveau.setText("Nouveau");
        MINouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MINouveauActionPerformed(evt);
            }
        });
        jMenu1.add(MINouveau);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Amarrages");

        MIPlaisance.setText("Plaisance");
        MIPlaisance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIPlaisanceActionPerformed(evt);
            }
        });
        jMenu2.add(MIPlaisance);

        MIPeche.setText("P?che");
        MIPeche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIPecheActionPerformed(evt);
            }
        });
        jMenu2.add(MIPeche);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Bateaux");

        MIListeComplete.setText("Liste compl?te");
        MIListeComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIListeCompleteActionPerformed(evt);
            }
        });
        jMenu3.add(MIListeComplete);

        MIRechercherUnBateau.setText("Rechercher un bateau");
        MIRechercherUnBateau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIRechercherUnBateauActionPerformed(evt);
            }
        });
        jMenu3.add(MIRechercherUnBateau);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Personnel");

        MIEquipageBateau.setText("�quipage d'un bateau");
        MIEquipageBateau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIEquipageBateauActionPerformed(evt);
            }
        });
        jMenu4.add(MIEquipageBateau);

        MIRechercheUnMarin.setText("Rechercher un marin");
        MIRechercheUnMarin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIRechercheUnMarinActionPerformed(evt);
            }
        });
        jMenu4.add(MIRechercheUnMarin);

        jMenuBar1.add(jMenu4);

        jMenu6.setText("Param?tres");

        MIFormatDate.setText("Format date");
        MIFormatDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIFormatDateActionPerformed(evt);
            }
        });
        jMenu6.add(MIFormatDate);

        MIFichierLog.setText("Fichier log");
        jMenu6.add(MIFichierLog);

        MIAffichageDateHeureCourante.setSelected(true);
        MIAffichageDateHeureCourante.setText("Affichage date heure courante");
        MIAffichageDateHeureCourante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIAffichageDateHeureCouranteActionPerformed(evt);
            }
        });
        jMenu6.add(MIAffichageDateHeureCourante);

        jMenuBar1.add(jMenu6);

        jMenu5.setText("? propos");

        MIAuteurs.setText("Auteurs");
        MIAuteurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIAuteursActionPerformed(evt);
            }
        });
        jMenu5.add(MIAuteurs);

        MIAide.setText("Aide");
        MIAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIAideActionPerformed(evt);
            }
        });
        jMenu5.add(MIAide);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnEnvoyerConfirmation)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(BtnDemarrerServeur, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEnvoyerEmplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnChoisirEmplacement))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(LDateHeure, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnRemplirInformationsBateau, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnDemarrerServeur)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(LDateHeure))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(BtnChoisirEmplacement)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnEnvoyerEmplacement))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(BtnRemplirInformationsBateau)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEnvoyerConfirmation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnDemarrerServeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDemarrerServeurActionPerformed
        
    }//GEN-LAST:event_BtnDemarrerServeurActionPerformed

    private void BtnEnvoyerConfirmationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEnvoyerConfirmationActionPerformed
    }//GEN-LAST:event_BtnEnvoyerConfirmationActionPerformed

    private void BtnEnvoyerEmplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEnvoyerEmplacementActionPerformed
    }//GEN-LAST:event_BtnEnvoyerEmplacementActionPerformed

    private void MILoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MILoginActionPerformed
        if (!IsLoggedIn()) {
            Connexion connexion = new Connexion();
            connexion.setVisible(true);
            dispose();
        } else {
            new CapitainerieException("Erreur.", "Vous devez d'abord vous d�connecter de la capitainerie.");
        }
    }//GEN-LAST:event_MILoginActionPerformed

    private void MILogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MILogoutActionPerformed
        // D�sactivation des boutons
        BtnDemarrerServeur.setEnabled(false);
        BtnEnvoyerEmplacement.setEnabled(false);
        BtnChoisirEmplacement.setEnabled(false);
        BtnRemplirInformationsBateau.setEnabled(false);
        BtnEnvoyerConfirmation.setEnabled(false);

        // D�sactivation des listbox
        LBBateauAttenteEntrer.setEnabled(false);
        LBBateauAttenteEntrer.setEnabled(false);
        LBBateauEnCoursDAmarrage.setEnabled(false);
        LBBateauEntresDansLaRade.setEnabled(false);
        LBBateauxAmarres.setEnabled(false);

        setIsLoggedIn(false);
    }//GEN-LAST:event_MILogoutActionPerformed

    private void MIAffichageDateHeureCouranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIAffichageDateHeureCouranteActionPerformed
        LDateHeure.setVisible(!LDateHeure.isVisible());
    }//GEN-LAST:event_MIAffichageDateHeureCouranteActionPerformed

    private void MINouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MINouveauActionPerformed
        Utilisateurs_Nouveau ajoutUtilisateur = new Utilisateurs_Nouveau(this, true);
        ajoutUtilisateur.setVisible(true);
    }//GEN-LAST:event_MINouveauActionPerformed

    private void MIFormatDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIFormatDateActionPerformed
        Parametres_FormatDate parametres_FormatDate = new Parametres_FormatDate(this, true);
        parametres_FormatDate.setVisible(true);
    }//GEN-LAST:event_MIFormatDateActionPerformed

    private void BtnRemplirInformationsBateauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRemplirInformationsBateauActionPerformed
        int selectedIndex = this.LBBateauEnCoursDAmarrage.getSelectedIndex();
        if (selectedIndex != -1) {
            Bateau bateau = this.bateauEnCoursDAmarrage.get(selectedIndex);
            String emplacement = this.getEmplacementMTSE(bateau);
            RemplirInfoBateau remplirInfoBateau = new RemplirInfoBateau(this, true, bateau, emplacement);
            remplirInfoBateau.setTitle("Ajout informations du bateau � " + bateau.getNom() + " �");
            remplirInfoBateau.setVisible(true);
        }
    }//GEN-LAST:event_BtnRemplirInformationsBateauActionPerformed

    private void MIAuteursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIAuteursActionPerformed
        APropos_Auteurs aProposAuteurs = new APropos_Auteurs(this, true);
        aProposAuteurs.setVisible(true);
    }//GEN-LAST:event_MIAuteursActionPerformed

    private void MIAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIAideActionPerformed
        APropos_Aide aProposAide = new APropos_Aide(this, true);
        aProposAide.setVisible(true);
    }//GEN-LAST:event_MIAideActionPerformed

    private void MIListeCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIListeCompleteActionPerformed
        Bateaux_ListComplete listeBateaux = new Bateaux_ListComplete(pontons, quais);
        listeBateaux.setVisible(true);
    }//GEN-LAST:event_MIListeCompleteActionPerformed

    private void MIRechercherUnBateauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIRechercherUnBateauActionPerformed
        Bateaux_RechercherUnBateau rechercherBateau = new Bateaux_RechercherUnBateau(pontons, quais);
        rechercherBateau.setVisible(true);
    }//GEN-LAST:event_MIRechercherUnBateauActionPerformed

    private void MIPlaisanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIPlaisanceActionPerformed
        Amarrages_Plaisance amarragesPlaisance = new Amarrages_Plaisance(pontons);
        amarragesPlaisance.setVisible(true);
    }//GEN-LAST:event_MIPlaisanceActionPerformed

    private void MIPecheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIPecheActionPerformed
        Amarrages_Peche amarragesPeche = new Amarrages_Peche(quais);
        amarragesPeche.setVisible(true);
    }//GEN-LAST:event_MIPecheActionPerformed

    private void MIEquipageBateauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIEquipageBateauActionPerformed
        Personnel_EquipageDUnBateau peb = new Personnel_EquipageDUnBateau(pontons, quais);
        peb.setVisible(true);
    }//GEN-LAST:event_MIEquipageBateauActionPerformed

    private void MIRechercheUnMarinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIRechercheUnMarinActionPerformed
        Personnel_RechercherUnMarin prunmm = new Personnel_RechercherUnMarin(pontons, quais);
        prunmm.setAlwaysOnTop(true);
        prunmm.setVisible(true);
    }//GEN-LAST:event_MIRechercheUnMarinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnChoisirEmplacement;
    private javax.swing.JButton BtnDemarrerServeur;
    private javax.swing.JButton BtnEnvoyerConfirmation;
    private javax.swing.JButton BtnEnvoyerEmplacement;
    private javax.swing.JButton BtnRemplirInformationsBateau;
    private javax.swing.JList<String> LBBateauAttenteEntrer;
    private javax.swing.JList<String> LBBateauEnCoursDAmarrage;
    private javax.swing.JList<String> LBBateauEntresDansLaRade;
    private javax.swing.JList<String> LBBateauxAmarres;
    private javax.swing.JLabel LDateHeure;
    private javax.swing.JCheckBoxMenuItem MIAffichageDateHeureCourante;
    private javax.swing.JMenuItem MIAide;
    private javax.swing.JMenuItem MIAuteurs;
    private javax.swing.JMenuItem MIEquipageBateau;
    private javax.swing.JMenuItem MIFichierLog;
    private javax.swing.JMenuItem MIFormatDate;
    private javax.swing.JMenuItem MIListeComplete;
    private javax.swing.JMenuItem MILogin;
    private javax.swing.JMenuItem MILogout;
    private javax.swing.JMenuItem MINouveau;
    private javax.swing.JMenuItem MIPeche;
    private javax.swing.JMenuItem MIPlaisance;
    private javax.swing.JMenuItem MIRechercheUnMarin;
    private javax.swing.JMenuItem MIRechercherUnBateau;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the isLoggedIn
     */
    public boolean IsLoggedIn() {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setDateHeure(String dateHeure) {
        LDateHeure.setText(dateHeure);
    }

    public String getEmplacementMTSE(MoyenDeTransportSurEau mtse) {
        if (mtse instanceof BateauPlaisance) {
            for (int i = 0; i < pontons.size(); i++) {
                for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {
                    if (pontons.get(i).getListe(1)[j] == mtse) {
                        return "P" + i + "1*" + j;
                    }
                }
                for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                    if (pontons.get(i).getListe(2)[j] == mtse) {
                        return "P" + i + "2*" + j;
                    }
                }
            }
        } else {

            for (int i = 0; i < quais.size(); i++) {
                for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length; j++) {
                    if (quais.get(i).getListeBateauxAmarres()[j] == mtse) {
                        return "Q" + "1*" + j;
                    }
                }

            }

        }
        return " ";
    }
}
