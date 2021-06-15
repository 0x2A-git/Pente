import MG2D.Fenetre;

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

    /**
     * Constructeur Scene
     *
     * @param fenetre - Fenetre MG2D où l'on va dessiner
     * @param grille - Grille de la scène
     */
    public Scene(Fenetre fenetre, Grille grille){
        this.fenetre = fenetre;
        this.grille = grille;
    }


    /**
     *
     * Permet d'ajouter un objet à la grille scène
     *
     * @param pos - Position en unité de grille
     * @param obj - Objet à ajouter
     */
    public void ajouter(Vecteur2<Integer> pos, Acteur obj){
        getGrille().ajouter(pos, obj);

        obj.onPreAjout(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.setPosition( new Vecteur2<>(
                 pos.getX() * this.getGrille().getLargeurCases(),
                 pos.getY() * this.getGrille().getHauteurCases()));
        obj.onAjoute(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.dessiner().forEach(dessin -> fenetre.ajouter(dessin));

    }



    /**
     *
     * Permet d'ajouter un objet à la grille de la scène
     *
     * @param pos - Position en unité de grille
     * @param obj - Objet à ajouter
     * @param placement - Type de placement à effectuer par rapport à la case
     */
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

            default:
                break;
        }
        obj.onAjoute(getGrille().getMatrice().get(pos.getX()).get(pos.getY()) );

        obj.dessiner().forEach(dessin -> fenetre.ajouter(dessin));

    }


    /**
     * Getter de la grille de la scène actuelle
     * @return - Grille principale du jeu
     */
    public Grille getGrille(){
        return this.grille;
    }

}
