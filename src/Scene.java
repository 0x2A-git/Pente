import MG2D.Fenetre;
import math.Vecteur2;

/**
 * Représente la grille principale du logiciel c'est ici qu'on va placer les différents éléments
 * Sa spécialité est de cacher la grille, car la classe va gérer elle-même la grille
 * */

public class Scene {

    public enum Placement {
        MILIEU
    }

    Grille grille;

    Fenetre fenetre;

    public Scene(Fenetre fenetre, Grille grille){
        this.fenetre = fenetre;
        this.grille = grille;
    }


    public Grille getGrille(){
        return this.grille;
    }

    public void onClique(Vecteur2<Integer> caseConcernee){


    }

    public void ajouter(Vecteur2<Integer> pos, Acteur obj){
        getGrille().ajouter(pos, obj);

        obj.onPreAjout(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.setPosition( new Vecteur2<>(
                 pos.getX() * this.getGrille().getLargeurCases(),
                 pos.getY() * this.getGrille().getHauteurCases()));
        obj.onAjoute(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.dessiner().forEach(dessin -> fenetre.ajouter(dessin));

    }



    public void ajouter(Vecteur2<Integer> pos, Acteur obj, Placement placement){
        getGrille().ajouter(pos, obj);

        obj.onPreAjout(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        switch (placement) {
            case MILIEU:
                obj.setPosition(new Vecteur2<>(
                    pos.getX() * this.getGrille().getLargeurCases() + Math.floorDiv(getGrille().getLargeurCases(), 2),
                    pos.getY() * this.getGrille().getHauteurCases() + Math.floorDiv(getGrille().getHauteurCases(), 2)
                ));
            break;
        }
        obj.onAjoute(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.dessiner().forEach(dessin -> fenetre.ajouter(dessin));

    }



    public void supprimer(){

    }
}
