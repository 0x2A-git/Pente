import MG2D.geometrie.Dessin;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente un objet quelconque dans une scène
 * Auteurs :
 *
 * BERNARD Manon
 * BOURRE Maxime
 * BUTELLE Dorine
 * VASSEUR Maxence
 * DELSART Eloise
 * MARTIN Lucas
 * */
public abstract class Acteur {

    // Va stocker les composants de l'acteur
    protected HashMap< String, Composant<? extends Composant.Callbacks> > composants = new HashMap();

    protected Vecteur2<Integer> position;

    protected String id = "";


    ArrayList<Dessin> dessins = new ArrayList<>();

    /**
     * Constructeur par défaut
     */
    public Acteur() {
        this.position = new Vecteur2<>(-1,-1);
    }

    /**
     * Constructeur
     * @param position - Position dans la fenêtre
     */
    public Acteur(Vecteur2<Integer> position){
        this.position = position;

    }

    /**
     * Changer la position de l'acteur
     * @param nouvellePosition - Nouvelle position de l'acteur
     */
    public void setPosition(Vecteur2<Integer> nouvellePosition){
        this.position = nouvellePosition;
    }

    /**
     * Getter position
     * @return - Retourne la position de l'acteur
     */
    public Vecteur2<Integer> getPosition(){
        return this.position;
    }


    /**
     * Méthode abstraite qui permet d'implémenter le dessin de l'acteur d'une certaine façon
     *
     * @return - Retourne les dessins associés à l'acteur
     */
    public abstract ArrayList<Dessin> dessiner();

    /**
     * Getter dessins
     * @return - Récupère les dessins associés à l'acteur
     */
    public ArrayList<Dessin> getDessins(){
        return this.dessins;
    }
    // Events

    /**
     * Event quand l'objet a été ajoutée à une case de la grille appartenant à la scène actuelle
     * @param caseActuelle - Case a
     * */
    public abstract void onPreAjout(Case caseActuelle);
    public abstract void onAjoute(Case caseActuelle);

    /**
     * Appelé lorsque l'acteur est cliqué
     * @param event
     */

    public abstract void onClique(MouseEvent event);

    /**
     * Ajout d'un composant
     * @param composant - Composant à ajouter ( ex: MonComposant.class )
     */
    public <T extends Composant.Callbacks> void ajouterComposant(Class<T> composant) {
        Composant<T> cmp = new Composant<T>(composant);

        if(this.composants.containsKey(composant.getName())) {
            System.out.println("[ERREUR -> Composant] : Composant déjà existant sur l'acteur");
            return;
        }


        this.composants.put(composant.getName(), cmp);

        cmp.get().onAttache(this);

    }

    /**
     * Récupérer un composant
     *
     * @param composant - Composant à récupérer ( ex : MonComposant.class )
     * @return - Retourne le composant spécifié en paramètre
     */
    public <T extends Composant.Callbacks> T getComposant(Class<T> composant){
        return composant.cast( composants.get(composant.getName()).get() );
    }
}
