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
        // System.out.println(tempBateauPlaisance);
        tempBateauPlaisance.setEquipage(equipageTemporaire);
        bateauAttenteEntrer.add(tempBateauPlaisance);
        bateauEntresDansLaRade.add(tempBateauPlaisance);
        bateauEnCoursDAmarrage.add(tempBateauPlaisance);
        pontons.get(0).addMTSE(tempBateauPlaisance);

        BateauPeche tempBateauPeche = new BateauPeche("Bateaupeche" + i, "Liege", 100, 10, BateauPeche.TypeDePeche.Thonier, "FR");
        // System.out.println(tempBateauPeche);
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