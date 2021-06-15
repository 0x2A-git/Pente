import MG2D.Couleur;
import MG2D.geometrie.Cercle;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Point;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Représente le pion qui peut être placé dans le jeu de la pente
 * */


public  class Pion extends Acteur {


    //Couleur couleur = Couleur.MAGENTA; // Magenta pour debug

    /**
     * Constructeur par défaut du Pion
     * */
    public Pion(Vecteur2<Integer> position){
        super(position);

    }

    public Pion(Vecteur2<Integer> position, Couleur couleur){
        super(position);

        ajouterComposant(ColorableComposant.class);

        getComposant(ColorableComposant.class).setCouleur(couleur);
    }

    /**
     * Constructeur par copie
     * */

    public Pion(Pion that){
        super(new Vecteur2<>(that.getPosition().getX(), that.getPosition().getY()));
        //this.couleur = that.couleur;

    }

    @Override
    public void onClique(MouseEvent event) {

    }


    @Override
    public ArrayList<Dessin> dessiner() {
        //System.out.println("Pion :" + position.getX() + ", " + position.getY());
        Cercle cercle = new Cercle(
                getComposant(ColorableComposant.class).getCouleur(),
                new Point(getPosition().getX(),getPosition().getY()),
                //Jeu.getInstance().getScene().getGrille().getHauteurCases()
                10
        );
        cercle.setPlein(true);
        dessins.add(cercle);
        return dessins;
    }

    @Override
    public void mettreAJours() {

    }

    @Override
    public void onPreAjout(Case caseActuelle) {

    }

    @Override
    public void onAjoute(Case caseActuelle) {



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
