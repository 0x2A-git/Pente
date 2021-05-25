import MG2D.Couleur;
import MG2D.Fenetre;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Ligne;
import MG2D.geometrie.Point;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Plateau extends Acteur {


    Grille grille = null;

    Vecteur2<Integer> position = null;

    public Plateau(){

        super(new Vecteur2<Integer>(0,0));
    }

    public Plateau(Vecteur2<Integer> position){

        super(position);

    }

    public Grille getGrille() {
        return grille;
    }


    @Override
    public void onClique(MouseEvent event) {


        Vecteur2<Integer> localCoords = new Vecteur2<Integer>(
                Math.floorDiv(event.getX() - getPosition().getX(), getGrille().getLargeurCases()),
                Math.floorDiv((Jeu.getInstance().getFenetre().getHeight() - (getPosition().getY()) - event.getY()), getGrille().getHauteurCases())
        );

        Case caseActuelle = getGrille().getMatrice().get(localCoords.getX()).get(localCoords.getY());

        System.out.println(localCoords.getX() + ", " + localCoords.getY());

        Acteur pion = new Pion(caseActuelle.getPositionEcran());

        getGrille().ajouter(
                caseActuelle.getPosition(),
                pion
        );

        Jeu.getInstance().dessiner(pion.dessiner());



    }

    @Override
    public void onAjoute(Case caseActuelle) {

        this.grille = new Grille(
                getPosition(),
                caseActuelle.getLargeur(),
                caseActuelle.getHauteur(),
                19,19);
        grille.setCouleurLignes(Couleur.NOIR, Couleur.NOIR);
        grille.setPlacement(Grille.Placement.HAUT_DROIT);


    }



    public ArrayList<Dessin> dessiner() {

        for(int x = 0; x < getGrille().getMatrice().size(); x++) {
            for (int y = 0; y < getGrille().getMatrice().size(); y++) {


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

        Ligne derniereLigneH = new Ligne(
            new Point(
                getGrille().getPosition().getX(),
                getGrille().getPosition().getY() + getGrille().getMatrice().get(0).size() * getGrille().getHauteurCases()
            ),

            new Point(
                getGrille().getPosition().getX() + getGrille().getMatrice().size() * getGrille().getLargeurCases(),
                getGrille().getPosition().getY() + getGrille().getMatrice().get(0).size() * getGrille().getHauteurCases()

            )
        );
        
        Ligne derniereLigneL = new Ligne(
            
            new Point(
                getGrille().getPosition().getX() + getGrille().getMatrice().size() * getGrille().getLargeurCases(),
                getGrille().getPosition().getY() + getGrille().getMatrice().get(0).size() * getGrille().getHauteurCases()

            ),

            new Point(
                getGrille().getPosition().getX() + getGrille().getMatrice().size() * getGrille().getLargeurCases(),
                getGrille().getPosition().getY()
            )

        );

        dessins.add(derniereLigneH);
        dessins.add(derniereLigneL);
        

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
