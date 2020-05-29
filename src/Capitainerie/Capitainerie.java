package Capitainerie;

import Classes.*;
import Exceptions.CapitainerieException;
import Exceptions.ShipWithoutIdentificationException;
import Network.Frame;
import Network.IInOutEvent;
import Network.NetworkBasicServer;
import Network.XMLFormatter;
import Utilisateurs.Connexion;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Florent & Wadi
 */
public class Capitainerie extends javax.swing.JFrame implements IInOutEvent, Serializable {

    private boolean isLoggedIn = false;

    private NetworkBasicServer networkBS;
    private int PORT = 50000;

    public LinkedList<Bateau> bateauAttenteEntrer = new LinkedList<Bateau>();
    public LinkedList<Bateau> bateauEnCoursDAmarrage = new LinkedList<Bateau>();
    public LinkedList<Bateau> bateauEntresDansLaRade = new LinkedList<Bateau>();
    public LinkedList<Bateau> bateauAmarre = new LinkedList<Bateau>();

    public LinkedList<Ponton> pontons = new LinkedList<Ponton>();
    public LinkedList<Quai> quais = new LinkedList<Quai>();
    public Properties properties;

    /**
     * Creates new form Capitainerie
     */
    public Capitainerie() {
        initComponents();

        try {
            this.networkBS = new NetworkBasicServer(PORT, this, this.LOnOff, this.BtnDemarrerServeur);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Date now = new Date();
        setDateHeure(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE).format(now));

        this.LBBateauAttenteEntrer.setModel(new DefaultListModel());
        this.LBBateauEnCoursDAmarrage.setModel(new DefaultListModel());
        this.LBBateauEntresDansLaRade.setModel(new DefaultListModel());
        this.LBBateauxAmarres.setModel(new DefaultListModel());

        try {
            this.properties = Fonctions.chargerConfig();
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.PORT = Integer.parseInt(this.properties.getProperty("port.ecoute"));
        /*
        
        this.pontons.add(new Ponton("1", 12, 15.0));
        this.pontons.add(new Ponton("2", 12, 15.0));
        
        this.quais.add(new Quai("1", 6, 15.0));
        this.quais.add(new Quai("2", 6, 15.0));
        
        

        this.LBBateauAttenteEntrer.setModel(new DefaultListModel());
        this.LBBateauEnCoursDAmarrage.setModel(new DefaultListModel());
        this.LBBateauEntresDansLaRade.setModel(new DefaultListModel());
        this.LBBateauxAmarres.setModel(new DefaultListModel());

        BateauPlaisance tempBateauPlaisance = null;
        BateauPeche tempBateauPeche = null;
        try {
            tempBateauPlaisance = new BateauPlaisance("Bateau", "Exeter", 200, 5, BateauPlaisance.TypePermis.PlaisanceExtentionHauturiere, "BE");
            tempBateauPeche = new BateauPeche("BateauPeche", "Liege", 100, 10, BateauPeche.TypeDePeche.Thonier, "FR");

            Marin capitaine = new Marin("Mokh", "Wad", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Capitaine);
            Marin second = new Marin("Flo", "Bann", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Second);
            Marin bosco = new Marin("Air", "29", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Bosco);
            Marin mecanicien = new Marin("Oussama", "Achour", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.MaitreMecanicien);
            Equipage equipage = new Equipage(capitaine, second);
            equipage.getMarins().add(bosco);
            equipage.getMarins().add(mecanicien);
            tempBateauPlaisance.setEquipage(equipage);
            tempBateauPeche.setEquipage(equipage);
        } catch (Exception ex) {
            Logger.getLogger(Capitainerie.class.getName()).log(Level.SEVERE, null, ex);
        }

        bateauAttenteEntrer.add(tempBateauPlaisance);
        bateauAttenteEntrer.add(tempBateauPeche);
        ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).addElement(tempBateauPlaisance);
        ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).addElement(tempBateauPeche);
         */
    }

    private void init() {
        String sep = System.getProperty("file.separator");
        String file = System.getProperty("user.dir") + sep + "Donnees" + sep;
        File dossier = new File(file);
// le "fichier" existe et est un dossier 
        if (dossier.exists() && dossier.isDirectory()) {

        } else {
            file = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep;
            dossier = new File(file);
            if (dossier.exists() && dossier.isDirectory()) {

            } else {
            }
        }

        try {

            FileInputStream fileIn = new FileInputStream(file + "bateauAttenteEntrer.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.bateauAttenteEntrer = (LinkedList<Bateau>) objectIn.readObject();

            for (int i = 0; i < this.bateauAttenteEntrer.size(); i++) {
                ((DefaultListModel) this.LBBateauAttenteEntrer.getModel()).addElement(this.bateauAttenteEntrer.get(i));
            }

            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {

            FileInputStream fileIn = new FileInputStream(file + "bateauEnCoursDAmarrage.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.bateauEnCoursDAmarrage = (LinkedList<Bateau>) objectIn.readObject();
            for (int i = 0; i < this.bateauEnCoursDAmarrage.size(); i++) {
                ((DefaultListModel) this.LBBateauEnCoursDAmarrage.getModel()).addElement(this.bateauEnCoursDAmarrage.get(i));
            }
            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {

            FileInputStream fileIn = new FileInputStream(file + "bateauEntresDansLaRade.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.bateauEntresDansLaRade = (LinkedList<Bateau>) objectIn.readObject();

            for (int i = 0; i < this.bateauEntresDansLaRade.size(); i++) {
                ((DefaultListModel) this.LBBateauEntresDansLaRade.getModel()).addElement(this.bateauEntresDansLaRade.get(i));
            }
            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {

            FileInputStream fileIn = new FileInputStream(file + "bateauAmarre.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.bateauAmarre = (LinkedList<Bateau>) objectIn.readObject();

            for (int i = 0; i < this.bateauAmarre.size(); i++) {
                ((DefaultListModel) this.LBBateauxAmarres.getModel()).addElement(this.bateauAmarre.get(i));
            }

            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {

            FileInputStream fileIn = new FileInputStream(file + "pontons.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.pontons = (LinkedList<Ponton>) objectIn.readObject();

            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {

            FileInputStream fileIn = new FileInputStream(file + "quais.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.quais = (LinkedList<Quai>) objectIn.readObject();

            objectIn.close();

        } catch (Exception ex) {
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

        LDateHeure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LDateHeure.setText("...");

        BtnDemarrerServeur.setText("D�marrer le serveur");
        BtnDemarrerServeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDemarrerServeurActionPerformed(evt);
            }
        });

        jLabel2.setText("�tat du serveur:");

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

        jLabel5.setText("Bateau(x) amarr�(s) :");

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

        MIPeche.setText("P?che");
        MIPeche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIPecheActionPerformed(evt);
            }
        });
        jMenu2.add(MIPeche);

        MenuPrincipal.add(jMenu2);

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

        MenuPrincipal.add(jMenu3);

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

        MenuPrincipal.add(jMenu4);

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

        MenuPrincipal.add(jMenu6);

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
        int selectedIndex = this.LBBateauEntresDansLaRade.getSelectedIndex();
        boolean trouve = false;

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
    }//GEN-LAST:event_BtnEnvoyerConfirmationActionPerformed

    private void BtnEnvoyerEmplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEnvoyerEmplacementActionPerformed
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
                DialogErreur de = new DialogErreur("Erreur", "Aucun emplacement n'a �t� choisis pour ce bateau");
                de.setVisible(true);
            }
        } else {
            DialogErreur de = new DialogErreur("Erreur", "Aucun bateau n'a �t� choisis");
            de.setVisible(true);
        }
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
            Bateau bateauAEnvoyer = null;
            if (bateau instanceof BateauPeche) {

                for (int i = 0; i < this.quais.size(); i++) {
                    for (int j = 0; j < this.quais.get(i).getListeBateauxAmarres().length; j++) {
                        if (bateau.equals(this.quais.get(i).getListeBateauxAmarres()[j])) {
                            bateauAEnvoyer = (Bateau) this.quais.get(i).getListeBateauxAmarres()[j];
                            i = this.quais.size();
                            break;
                        }
                    }
                }

            } else {
                for (int i = 0; i < this.pontons.size(); i++) {
                    for (int j = 0; j < this.pontons.get(i).getListe(1).length; j++) {
                        if (bateau.equals(this.pontons.get(i).getListe(1)[j])) {
                            bateauAEnvoyer = (Bateau) this.pontons.get(i).getListe(1)[j];
                        }
                    }
                    for (int j = 0; j < this.pontons.get(i).getListe(2).length; j++) {
                        if (bateau.equals(this.pontons.get(i).getListe(2)[j])) {
                            bateauAEnvoyer = (Bateau) this.pontons.get(i).getListe(2)[j];
                        }
                    }
                }

            }
            String emplacement = this.getEmplacementBateau(bateau);
            RemplirInfoBateau remplirInfoBateau = new RemplirInfoBateau(this, true, bateauAEnvoyer, emplacement);
            remplirInfoBateau.setTitle("Ajout informations du bateau  " + bateau.getNom() + " ");
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
            this.LBEmplacement.setText(this.getEmplacementMTSE(bateau));
        }
    }//GEN-LAST:event_LBBateauAttenteEntrerValueChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //private LinkedList<Bateau> bateauAttenteEntrer = new LinkedList<Bateau>();
        //private LinkedList<Bateau> bateauEnCoursDAmarrage = new LinkedList<Bateau>();
        //private LinkedList<Bateau> bateauEntresDansLaRade = new LinkedList<Bateau>();
        //private LinkedList<Ponton> pontons = new LinkedList<Ponton>();
        //private LinkedList<Quai> quais = new LinkedList<Quai>();

        String sep = System.getProperty("file.separator");
        String file = System.getProperty("user.dir") + sep + "Donnees" + sep;
        File dossier = new File(file);
// le "fichier" existe et est un dossier 
        if (dossier.exists() && dossier.isDirectory()) {

        } else {
            file = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep;
            dossier = new File(file);
            if (dossier.exists() && dossier.isDirectory()) {

            } else {
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file + "quais.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.quais);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file + "pontons.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.pontons);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file + "bateauAttenteEntrer.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.bateauAttenteEntrer);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file + "bateauEnCoursDAmarrage.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.bateauEnCoursDAmarrage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file + "bateauEntresDansLaRade.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.bateauEntresDansLaRade);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file + "bateauAmarre.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.bateauAmarre);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_formWindowClosing

    public void ajouterBateauListeBateauAmmare(Bateau bateau) {
        this.bateauEnCoursDAmarrage.remove(bateau);
        ((DefaultListModel) this.LBBateauEnCoursDAmarrage.getModel()).removeElement(bateau);
        ((DefaultListModel) this.LBBateauxAmarres.getModel()).addElement(bateau);
        this.bateauAmarre.add(bateau);
    }

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
        return "Aucun";
    }

    public String getEmplacementBateau(Bateau mtse) {

        if (mtse instanceof BateauPlaisance) {
            for (int i = 0; i < pontons.size(); i++) {
                for (int j = 0; j < pontons.get(i).getListe(1).length; j++) {

                    if (pontons.get(i).getListe(1)[j] != null) {
                        System.err.println(pontons.get(i).getListe(1)[j]);
                        if (pontons.get(i).getListe(1)[j].equals(mtse)) {
                            return "P" + i + "1*" + j;
                        }
                    }

                }
                for (int j = 0; j < pontons.get(i).getListe(2).length; j++) {
                    if (pontons.get(i).getListe(2)[j] != null) {
                        if (pontons.get(i).getListe(2)[j].equals(mtse)) {
                            return "P" + i + "2*" + j;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < quais.size(); i++) {
                for (int j = 0; j < quais.get(i).getListeBateauxAmarres().length; j++) {
                    if (quais.get(i).getListeBateauxAmarres()[j] != null) {
                        if (quais.get(i).getListeBateauxAmarres()[j].equals(mtse)) {
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
        System.out.println("[Capitainerie | Info] [<<<] " + this.readMessage());
        try {
            Frame frame = (Frame) XMLFormatter.fromXML(this.getMessage());

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
