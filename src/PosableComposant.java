import MG2D.Couleur;
import math.Vecteur2;

/**
 * Composant qui ajoute des méthodes pour la couleur d'un acteur
 */

public class PosableComposant implements Composant.Callbacks {

    Vecteur2<Integer> position = null;

    public PosableComposant(){
        position = new Vecteur2<Integer>(0,0);
    }



    @Override
    public void onAttache(Acteur acteur) {

    }

    @Override
    public void onDetache() {

    }

    public Vecteur2<Integer> getPosition(){
        return this.position;
    }

    public void setPosition(Vecteur2<Integer> position){
        this.position = position;
    }
}
