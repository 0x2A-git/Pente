import MG2D.Couleur;
import MG2D.geometrie.Cercle;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Représente le pion qui peut être placé dans le jeu de la pente
 * */

/**
 * **** Dev *****
 * Si quelqu'un peut commencer à dev des méthodes de base pour pouvoir l'utiliser plus tard ce serait cool.
 * - LM
 */

public  class Pion extends Acteur {

    /**
     * Pas encore sûr de mettre en attribut les objets de type Dessin ça peut devenir rapidement compliqué pour rien.
     * Je me demande si on peut pas faire une implémentation sur Acteur avec une interface du style Dessinable.
     * - LM
     * */
    Texture texture = null;

    /**
     * Constructeur par défaut du Pion
     * */
    public Pion(Vecteur2<Integer> position){
        super(position);

        System.out.println(position.getX());
    }

    /**
     * Constructeur par copie
     * */

    public Pion(Pion that){
        super(new Vecteur2<Integer>(that.getPosition().getX(), that.getPosition().getY()));

    }

    @Override
    public void onClique(MouseEvent event) {

    }


    @Override
    public ArrayList<Dessin> dessiner() {
        System.out.println("Pion :" + position.getX() + ", " + position.getY());
        Cercle cercle = new Cercle(
                Couleur.MAGENTA,
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
