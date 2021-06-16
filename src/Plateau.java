import MG2D.Couleur;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Ligne;
import MG2D.geometrie.Point;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Représente un plateau où l'on peut placer des pions
 *
 * Auteurs :
 *
 * BERNARD Manon
 * BOURRE Maxime
 * BUTELLE Dorine
 * VASSEUR Maxence
 * DELSART Eloise
 * MARTIN Lucas
 * */

public class Plateau extends Acteur {


    // Trigger appelé lorsqu'un pion est placé sur le plateau
    public interface OnPionPlaceListener {
        void onPionPlaceListener(Case casePlacement, Pion pion);
    }

    ArrayList<OnPionPlaceListener> onPionPlaceListeners = new ArrayList<>();

    // Grille de jeu
    Grille grille = null;

    /**
     * Constructeur par défaut de Plateau
     */

    public Plateau(){

        super(new Vecteur2<>(0, 0));
    }

    /**
     * Constructeur Plateau
     * @param position - Position plateau
     */
    public Plateau(Vecteur2<Integer> position){

        super(position);

    }

    /**
     * Getter grille
     * @return - Retourne une référence vers la grille du plateau
     */
    public Grille getGrille() {
        return grille;
    }


    /**
     * Lorsque le plateau est cliqué
     * @param event - Evennement AWT
     */
    @Override
    public void onClique(MouseEvent event) {


        // Coordoonées relatives au plateau
        Vecteur2<Integer> localCoords = new Vecteur2<>(
                Math.floorDiv(event.getX() - getPosition().getX() + (getGrille().getLargeurCases() / 2), getGrille().getLargeurCases()),
                Math.floorDiv((Jeu.getInstance().getFenetre().getHeight() - (getPosition().getY()) - event.getY() + (getGrille().getHauteurCases() / 2)), getGrille().getHauteurCases())
        );

        // On Clamp la valeur sur X et Y pour éviter les erreurs Out of Bounds
        localCoords.setX(Math.min(Math.max(localCoords.getX(), 0), getGrille().getMatrice().size() - 1));
        localCoords.setY(Math.min(Math.max(localCoords.getY(), 0), getGrille().getMatrice().get(0).size() - 1));



        Case caseActuelle = getGrille().getCase(localCoords);

        // Evite 2 pions sur la même case, pas gênant vu qu'on ne peut placer qu'un objet par case
        if(caseActuelle.getObjets().size() > 0)
            return;


        // Va créer un nouveau pion sur le plateau et met la couleur du joueur
        Pion pion = new Pion(
                new Vecteur2<>(
                        getGrille().getPosition().getX() + caseActuelle.getPosition().getX() * getGrille().getLargeurCases(),
                        getGrille().getPosition().getY() + caseActuelle.getPosition().getY() * getGrille().getHauteurCases()
                ),
                Jeu.getInstance().getJoueurActuel().getCouleur()
        );

        getGrille().ajouter(
                caseActuelle.getPosition(),
                pion
        );

        pion.ajouterComposant(PosableComposant.class);

        pion.getComposant(PosableComposant.class).setPosition(caseActuelle.getPosition());

        Jeu.getInstance().dessiner(pion.dessiner());

        this.onPionPlaceListeners.forEach(c -> c.onPionPlaceListener(caseActuelle, pion));


        Jeu.getInstance().getJoueursQueue().add(Jeu.getInstance().getJoueurActuel());
        Jeu.getInstance().setJoueurActuel(Jeu.getInstance().getJoueursQueue().poll());

    }

