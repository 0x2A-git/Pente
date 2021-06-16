import MG2D.Couleur;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
/**
 * Représente la case d'une grille
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
public class Case {

    /**
     * Interface des callbacks de Case
     *
     * onCaseChangeTaille - Appelée lorsque la case a changée de taille en unité de grille
     */
    public interface Callbacks {
        void onCaseChangeTaille(Case caseChangee);
    }

    /**
     * Trigger qui déclenche les listeners de la case
     * @param event - Evennement de souris d'AWT
     */
    public void onCaseCliquee(MouseEvent event){
        getObjets().forEach(obj -> obj.onClique(event));
    }


    private Couleur couleurFond = Couleur.MAGENTA;

    private ArrayList<Callbacks> onCaseCallbacks = null;

    private Vector<Acteur> objets = null;

    private int hauteur = 1; // En unité de grille
    private int largeur = 1; // En unité de grille

    private int hauteurPixels = 0; // En pixels
    private int largeurPixels = 0; // En pixels

    // Position dans la grille
    private Vecteur2<Integer> position = null;

    // Position relative à l'écran
    private Vecteur2<Integer> positionEcran = null;
    /**
     * @param largeur - Largeur de la case en pixel
     * @param hauteur - Hauteur de la case en pixel
     * @param position - Représente la position dans la matrice de la grille
     * @param grillePos - Position de la grille relative à l'écran
     * */

    public Case(int largeur, int hauteur, Vecteur2<Integer> position, Vecteur2<Integer> grillePos){

        this.objets = new Vector<Acteur>();

        this.position = new Vecteur2<Integer>(position);


        this.hauteurPixels = hauteur;
        this.largeurPixels = largeur;

        this.onCaseCallbacks = new ArrayList<Callbacks>();


        this.positionEcran = new Vecteur2<Integer>(
                grillePos.getX() + (getLargeur() * getPosition().getX()),
                grillePos.getY() + (getHauteur() * getPosition().getY() ));

    }

    /**
     * Getter des objets sur la case
     * @return - Retourne les objets sur la case
     * */
    public Vector<Acteur> getObjets() {
        return this.objets;
    }

    public Acteur getObjet(int indice){
        if(indice + 1 > this.objets.size())
            return null;

        return this.objets.get(indice);
    }

    /**
     * Change la couleur de la case
     * @param couleur - Nouvelle couleur de la case
     * */
    public void setCouleurFond(Couleur couleur) {
        this.couleurFond = couleur;
    }

    /**
     * Getter de la couleur de la case
     * @return - Couleur de la case
     * */

    public Couleur getCouleurFond(){
        return this.couleurFond;
    }

    /**
     * Setter largeur de la case en pixels
     * @param nouvelleLargeur - Nouvelle largeur exprimée en Pixels
     * */
    public void setLargeur(int nouvelleLargeur){
        this.largeurPixels = nouvelleLargeur;
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
        this.hauteurPixels = nouvelleHauteur;
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
    public void etendreColonne(int nouvelleLargeur){
        this.largeur = nouvelleLargeur;
        this.largeurPixels *= this.largeur + 1;
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
    public void etendreLigne(int nouvelleHauteur){
        this.hauteur = nouvelleHauteur;
        this.hauteurPixels *= this.hauteur + 1;
        onCaseCallbacks.forEach(listener -> listener.onCaseChangeTaille(this));
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

    /**
     * Getter position écran
     * @return - Retourne la position de la case par rapport à l'écran
     * */
    public Vecteur2<Integer> getPositionEcran(){
        return positionEcran;
    }
}
