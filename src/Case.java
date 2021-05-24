import MG2D.geometrie.Dessin;
import math.Vecteur2;

import java.util.ArrayList;
import java.util.Vector;
/**
 * Représente la case d'une grille
 * */

public class Case {

    public static interface Callbacks {
        void onCaseChangeTaille(Case caseChangee);
    }

    private ArrayList<Callbacks> onCaseCallbacks = new ArrayList<>();

    private Vector<Dessin> objets = null;

    private int hauteur = 1;
    private int largeur = 1;

    private int hauteurPixels = 0;
    private int largeurPixels = 0;

    private Vecteur2<Integer> position = null;


    /**
     * @param largeur - Largeur de la case en pixel
     * @param hauteur - Hauteur de la case en pixel
     * @param position - Représente la position dans la matrice de la grille
     * */
    public Case(int largeur, int hauteur, Vecteur2<Integer> position){

        this.objets = new Vector<Dessin>();

        this.position = new Vecteur2<Integer>(position);


        this.hauteurPixels = hauteur;
        this.largeurPixels = largeur;

    }

    /**
     * Setter largeur de la case en pixels
     * @param nouvelleLargeur - Nouvelle largeur exprimée en Pixels
     * */
    public void setLargeur(int nouvelleLargeur){
        this.largeurPixels = nouvelleLargeur * getLargeurColonne();
    }

    /**
     * Getter largeur de la case en pixels
     * @return - Largeur de la case exprimée en pixels
     * */
    public int getLargeur(){
        return this.largeurPixels;
    }

    /**
     * Setter hauteur de la case en pixels
     * @param nouvelleHauteur - Nouvelle hauteur exprimée en Pixels
     * */
    public void setHauteur(int nouvelleHauteur){
        this.hauteurPixels = nouvelleHauteur * getHauteurLigne();
    }

    /**
     * Getter hauteur de la case en pixels
     * @return - Hauteur de la case exprimée en pixels
     * */
    public int getHauteur(){
        return this.hauteurPixels;
    }

    /**
     * Getter largeur de la case par rapport à la colonne de la grille
     * @return - Nombre de colonnes occupées dans la grille par la case
     * */
    public int getLargeurColonne(){
        return this.largeur;
    }

    /**
     * Permet d'étendre la largeur de la case sur la matrice de la grille
     * @param nouvelleLargeur - Nombre de colonnes à occuper sur la grille en partant de la position X de la case
     * */
    public void etendreLargeurColonne(int nouvelleLargeur){
        this.largeur = nouvelleLargeur;
        this.largeurPixels *= this.largeur;
        onCaseCallbacks.forEach(listener -> listener.onCaseChangeTaille(this));

    }

    /**
     * Getter hauteur de la case par rapport à la ligne de la grille
     * @return - Nombre de lignes occupées dans la grille par la case
     * */
    public int getHauteurLigne(){
        return this.hauteur;
    }

    /**
     * Permet d'étendre la hauteur de la case sur la matrice de la grille
     * @param nouvelleHauteur - Nombre de lignes à occuper sur la grille en partant de la position Y de la case
     * */
    public void etendreHauteurLigne(int nouvelleHauteur){
        this.hauteur = nouvelleHauteur;
        this.hauteurPixels *= this.hauteur;
        onCaseCallbacks.forEach(listener -> listener.onCaseChangeTaille(this));
    }

    /**
     * Permet d'ajouter un objet qu'on peut dessiner à la case ( TODO )
     * */
    public void ajouter(Dessin dessin){
        this.objets.add(dessin);
    }

    /**
     * Permet de supprimer un objet qu'on peut dessiner et qui se trouve dans la case (TODO)
     * */
    public void supprimer(Dessin dessin){
        this.objets.remove(dessin);
    }

    /**
     * @param cb - Callback à ajouter lorsque la taille de la case va être affectée ( en hauteur ou en largeur )
     * */

    public void addCaseCallbacksListener(Case.Callbacks cb){
        this.onCaseCallbacks.add(cb);
    }

    /**
     * Getter position
     * @return - Retourne la position de la case dans la matrice de la grille
     * */

    public Vecteur2<Integer> getPosition(){
        return this.position;
    }
}