    /**
     * Appelé lorseque le plateau est ajouté à la scène
     * @param caseActuelle - Case sur laquelle le plateau a été ajoutée
     */
    @Override
    public void onAjoute(Case caseActuelle) {

        // On va créer une grille de 19 + 1 et cacher le dernier carré afin de pouvoir poser des pions sur les côtés
        this.grille = new Grille(
                getPosition(),
                caseActuelle.getLargeur(),
                caseActuelle.getHauteur(),
                20,20);


        getGrille().getPosition().setX(
                // Moitié de largeur de la case - ( largeur de la grille ( en enlevant une case ) / 2 )
                (caseActuelle.getPositionEcran().getX() + caseActuelle.getLargeur() / 2 ) - (getGrille().getLargeur() - getGrille().getLargeurCases()) / 2
        );

        getPosition().setX(getGrille().getPosition().getX());

        getGrille().getPosition().setY(
                (caseActuelle.getPositionEcran().getY() + caseActuelle.getHauteur() / 2) - (getGrille().getHauteur() - getGrille().getHauteurCases()) / 2
         );

        getPosition().setY(getGrille().getPosition().getY());



        grille.setCouleurLignes(Couleur.NOIR, Couleur.NOIR);
        grille.setMode(Grille.Mode.INTERSECTION);


    }


    /**
     * Permet de dessiner graphiquement le plateau
     * @return - Retourne les dessins attachés au plateau
     */
    @Override
    public ArrayList<Dessin> dessiner() {

        for(int x = 0; x < getGrille().getMatrice().size() - 1; x++) {
            for (int y = 0; y < getGrille().getMatrice().size() - 1; y++) {


                Case caseActuelle = getGrille().getMatrice().get(x).get(y);

                // Dessin lignes horizontales
                Point s1 = new Point(
                        x * caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur()
                );

                s1.setX(s1.getX() + getPosition().getX());
                s1.setY(s1.getY() + getPosition().getY());

                Point t1 = new Point(
                        x * caseActuelle.getLargeur() + caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur());

                t1.setX(t1.getX() + getPosition().getX());
                t1.setY(t1.getY() + getPosition().getY());

                Ligne l = new Ligne(s1, t1);
                l.setCouleur(Couleur.NOIR);

                dessins.add(l);
                // Dessin lignes verticales
                Point s2 = new Point(
                        x * caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur()
                );

                s2.setX(s2.getX() + getPosition().getX());
                s2.setY(s2.getY() + getPosition().getY());

                Point t2 = new Point(
                        x * caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur() + caseActuelle.getHauteur());

                t2.setX(t2.getX() + getPosition().getX());
                t2.setY(t2.getY() + getPosition().getY());

                l = new Ligne(s2, t2);
                l.setCouleur(Couleur.NOIR);

                dessins.add(l);
            }
        }

        Ligne derniereLigneHorizontale = new Ligne(
            new Point(
                getGrille().getPosition().getX(),
                getGrille().getPosition().getY() + (getGrille().getMatrice().get(0).size() - 1) * getGrille().getHauteurCases()
            ),

            new Point(
                getGrille().getPosition().getX() + ( getGrille().getMatrice().size() - 1) * getGrille().getLargeurCases(),
                getGrille().getPosition().getY() + (getGrille().getMatrice().get(0).size() - 1) * getGrille().getHauteurCases()

            )
        );


        Ligne derniereLigneVerticale = new Ligne(

            new Point(
                getGrille().getPosition().getX() + (getGrille().getMatrice().size() - 1) * getGrille().getLargeurCases(),
                getGrille().getPosition().getY() + (getGrille().getMatrice().get(0).size() - 1)* getGrille().getHauteurCases()

            ),

            new Point(
                getGrille().getPosition().getX() + (getGrille().getMatrice().size() - 1) * getGrille().getLargeurCases(),
                getGrille().getPosition().getY()
            )

        );

        dessins.add(derniereLigneHorizontale);
        dessins.add(derniereLigneVerticale);


        return dessins;
    }


