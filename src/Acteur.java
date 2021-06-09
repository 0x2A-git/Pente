import MG2D.geometrie.Dessin;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente un objet quelconque dans une scène
 * */
public abstract class Acteur {

    // Va stocker les composants de l'acteur
    protected HashMap< String, Composant<? extends Composant.Callbacks> > composants = new HashMap();

    protected Vecteur2<Integer> position;

    protected String id = "";


    ArrayList<Dessin> dessins = new ArrayList<>();

    public Acteur() {
        this.position = new Vecteur2<>(-1,-1);
    }

    public Acteur(Vecteur2<Integer> position){
        this.position = position;

    }

    public void setPosition(Vecteur2<Integer> nouvellePosition){
        this.position = nouvellePosition;
    }

    public Vecteur2<Integer> getPosition(){
        return this.position;
    }


    public abstract ArrayList<Dessin> dessiner();
    public abstract void mettreAJours();

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

    public abstract void onMisAJour();
    public abstract void onSupprime();
    public abstract void onDessine();

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
