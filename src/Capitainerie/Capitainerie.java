package Capitainerie;

import Classes.*;
import Exceptions.CapitainerieException;
import Exceptions.SailorIndicatedIsNotACaptainException;
import Exceptions.SailorIndicatedIsNotASecondException;
import Exceptions.ShipWithoutIdentificationException;
import Network.Frame;
import Network.IInOutEvent;
import Network.NetworkBasicServer;
import Network.XMLFormatter;
import utilisateurs.Connexion;
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

/**
 *
 * @author Florent & Wadi
 */
public class Capitainerie extends javax.swing.JFrame implements IInOutEvent {

    private boolean isLoggedIn = false;

    private NetworkBasicServer networkBS;
    private int PORT;

    public LinkedList<Bateau> bateauAttenteEntrer = new LinkedList<>();
    public LinkedList<Bateau> bateauEntresDansLaRade = new LinkedList<>();
    public LinkedList<Bateau> bateauEnCoursDAmarrage = new LinkedList<>();
    public LinkedList<Bateau> bateauxAmarres = new LinkedList<>();

    public LinkedList<Ponton> pontons = new LinkedList<>();
    public LinkedList<Quai> quais = new LinkedList<>();
    public Properties properties;

    /**
     * Creates new form Capitainerie
     */
    public Capitainerie() {
        initComponents();
        
        FichierLog fl = new FichierLog();
        fl.ecrireLigne("La Capintainerie a été démarrée.");
        
        Properties properties = new Properties();
        try {
            properties = Fonctions.chargerConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.PORT = Integer.parseInt(properties.getProperty("network.port"));

        try {
            this.networkBS = new NetworkBasicServer(this.PORT, this, this.LOnOff, this.BtnDemarrerServeur);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        setDateHeure(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE).format(new Date()));

        this.LBBateauAttenteEntrer.setModel(new DefaultListModel());
        this.LBBateauEnCoursDAmarrage.setModel(new DefaultListModel());
        this.LBBateauEntresDansLaRade.setModel(new DefaultListModel());
        this.LBBateauxAmarres.setModel(new DefaultListModel());
        
        this.loadSavedData();
    }

    private void loadSavedData() {
        String sep = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + sep + "Donnees" + sep + "capitainerie" + sep;
        File directory = new File(path);
        
        if (!directory.exists()) {
            path = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + "capitainerie" + sep;
            directory = new File(path);
            if (!directory.exists()) {
                return;
            }
        }

        try {
            File toCheck = new File(path + "bateauAttenteEntrer.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauAttenteEntrer.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauAttenteEntrer = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.bateauAttenteEntrer.size(); i++) {
                    ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).addElement(this.bateauAttenteEntrer.get(i));
                }
                objectIn.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            File toCheck = new File(path + "bateauEnCoursDAmarrage.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauEnCoursDAmarrage.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauEnCoursDAmarrage = (LinkedList<Bateau>) objectIn.readObject();
                for (int i = 0; i < this.bateauEnCoursDAmarrage.size(); i++) {
                    ((DefaultListModel) this.LBBateauEnCoursDAmarrage.getModel()).addElement(this.bateauEnCoursDAmarrage.get(i));
                }
                objectIn.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            File toCheck = new File(path + "bateauEntresDansLaRade.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauEntresDansLaRade.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauEntresDansLaRade = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.bateauEntresDansLaRade.size(); i++) {
                    ((DefaultListModel) this.LBBateauEntresDansLaRade.getModel()).addElement(this.bateauEntresDansLaRade.get(i));
                }
                objectIn.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            File toCheck = new File(path + "bateauAmarre.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "bateauAmarre.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                this.bateauxAmarres = (LinkedList<Bateau>) objectIn.readObject();

                for (int i = 0; i < this.bateauxAmarres.size(); i++) {
                    ((DefaultListModel) this.LBBateauxAmarres.getModel()).addElement(this.bateauxAmarres.get(i));
                }
                objectIn.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            File toCheck = new File(path + "pontons.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "pontons.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                this.pontons = (LinkedList<Ponton>) objectIn.readObject();
                objectIn.close();
            } else {
                this.pontons.add(new Ponton("1", 12, 15.0));
                this.pontons.add(new Ponton("2", 12, 15.0));
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            File toCheck = new File(path + "quais.dat");
            if (toCheck.exists()) {
                FileInputStream fileIn = new FileInputStream(path + "quais.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                this.quais = (LinkedList<Quai>) objectIn.readObject();
                objectIn.close();
            } else {
                this.quais.add(new Quai("1", 6, 15.0));
                this.quais.add(new Quai("2", 6, 15.0));
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

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
        LOnOff = new javax.swing.JLabel();
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
        LBEmplacement = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LBBateauEntresDansLaRade = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        BtnEnvoyerConfirmation = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        PanelHeader = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        MenuPrincipal = new javax.swing.JMenuBar();
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        LDateHeure.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LDateHeure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LDateHeure.setText("...");

        BtnDemarrerServeur.setText("Démarrer le serveur");
        BtnDemarrerServeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDemarrerServeurActionPerformed(evt);
            }
        });

        jLabel2.setText("État du serveur:");

        LOnOff.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LOnOff.setForeground(new java.awt.Color(255, 0, 0));
        LOnOff.setText("OFF");

        jLabel4.setText("Bateau(x) en attente pour entrer :");

        LBBateauAttenteEntrer.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LBBateauAttenteEntrerValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(LBBateauAttenteEntrer);

        jScrollPane2.setViewportView(LBBateauxAmarres);

        BtnRemplirInformationsBateau.setText("Remplir informations du bateau");
        BtnRemplirInformationsBateau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRemplirInformationsBateauActionPerformed(evt);
            }
        });

        BtnChoisirEmplacement.setText("Choisir un emplacement");
        BtnChoisirEmplacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChoisirEmplacementActionPerformed(evt);
            }
        });

        jLabel5.setText("Bateau(x) amarré(s) :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Emplacement:");

        BtnEnvoyerEmplacement.setText("Envoyer");
        BtnEnvoyerEmplacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEnvoyerEmplacementActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(LBBateauEnCoursDAmarrage);

        jLabel7.setText("Bateau(x) en cours d'amarrage :");
        jLabel7.setToolTipText("");

        LBEmplacement.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBEmplacement.setText("Aucun");

        jScrollPane4.setViewportView(LBBateauEntresDansLaRade);

        jLabel9.setText("Bateau(x) entré(s) dans la rade:");

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

        PanelHeader.setBackground(new java.awt.Color(220, 220, 220));

        jLabel12.setBackground(new java.awt.Color(220, 220, 220));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("La capitainerie");
        jLabel12.setAutoscrolls(true);

        javax.swing.GroupLayout PanelHeaderLayout = new javax.swing.GroupLayout(PanelHeader);
        PanelHeader.setLayout(PanelHeaderLayout);
        PanelHeaderLayout.setHorizontalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelHeaderLayout.setVerticalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
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

        MenuPrincipal.add(jMenu1);

        jMenu2.setText("Amarrages");

        MIPlaisance.setText("Plaisance");
        MIPlaisance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIPlaisanceActionPerformed(evt);
            }
        });
        jMenu2.add(MIPlaisance);

        MIPeche.setText("Pêche");
        MIPeche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIPecheActionPerformed(evt);
            }
        });
        jMenu2.add(MIPeche);

        MenuPrincipal.add(jMenu2);

        jMenu3.setText("Bateaux");

        MIListeComplete.setText("Liste complète");
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

        MenuPrincipal.add(jMenu3);

        jMenu4.setText("Personnel");

        MIEquipageBateau.setText("Équipage d'un bateau");
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

        MenuPrincipal.add(jMenu4);

        jMenu6.setText("Paramètres");

        MIFormatDate.setText("Format date");
        MIFormatDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIFormatDateActionPerformed(evt);
            }
        });
        jMenu6.add(MIFormatDate);

        MIFichierLog.setText("Fichier log");
        MIFichierLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIFichierLogActionPerformed(evt);
            }
        });
        jMenu6.add(MIFichierLog);

        MIAffichageDateHeureCourante.setSelected(true);
        MIAffichageDateHeureCourante.setText("Affichage date heure du démarrage");
        MIAffichageDateHeureCourante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIAffichageDateHeureCouranteActionPerformed(evt);
            }
        });
        jMenu6.add(MIAffichageDateHeureCourante);

        MenuPrincipal.add(jMenu6);

        jMenu5.setText("À propos");

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

        MenuPrincipal.add(jMenu5);

        setJMenuBar(MenuPrincipal);

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
                                .addComponent(LOnOff))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LBEmplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEnvoyerEmplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnChoisirEmplacement))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
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
            .addComponent(PanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnDemarrerServeur)
                    .addComponent(jLabel2)
                    .addComponent(LOnOff)
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
                            .addComponent(LBEmplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if (this.networkBS.isDisconnected()) {
            try {
                this.networkBS.connect();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            this.networkBS.disconnect();
        }
    }//GEN-LAST:event_BtnDemarrerServeurActionPerformed

    private void BtnEnvoyerConfirmationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEnvoyerConfirmationActionPerformed
        if (this.networkBS.isConnected()) {
            int selectedIndex = this.LBBateauEntresDansLaRade.getSelectedIndex();
            if (selectedIndex != -1) {
                Bateau bateau = (Bateau) ((DefaultListModel) this.LBBateauEntresDansLaRade.getModel()).getElementAt(selectedIndex);
                try {
                    String xmlBateau = XMLFormatter.toXML(bateau);
                    // Envois bateau dans phare
                    Frame.send(this.networkBS, new String[]{"phare_ajouter_bateau_liste", "confirmation_capitainerie", xmlBateau});

                    // Retrait bateau capitainerie
                    this.bateauEntresDansLaRade.remove(bateau);
                    ((DefaultListModel) this.LBBateauEntresDansLaRade.getModel()).removeElement(bateau);

                    this.bateauEnCoursDAmarrage.add(bateau);
                    ((DefaultListModel) this.LBBateauEnCoursDAmarrage.getModel()).addElement(bateau);
                } catch (Exception e) {
                    System.err.println("[Capitainerie | Error] \"" + e.getMessage() + "\"");
                }
            }
        } else {
            DialogErreur de = new DialogErreur("Erreur", "Le serveur n'est pas allumé. Attention à ce que le phare le soit aussi!");
            de.setVisible(true);
        }
    }//GEN-LAST:event_BtnEnvoyerConfirmationActionPerformed

    private void BtnEnvoyerEmplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEnvoyerEmplacementActionPerformed
        if (this.networkBS.isConnected()) {
            int selectedIndex = this.LBBateauAttenteEntrer.getSelectedIndex();
            boolean trouve = false;
            if (selectedIndex != -1) {
                Bateau bateau = (Bateau) ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).getElementAt(selectedIndex);
                if (bateau instanceof BateauPeche) {
                    for (int i = 0; i < quais.size() && !trouve; i++) {
                        for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length && !trouve; j++) {
                            BateauPeche tempBateauPeche = (BateauPeche) quais.get(i).getListeBateauxAmarres()[j];
                            trouve = (tempBateauPeche != null && bateau.equals(tempBateauPeche));
                        }
                    }
                } else {
                    for (int i = 0; i < this.pontons.size() && !trouve; i++) {
                        for (int j = 0; j < this.pontons.get(i).getListe(1).length && !trouve; j++) {
                            BateauPlaisance tempBateauPlaisance = (BateauPlaisance) this.pontons.get(i).getListe(1)[j];
                            trouve = (tempBateauPlaisance != null && bateau.equals(tempBateauPlaisance));
                        }
                        for (int j = 0; j < this.pontons.get(i).getListe(2).length && !trouve; j++) {
                            BateauPlaisance tempBateauPlaisance = (BateauPlaisance) this.pontons.get(i).getListe(2)[j];
                            trouve = (tempBateauPlaisance != null && bateau.equals(tempBateauPlaisance));
                        }
                    }
                }
                if (trouve) {
                    try {
                        String xmlBateau = XMLFormatter.toXML(bateau);
                        // Envois bateau dans phare
                        Frame.send(this.networkBS, new String[]{"phare_ajouter_bateau_liste", "reponse_capitainerie", xmlBateau});

                        // Retrait bateau capitainerie
                        this.bateauAttenteEntrer.remove(bateau);
                        ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).removeElement(bateau);
                    } catch (Exception e) {
                        System.err.println("[Capitainerie | Error] \"" + e.getMessage() + "\"");
                    }
                } else {
                    DialogErreur de = new DialogErreur("Erreur", "Aucun emplacement n'a été choisis pour ce bateau");
                    de.setVisible(true);
                }
            } else {
                DialogErreur de = new DialogErreur("Erreur", "Aucun bateau n'a été choisis");
                de.setVisible(true);
            }
        } else {
            DialogErreur de = new DialogErreur("Erreur", "Le serveur n'est pas allumé. Attention à ce que le phare le soit aussi!");
            de.setVisible(true);
        }
    }//GEN-LAST:event_BtnEnvoyerEmplacementActionPerformed

    private void MILoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MILoginActionPerformed
        if (!IsLoggedIn()) {
            Connexion connexion = new Connexion();
            connexion.setVisible(true);
            dispose();
        } else {
            new CapitainerieException("Erreur.", "Vous devez d'abord vous déconnecter de la capitainerie.");
        }
    }//GEN-LAST:event_MILoginActionPerformed

    private void MILogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MILogoutActionPerformed
        // Désactivation des boutons
        BtnDemarrerServeur.setEnabled(false);
        BtnEnvoyerEmplacement.setEnabled(false);
        BtnChoisirEmplacement.setEnabled(false);
        BtnRemplirInformationsBateau.setEnabled(false);
        BtnEnvoyerConfirmation.setEnabled(false);

        // Désactivation des listbox
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
            Bateau bateauQP = null;
            if (bateau instanceof BateauPeche) {
                for (int i = 0; i < this.quais.size(); i++) {
                    for (int j = 0; j < this.quais.get(i).getListeBateauxAmarres().length; j++) {
                        if (bateau.equals(this.quais.get(i).getListeBateauxAmarres()[j])) {
                            bateauQP = (Bateau) this.quais.get(i).getListeBateauxAmarres()[j];
                            i = this.quais.size();
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.pontons.size(); i++) {
                    for (int j = 0; j < this.pontons.get(i).getListe(1).length; j++) {
                        if (bateau.equals(this.pontons.get(i).getListe(1)[j])) {
                            bateauQP = (Bateau) this.pontons.get(i).getListe(1)[j];
                            i = this.pontons.size();
                            break;
                        }
                    }
                    if (i < this.pontons.size()) { // Si le bateau n'a pas été trouvé du côté 1 (Droite)
                        for (int j = 0; j < this.pontons.get(i).getListe(2).length; j++) {
                            if (bateau.equals(this.pontons.get(i).getListe(2)[j])) {
                                bateauQP = (Bateau) this.pontons.get(i).getListe(2)[j];
                                i = this.pontons.size();
                                break;
                            }
                        }
                    }
                }
            }
            String emplacement = this.getEmplacementBateau(bateau);
            try {
                RemplirInfoBateau remplirInfoBateau = new RemplirInfoBateau(this, true, bateau, bateauQP, emplacement);
                remplirInfoBateau.setTitle("Ajout informations du bateau  " + bateau.getNom() + " ");
                remplirInfoBateau.setVisible(true);
            } catch (SailorIndicatedIsNotACaptainException | SailorIndicatedIsNotASecondException | ShipWithoutIdentificationException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BtnRemplirInformationsBateauActionPerformed
    
    /**
     * @param bateauAAjouter copie du bateauARetirer
     * @param bateauARetirer la référence du bateau initialement envoyé dans RemplirInfoBateau
     */
    public void ajouterBateauListeBateauAmmare(Bateau bateauAAjouter, Bateau bateauARetirer) {
        this.bateauEnCoursDAmarrage.remove(bateauARetirer);
        ((DefaultListModel) this.LBBateauEnCoursDAmarrage.getModel()).removeElement(bateauARetirer);
        ((DefaultListModel) this.LBBateauxAmarres.getModel()).addElement(bateauAAjouter);
        this.bateauxAmarres.add(bateauAAjouter);
    }
    
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

    private void BtnChoisirEmplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChoisirEmplacementActionPerformed
        int selectedIndex = this.LBBateauAttenteEntrer.getSelectedIndex();
        if (selectedIndex != -1) {
            Bateau bateau = this.bateauAttenteEntrer.get(selectedIndex);
            ChoisirEmplacementBateau choisirEmplacement;
            if (bateau instanceof BateauPeche) {
                choisirEmplacement = new ChoisirEmplacementBateau(this, true, bateau, quais);
            } else {
                choisirEmplacement = new ChoisirEmplacementBateau(this, true, bateau, pontons, 1);
            }
            choisirEmplacement.setAlwaysOnTop(true);
            choisirEmplacement.setVisible(true);
        }
    }//GEN-LAST:event_BtnChoisirEmplacementActionPerformed

    private void LBBateauAttenteEntrerValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LBBateauAttenteEntrerValueChanged
        int selectedIndex = this.LBBateauAttenteEntrer.getSelectedIndex();
        if (selectedIndex != -1) {
            Bateau bateau = (Bateau) ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).getElementAt(selectedIndex);
            this.LBEmplacement.setText(this.getEmplacementBateau(bateau));
        }
    }//GEN-LAST:event_LBBateauAttenteEntrerValueChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String sep = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + sep + "Donnees" + sep + "capitainerie" + sep;
        File directory = new File(path);
        if (!directory.exists()) {
            path = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + "capitainerie" + sep;
            directory = new File(path);
            if (!directory.exists()) {
                return;
            }
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "quais.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.quais);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "pontons.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.pontons);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "bateauAttenteEntrer.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauAttenteEntrer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(path + "bateauEnCoursDAmarrage.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauEnCoursDAmarrage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "bateauEntresDansLaRade.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauEntresDansLaRade);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try (FileOutputStream fos = new FileOutputStream(path + "bateauAmarre.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.bateauxAmarres);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        FichierLog fl = new FichierLog();
        fl.ecrireLigne("La Capintainerie a été fermée.");
    }//GEN-LAST:event_formWindowClosing

    private void MIFichierLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIFichierLogActionPerformed
        Parametres_FichierLog fl = new Parametres_FichierLog();
        fl.setTitle("Tous les logs");
        fl.setVisible(true);
    }//GEN-LAST:event_MIFichierLogActionPerformed

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
    private javax.swing.JLabel LBEmplacement;
    private javax.swing.JLabel LDateHeure;
    private javax.swing.JLabel LOnOff;
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
    private javax.swing.JMenuBar MenuPrincipal;
    private javax.swing.JPanel PanelHeader;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
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

    public String getEmplacementBateau(Bateau toFind) {
        if (toFind instanceof BateauPlaisance) {
            for (int i = 0; i < pontons.size(); i++) {
                for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {
                    if (pontons.get(i).getListe(1)[j] != null) {
                        System.err.println(pontons.get(i).getListe(1)[j]);
                        if (toFind.equals(pontons.get(i).getListe(1)[j])) {
                            return "P" + i + "1*" + j;
                        }
                    }
                }
                for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                    if (pontons.get(i).getListe(2)[j] != null) {
                        if (toFind.equals(pontons.get(i).getListe(2)[j])) {
                            return "P" + i + "2*" + j;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < quais.size(); i++) {
                for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length; j++) {
                    if (quais.get(i).getListeBateauxAmarres()[j] != null) {
                        if (toFind.equals(quais.get(i).getListeBateauxAmarres()[j])) {
                            return "Q" + "1*" + j;
                        }
                    }
                }
            }
        }
        return "Aucun";
    }

    // IInOutEvent
    @Override
    public String getMessage() {
        return this.networkBS.getMessage();
    }

    @Override
    public String readMessage() {
        return this.networkBS.readMessage();
    }

    @Override
    public void sendMessage(String message) {
        this.networkBS.sendMessage(message);
    }

    @Override
    public void messageReceived() {
        String message = this.getMessage();
        System.out.println("[Capitainerie | Info] [<<<] " + message);
        try {
            Frame frame = (Frame) XMLFormatter.fromXML(message);
            String action = frame.getAction();
            if (action.equals("capitainerie_ajouter_bateau_liste")) {
                String list = frame.getArg(1);
                String objectXml = XMLFormatter.decode(frame.getArg(2));
                Object object = XMLFormatter.fromXML(objectXml);
                if (list.equals("bateau_attente_entree")) {
                    if (object instanceof Bateau) {
                        ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).addElement((Bateau) object);
                        this.bateauAttenteEntrer.add((Bateau) object);
                    } else {
                        System.err.println("[Capitainerie | Error] Couldn't cast object received as \"Bateau\"");
                    }
                } else if (list.equals("bateau_entre_rade")) {

                    //Ajouter dans bateau entre dans la rade
                    ((DefaultListModel) this.LBBateauEntresDansLaRade.getModel()).addElement((Bateau) object);
                    this.bateauEntresDansLaRade.add((Bateau) object);

                }
            } else if (action.equals("capitainerie_supprimer_bateau_liste")) {

            }
        } catch (Exception e) {
            System.err.println("[Capitainerie | Error] \"" + e.getMessage() + "\"");
        }
    }

    public void modifierLBEmplacement(String emp) {
        this.LBEmplacement.setText(emp);
    }

}