    /**
     * Détecte si il y a une capture en diagonale droite
     * @param casePlacement - Référence de la classe où le pion a été placé
     */
    public void detecterCaptureDiagonaleDroite(Case casePlacement){

        LinkedList<Acteur> candidats = new LinkedList<>();

        // capture diagonale droite
        for(int i = -3; i < 4; i++){

            // Check Out of Bounds bords à gauche
            if(casePlacement.getPosition().getX() + i < 0 ||
                    casePlacement.getPosition().getY() + i < 0)
                continue;

            // Check Out of Bounds bords à droite
            if(casePlacement.getPosition().getX() + i > this.getGrille().getMatrice().get(0).size() - 1 ||
                    casePlacement.getPosition().getY() + i > this.getGrille().getMatrice().get(0).size() - 1)
                continue;


            Case diagonaleGauche = this.getGrille().getCase(
                    casePlacement.getPosition().getX() + i,
                    casePlacement.getPosition().getY() + i
            );

            //System.out.println(diagonaleGauche.getPosition().getX() + ", " + diagonaleGauche.getPosition().getY());

            try{

                candidats.add(diagonaleGauche.getObjets().get(0));


                //System.out.println(i);

                // Si on arrive à la case cliquée ou à la dernière case de la diagonale gauche...
                if(diagonaleGauche.getPosition().equals(casePlacement.getPosition()) || i == 3) {

                    //System.out.println(candidats);

                    Acteur debut = i == 3 ? candidats.pollLast() : candidats.pollFirst();

                    if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur()){
                        //System.out.println("ok");

                        ArrayList<Acteur> aEnlever = new ArrayList<>();

                        // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                        while(!candidats.isEmpty()){
                            Acteur act = candidats.poll();

                            if (act == null) break;

                            if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                                aEnlever.add(act);
                        }

                        // Enfin on supprime les pions si capture valide en Y
                        if(aEnlever.size() == 2) {

                            Jeu.getInstance().ajouterLog(
                                    String.format("Joueur %s %s a capturé des pions en diagonale droite à %s %s",
                                            Jeu.getInstance().getJoueurActuel().getNom(),
                                            Jeu.getInstance().getJoueurActuel().getPrenom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getPrenom())
                            );

                            Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);
                            aEnlever.forEach(p -> {
                                Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                                Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                                this.getGrille().supprimer(
                                        p.getComposant(PosableComposant.class).getPosition(),
                                        p
                                );
                                Jeu.getInstance().getFenetre().supprimer(p.getDessins().get(0));

                            });
                        }

                    }

                    candidats = new LinkedList<>();
                }

            } catch(IndexOutOfBoundsException ex){

                candidats.add(null);
            }

        }
    }

    /**
     * Détecte si il y a une capture en diagonale gauche
     * @param casePlacement - Référence de la classe où le pion a été placé
     */
    public void detecterCaptureDiagonaleGauche(Case casePlacement){
        LinkedList<Acteur> candidats = new LinkedList<>();

        // capture diagonale gauche
        for(int i = -3; i < 4; i++){


            // Check Out of Bounds bords à gauche
            if(casePlacement.getPosition().getX() - i < 0 ||
                    casePlacement.getPosition().getY() + i < 0)
                continue;

            // Check Out of Bounds bords à droite
            if(casePlacement.getPosition().getX() - i > this.getGrille().getMatrice().get(0).size() - 1 ||
                    casePlacement.getPosition().getY() + i > this.getGrille().getMatrice().get(0).size() - 1)
                continue;

            Case diagonaleGauche = this.getGrille().getCase(
                    casePlacement.getPosition().getX() - i,
                    casePlacement.getPosition().getY() + i
            );

            //System.out.println(diagonaleGauche.getPosition().getX() + ", " + diagonaleGauche.getPosition().getY());

            try{

                candidats.add(diagonaleGauche.getObjets().get(0));


                //System.out.println(i);
                if(diagonaleGauche.getPosition().equals(casePlacement.getPosition()) || i == 3) {

                    //System.out.println(candidats);

                    // Si fin de la diagonale prendre le dernier pion sinon si milieu de la diago prendre le premier pion
                    Acteur debut = i == 3 ? candidats.pollLast() : candidats.pollFirst();

                    if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur()){
                       // System.out.println("ok");

                        ArrayList<Acteur> aEnlever = new ArrayList<>();

                        // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                        while(!candidats.isEmpty()){
                            Acteur act = candidats.poll();

                            if (act == null) break;

                            if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                                aEnlever.add(act);
                        }

                        // Enfin on supprime les pions si capture valide en Y
                        if(aEnlever.size() == 2) {

                            Jeu.getInstance().ajouterLog(
                                    String.format("Joueur %s %s a capturé des pions en diagonale gauche à %s %s",
                                            Jeu.getInstance().getJoueurActuel().getNom(),
                                            Jeu.getInstance().getJoueurActuel().getPrenom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getPrenom())
                            );

                            Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);
                            aEnlever.forEach(p -> {
                                Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                                Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                                this.getGrille().supprimer(
                                        p.getComposant(PosableComposant.class).getPosition(),
                                        p
                                );
                                Jeu.getInstance().getFenetre().supprimer(p.getDessins().get(0));

                            });
                        }

                    }

                    candidats = new LinkedList<>();
                }

            } catch(IndexOutOfBoundsException ex){

                candidats.add(null);
            }

        }
    }

    /**
     * Détecter une capture verticale
     *
     * @param casePlacement - Référence de la classe où le pion a été placé
     * @param pion - Pion placé
     */
    public void detecterCaptureVerticale(Case casePlacement, Pion pion){

        LinkedList<Acteur> candidats = new LinkedList<>();

        // Capture verticale
        for(int i = -3; i < 4; i++){

            // Check Out of Bounds bords à gauche
            if(casePlacement.getPosition().getX() < 0 ||
                    casePlacement.getPosition().getY() + i < 0)
                continue;

            // Check Out of Bounds bords à droite
            if(casePlacement.getPosition().getX() > this.getGrille().getMatrice().get(0).size() - 1 ||
                    casePlacement.getPosition().getY() + i > this.getGrille().getMatrice().get(0).size() - 1)
                continue;


            Case diagonaleGauche = this.getGrille().getCase(
                    casePlacement.getPosition().getX(),
                    casePlacement.getPosition().getY() + i
            );

            //System.out.println(diagonaleGauche.getPosition().getX() + ", " + diagonaleGauche.getPosition().getY());

            try{

                candidats.add(diagonaleGauche.getObjets().get(0));


                //System.out.println(i);

                // Si on arrive à la case cliquée ou à la dernière case de la diagonale gauche...
                if(diagonaleGauche.getPosition().equals(casePlacement.getPosition()) || i == 3) {

                    //System.out.println(candidats);

                    Acteur debut = i == 3 ? candidats.pollLast() : candidats.pollFirst();

                    if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur()){
                        //System.out.println("ok");

                        ArrayList<Acteur> aEnlever = new ArrayList<>();

                        // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                        while(!candidats.isEmpty()){
                            Acteur act = candidats.poll();

                            if (act == null) break;

                            if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                                aEnlever.add(act);
                        }

                        // Enfin on supprime les pions si capture valide en Y
                        if(aEnlever.size() == 2) {

                            Jeu.getInstance().ajouterLog(
                                    String.format("Joueur %s %s a capturé des pions en vertical à %s %s",
                                            Jeu.getInstance().getJoueurActuel().getNom(),
                                            Jeu.getInstance().getJoueurActuel().getPrenom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getPrenom())
                            );

                            Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);
                            aEnlever.forEach(p -> {
                                Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                                Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                                this.getGrille().supprimer(
                                        p.getComposant(PosableComposant.class).getPosition(),
                                        p
                                );
                                Jeu.getInstance().getFenetre().supprimer(p.getDessins().get(0));

                            });
                        }

                    }

                    candidats = new LinkedList<>();
                }

            } catch(IndexOutOfBoundsException ex){

                candidats.add(null);
            }

        }
    }

    /**
     * Détecter une capture horizontale
     * @param casePlacement - Référence de la classe où le pion a été placé
     * @param pion - Pion placé
     */

    public void detecterCaptureHorizontale(Case casePlacement, Pion pion){

        LinkedList<Acteur> candidats = new LinkedList<>();

        // Capture verticale
        for(int i = -3; i < 4; i++){

            // Check Out of Bounds bords à gauche
            if(casePlacement.getPosition().getX() + i < 0 ||
                    casePlacement.getPosition().getY() < 0)
                continue;

            // Check Out of Bounds bords à droite
            if(casePlacement.getPosition().getX() + i > this.getGrille().getMatrice().get(0).size() - 1 ||
                    casePlacement.getPosition().getY() > this.getGrille().getMatrice().get(0).size() - 1)
                continue;


            Case diagonaleGauche = this.getGrille().getCase(
                    casePlacement.getPosition().getX() + i,
                    casePlacement.getPosition().getY()
            );

            //System.out.println(diagonaleGauche.getPosition().getX() + ", " + diagonaleGauche.getPosition().getY());

            try{

                candidats.add(diagonaleGauche.getObjets().get(0));


                //System.out.println(i);

                // Si on arrive à la case cliquée ou à la dernière case de la diagonale gauche...
                if(diagonaleGauche.getPosition().equals(casePlacement.getPosition()) || i == 3) {

                    //System.out.println(candidats);

                    Acteur debut = i == 3 ? candidats.pollLast() : candidats.pollFirst();

                    if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur()){
                        //System.out.println("ok");

                        ArrayList<Acteur> aEnlever = new ArrayList<>();

                        // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                        while(!candidats.isEmpty()){
                            Acteur act = candidats.poll();

                            if (act == null) break;

                            if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                                aEnlever.add(act);
                        }

                        // Enfin on supprime les pions si capture valide en Y
                        if(aEnlever.size() == 2) {

                            Jeu.getInstance().ajouterLog(
                                    String.format("Joueur %s %s a capturé des pions en horizontal à %s %s",
                                            Jeu.getInstance().getJoueurActuel().getNom(),
                                            Jeu.getInstance().getJoueurActuel().getPrenom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                            Jeu.getInstance().getJoueursQueue().peek().getPrenom())
                            );

                            Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);
                            aEnlever.forEach(p -> {
                                Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                                Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                                this.getGrille().supprimer(
                                        p.getComposant(PosableComposant.class).getPosition(),
                                        p
                                );
                                Jeu.getInstance().getFenetre().supprimer(p.getDessins().get(0));

                            });
                        }

                    }

                    candidats = new LinkedList<>();
                }

            } catch(IndexOutOfBoundsException ex){

                candidats.add(null);
            }

        }
    }

    /**
     * Permet de vérifier si il y a une victoire par alignement en vertical ou en horizontal
     * @param casePlacement - Référence de la classe où le pion a été placé
     * @return - Gagné ou pas
     */
    private boolean getVictoireHorizontaleVerticale(Case casePlacement){

        int nbAlignementX = 0;
        int nbAlignementY = 0;

        ArrayList<Acteur> pionsACapturer = new ArrayList<>();

        boolean possibleCapture = false;

        /**
         * Regarde 5 voisins en horizontal, en vertical et en diagonal
         */
        for(int x = Math.max(casePlacement.getPosition().getX() - 5, 0);
            x < Math.min(casePlacement.getPosition().getX() + 5, this.getGrille().getMatrice().size()); x++)
        {


            for (int y = Math.max(casePlacement.getPosition().getY() - 5, 0);
                 y < Math.min(casePlacement.getPosition().getY() + 5, this.getGrille().getMatrice().get(0).size());
                 y++)
            {

                // Check en vertical
                Case caseScannee = this.getGrille().getCase(x, y);

                if (caseScannee.getObjets().size() < 1)
                    continue;


                if (caseScannee.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                    nbAlignementY += 1;
                else {
                    nbAlignementY = 0;
                    pionsACapturer.add(caseScannee.getObjets().get(0) );
                }


            }

            if(nbAlignementY == 5) {
                System.out.println("Gagné en vertical");

                Jeu.getInstance().ajouterLog(
                        String.format(
                                "Le joueur %s %s a gagné grâce à un placement en vertical",
                                Jeu.getInstance().getJoueurActuel().getNom(),
                                Jeu.getInstance().getJoueurActuel().getPrenom()
                        )
                );

                return true;
            }
            nbAlignementY = 0;

            // On check en horizontal

            Case caseX = this.getGrille().getCase(x, casePlacement.getPosition().getY());

            if(caseX.getObjets().size() < 1) continue;

            if (caseX.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                nbAlignementX += 1;
            else
                nbAlignementX = 0;


            if(nbAlignementX == 5) {

                System.out.println("Gagné en horizontal");

                Jeu.getInstance().ajouterLog(
                        String.format(
                                "Le joueur %s %s a gagné grâce à un placement en horizontal",
                                Jeu.getInstance().getJoueurActuel().getNom(),
                                Jeu.getInstance().getJoueurActuel().getPrenom()
                        )
                );

                return true;
            }


        }

        return false;
    }

    /**
     * Getter check si victoire en diagonale
     *
     * @param casePlacement - Référence sur la case où un pion a été placé
     * @return - Retourne si victoire ou non
     */
    private boolean getVictoireDiagonale(Case casePlacement){
        int nbDiagonaleDroite = 0;


        int meilleurEnchainement = 0;

        for(int i = -5; i < 6; i++){


            try{
                Case diagonaleGauche = getGrille().getCase(
                        casePlacement.getPosition().getX() - i,
                        casePlacement.getPosition().getY() + i
                );

                if(diagonaleGauche.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                    nbDiagonaleDroite += 1;
                else
                    nbDiagonaleDroite = 0;

            } catch(IndexOutOfBoundsException ex){

                meilleurEnchainement = Math.max(meilleurEnchainement, nbDiagonaleDroite);
                nbDiagonaleDroite = 0;
            }

        }

        //System.out.println("diag gauche" + meilleurEnchainement);
        if (meilleurEnchainement == 5)
            return true;
        else
            meilleurEnchainement = 0;

        for(int i = -5; i < 6; i++){


            try{
                Case diagonaleDroite = getGrille().getCase(
                        casePlacement.getPosition().getX() + i,
                        casePlacement.getPosition().getY() + i
                );

                if(diagonaleDroite.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                    nbDiagonaleDroite += 1;
                else
                    nbDiagonaleDroite = 0;

            } catch(IndexOutOfBoundsException ex){

                meilleurEnchainement = Math.max(meilleurEnchainement, nbDiagonaleDroite);
                nbDiagonaleDroite = 0;
            }

        }

        //System.out.println("diag droite" + meilleurEnchainement);

        return meilleurEnchainement == 5;
    }

    /**
     * Retourne si la partie est gagnante ou non
     * @param casePlacement - Case où le pion a été placé
     * @return - Retourne si la partie est gagnée
     */
    public boolean getPartieEstGagnante(Case casePlacement){

        return getVictoireHorizontaleVerticale(casePlacement) ||
                getVictoireDiagonale(casePlacement) ||
                Jeu.getInstance().getJoueurActuel().getNbrCapture() == 5;
    }

    @Override
    public void onPreAjout(Case caseActuelle) {

    }



    public void ajouterOnPionPlaceListerner(OnPionPlaceListener listener){
        this.onPionPlaceListeners.add(listener);
    }
}
