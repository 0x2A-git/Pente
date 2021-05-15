import MG2D.geometrie.Texture;
import math.Vecteur2;


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
     * Constructeur Pion
     *
     * @param x - Coordonnée X relative au damier
     * @param y - Coordornée Y relative au damier
     * */
    public Pion(int x, int y){

        super(new Vecteur2<Integer>(x,y));

    }

    /**
     * Constructeur par copie
     * */

    public Pion(Pion that){

        super(new Vecteur2<Integer>(that.getPosition().getX(), that.getPosition().getY()));


    }


}
