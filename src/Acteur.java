import MG2D.geometrie.Dessin;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Représente un objet quelconque dans une scène
 * */
public abstract class Acteur {

    protected Vecteur2<Integer> position;
    /**
     * Je pense qu'on pourrait utiliser un String pour identifier chaque Acteur, ça nous permettrait de pouvoir
     * utiliser une HashMap et du coup avoir une complexité en O(1) pour le CPU quand on a besoin de récup un objet.
     * - LM
     * */

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
}
