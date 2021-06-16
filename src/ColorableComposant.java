import MG2D.Couleur;

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

public class ColorableComposant implements Composant.Callbacks {

    Couleur couleur = null;

    /**
     * Constructeur du composant Colorable
     */
    public ColorableComposant(){
        couleur = Couleur.MAGENTA;
    }



    @Override
    public void onAttache(Acteur acteur) {

    }

    @Override
    public void onDetache() {

    }

    /**
     * Setter Couleur
     * @param couleur - Couleur contenue dans le composant
     */

    public void setCouleur(Couleur couleur){
        this.couleur = couleur;
    }

    /**
     * Getter Couleur
     * @return - Couleur du composant
     */

    public Couleur getCouleur(){
        return this.couleur;
    }

}
