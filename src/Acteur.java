import math.Vecteur2;

/**
 * Représente un objet quelconque dans le jeu
 * */
public abstract class Acteur {

    protected Vecteur2<Integer> position = null;
    /**
     * Je pense qu'on pourrait utiliser un String pour identifier chaque Acteur, ça nous permettrait de pouvoir
     * utiliser une HashMap et du coup avoir une complexité en O(1) pour le CPU quand on a besoin de récup un objet.
     * - LM
     * */

    protected String id = "";

    public Acteur(Vecteur2<Integer> position){
        this.position = position;
    }

    public Vecteur2<Integer> getPosition(){
        return this.position;
    }

    public void setPosition(Vecteur2<Integer> nouvellePosition){
        this.position = nouvellePosition;
    }

}
