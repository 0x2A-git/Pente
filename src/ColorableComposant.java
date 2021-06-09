import MG2D.Couleur;

/**
 * Composant qui ajoute des méthodes pour la couleur d'un acteur
 */

public class ColorableComposant implements Composant.Callbacks {

    Couleur couleur = null;

    public ColorableComposant(){
        couleur = Couleur.MAGENTA;
    }



    @Override
    public void onAttache(Acteur acteur) {

    }

    @Override
    public void onDetache() {

    }

    public void setCouleur(Couleur couleur){
        this.couleur = couleur;
    }

    public Couleur getCouleur(){
        return this.couleur;
    }
    public void test(){
        System.out.println("test");
    }
}
