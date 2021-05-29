import MG2D.Couleur;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Ligne;
import MG2D.geometrie.Point;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Plateau extends Acteur {

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

        if(caseActuelle.getObjets().size() > 0)
            return;


        Acteur pion = new Pion(
                new Vecteur2<>(
                        getGrille().getPosition().getX() + caseActuelle.getPosition().getX() * getGrille().getLargeurCases(),
                        getGrille().getPosition().getY() + caseActuelle.getPosition().getY() * getGrille().getHauteurCases()
                )
        );

        getGrille().ajouter(
                caseActuelle.getPosition(),
                pion
        );

        Jeu.getInstance().dessiner(pion.dessiner());



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

    @Override
    public void mettreAJours() {

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
}
