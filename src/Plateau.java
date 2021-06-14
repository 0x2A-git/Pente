import MG2D.Couleur;
import MG2D.Fenetre;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Ligne;
import MG2D.geometrie.Point;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class Plateau extends Acteur {


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

    public Plateau(Vecteur2<Integer> position){

        super(position);

    }

    public Grille getGrille() {
        return grille;
    }


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

    public void capturer(Case casePlacement, Pion pion, Fenetre fenetrePrincipale){

        LinkedList<Acteur> candidats = new LinkedList<>();

        // Capture horizontale


        for (int x = Math.max(casePlacement.getPosition().getX() - 3, 0); x < casePlacement.getPosition().getX() + 4; x++){


            if( x == casePlacement.getPosition().getX() || x ==  casePlacement.getPosition().getX() + 3){

                // Contient le premier pion ajouté
                Acteur debut = candidats.pollFirst();

                // Si premier pion n'est pas null et est de la même couleur que le joueur actuel
                if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur() ){

                    ArrayList<Acteur> aEnlever = new ArrayList<>();

                    // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                    while(!candidats.isEmpty()){
                        Acteur act = candidats.poll();
                        if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                            aEnlever.add(act);
                    }


                    // Enfin on supprime les pions si capture valide en Y
                    if(aEnlever.size() == 2) {
                        Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);
                        int finalX = x;
                        aEnlever.forEach(p -> {
                            Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                            Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                            this.getGrille().supprimer(
                                    p.getComposant(PosableComposant.class).getPosition(),
                                    p
                            );
                            fenetrePrincipale.supprimer(p.getDessins().get(0));

                        });
                    }

                }

                candidats = new LinkedList<>();
                candidats.add(pion);
                continue;
            }

            try {
                candidats.add(this.getGrille().getCase(x, casePlacement.getPosition().getY()).getObjets().get(0));
            } catch(IndexOutOfBoundsException ex){
                // Si rien dans la case

                if(x < casePlacement.getPosition().getX())
                    x = casePlacement.getPosition().getX() - 1;
                else
                    x = casePlacement.getPosition().getX() + 3;
            }


        }

        // Capture verticale


        for (int y = Math.max(casePlacement.getPosition().getY() - 3,0); y < casePlacement.getPosition().getY() + 4; y++){


            if( y == casePlacement.getPosition().getY() || y ==  casePlacement.getPosition().getY() + 3){

                // Contient le premier pion ajouté
                Acteur debut = candidats.pollFirst();

                // Si premier pion n'est pas null et est de la même couleur que le joueur actuel
                if(debut != null && debut.getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur() ){

                    ArrayList<Acteur> aEnlever = new ArrayList<>();

                    // On dequeue mes candidats et on vérifie qu'ils ne sont pas de la même couleur que le joueur actuel
                    while(!candidats.isEmpty()){
                        Acteur act = candidats.poll();
                        if(act.getComposant(ColorableComposant.class).getCouleur() != Jeu.getInstance().getJoueurActuel().getCouleur())
                            aEnlever.add(act);
                    }


                    // Enfin on supprime les pions si capture valide en Y
                    if(aEnlever.size() == 2) {
                        Jeu.getInstance().getJoueurActuel().setNbrCapture(Jeu.getInstance().getJoueurActuel().getNbrCapture() + 1);

                        // final y pas bon stocker position pion dans linkedlist
                                                /*
                                                int finalY = y;

                                                for(int i = ; i < aEnlever.size(); i++){
                                                    Jeu.getInstance().getDerniersPionsSupprimes().put(aEnlever.get(i), new Vecteur2<>(casePlacement.getPosition().getX(), y + i));

                                                    Jeu.getInstance().getJoueursQueue().peek().getPions().remove(aEnlever.get(i));
                                                    plateau.getGrille().supprimer(new Vecteur2<>(casePlacement.getPosition().getX(), y + i), aEnlever.get(i));

                                                    fenetrePrincipale.supprimer(aEnlever.get(i).getDessins().get(0));
                                                }*/

                        aEnlever.forEach(p -> {
                            Jeu.getInstance().getDerniersPionsSupprimes().put(p, p.getComposant(PosableComposant.class).getPosition());

                            Jeu.getInstance().getJoueursQueue().peek().getPions().remove(p);

                            this.getGrille().supprimer(
                                    p.getComposant(PosableComposant.class).getPosition(),
                                    p
                            );
                            fenetrePrincipale.supprimer(p.getDessins().get(0));

                        });
                    }

                }

                candidats = new LinkedList<>();
                candidats.add(pion);
                continue;
            }

            try {
                candidats.add(this.getGrille().getCase(casePlacement.getPosition().getX(), y).getObjets().get(0));
            } catch(IndexOutOfBoundsException ex){
                // Si rien dans la case

                if(y < casePlacement.getPosition().getY())
                    y = casePlacement.getPosition().getY() - 1;
                else
                    y = casePlacement.getPosition().getY() + 3;
            }


        }
    }

    private boolean getVictoireDiagonale(Case casePlacement){
        int nbDiagonaleDroite = 0;
        for(int x = -5; x < 5; x++){

            Case caseDiagonaleDroite = this.getGrille().getCase(
                    Math.min(Math.max(casePlacement.getPosition().getX() + x, 0), this.getGrille().getMatrice().size() - 1),
                    Math.min(Math.max(casePlacement.getPosition().getY() + x, 0), this.getGrille().getMatrice().get(0).size() - 1));

            if(caseDiagonaleDroite.getObjets().size() < 1) continue;

            if (caseDiagonaleDroite.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                nbDiagonaleDroite += 1;

        }

        if(nbDiagonaleDroite == 5) {
            System.out.println("Gagné en diagonale droite");

            Jeu.getInstance().ajouterLog(
                    String.format(
                            "Le joueur %s %s a gagné grâce à un placement en diagonale ( droite )",
                            Jeu.getInstance().getJoueurActuel().getNom(),
                            Jeu.getInstance().getJoueurActuel().getPrenom()
                    )
            );

            return true;
        }
        System.out.println("Diagonale droite : " + nbDiagonaleDroite);

        int nbDiagonaleGauche = 0;
        for(int x = -5; x < 5; x++){

            Case caseDiagonaleGauche = this.getGrille().getCase(
                    Math.min(Math.max(casePlacement.getPosition().getX() - x, 0), this.getGrille().getMatrice().size() - 1),
                    Math.min(Math.max(casePlacement.getPosition().getY() + x, 0), this.getGrille().getMatrice().get(0).size() - 1)
            );

            if(caseDiagonaleGauche.getObjets().size() < 1) continue;

            if (caseDiagonaleGauche.getObjets().get(0).getComposant(ColorableComposant.class).getCouleur() == Jeu.getInstance().getJoueurActuel().getCouleur())
                nbDiagonaleGauche += 1;

            //
        }

        if(nbDiagonaleGauche == 5) {
            System.out.println("Gagné en diagonale gauche");

            Jeu.getInstance().ajouterLog(
                    String.format(
                            "Le joueur %s %s a gagné grâce à un placement en diagonale ( gauche )",
                            Jeu.getInstance().getJoueurActuel().getNom(),
                            Jeu.getInstance().getJoueurActuel().getPrenom()
                    )
            );

            return true;
        }

        return false;
    }
    public boolean getPartieEstGagnante(Case casePlacement, Joueur joueur){

        System.out.println("Pion place à :" + casePlacement.getPosition().getX() + ", " + casePlacement.getPosition().getY());

        return getVictoireHorizontaleVerticale(casePlacement) || getVictoireDiagonale(casePlacement) || joueur.getNbrCapture() == 5;
    }

    @Override
    public void mettreAJours() {

    }

    @Override
    public void onPreAjout(Case caseActuelle) {

    }

    @Override
    public void onMisAJour() {

    }

    @Override
    public void onSupprime() {

    }

    @Override
    public void onDessine() {

    }

    public void ajouterOnPionPlaceListerner(OnPionPlaceListener listener){
        this.onPionPlaceListeners.add(listener);
    }
}
