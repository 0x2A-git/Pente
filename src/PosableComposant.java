/**
 * Composant qui ajoute des méthodes pour la couleur d'un acteur
 *
 * Auteurs :
 *
 * BERNARD Manon
 * BOURRE Maxime
 * BUTELLE Dorine
 * VASSEUR Maxence
 * DELSART Eloise
 * MARTIN Lucas
 *
 * */

public class PosableComposant implements Composant.Callbacks {

    Vecteur2<Integer> position = null;

    /**
     * Constructeur du composant posable
     */
    public PosableComposant(){
        position = new Vecteur2<Integer>(0,0);
    }



    @Override
    public void onAttache(Acteur acteur) {

    }

    @Override
    public void onDetache() {

    }

    /**
     * Getter position
     * @return - Retourne la position contenue dans le composant
     */
    public Vecteur2<Integer> getPosition(){
        return this.position;
    }

    /**
     * Setter position
     * @param position - Position à mettre sur le composant
     */
    public void setPosition(Vecteur2<Integer> position){
        this.position = position;
    }
}
