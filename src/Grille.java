import MG2D.Couleur;
import MG2D.geometrie.*;
import math.Vecteur2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Classe à utiliser pour tout ajout de nouveaux éléments dans la fenêtre.
 *
 * La grille permet de rendre responsive les éléments à l'intérieur de la fenêtre.
 *
 * */

public class Grille implements Case.Callbacks {


    /**
     * Interface AjoutCallbacks
     *
     * onLigneAjoutee - Triggered quand appel de ajouterLigne()
     * onColonneAjoutee - Triggered quand appel de ajouterColonne()
     */
    public interface AjoutCallbacks {
        void onLigneAjoutee();
        void onColonneAjoutee();
    }

    /**
     * Interface OnCaseCliquee ( Listener )
     * onCliquee - Appelé quand une case a été cliquée, renvoie la case cliquée en paramètre
     */
    public interface OnCaseCliqueeListener {
        void onCliquee(Case caseCliquee);
    }

    private ArrayList<OnCaseCliqueeListener> onCaseCliqueeListeners = new ArrayList<>();



    private ArrayList<AjoutCallbacks> onLigneAjouteeCallbacks = new ArrayList<>();

    private int lignes;
    private int colonnes;

    private Couleur couleurLignesVerticales = Couleur.VERT;
    private Couleur couleurLignesHorizontales = Couleur.ROUGE;

    // Position de la grille sur l'écran ( en pixels )
    private Vecteur2<Integer> position;

    /**
     * Les 2 premiers vecteurs représentent colonne, ligne ; Le 3ème représente les objects à l'intérieur d'une case
     * de la grille.
     *
     * La matrice est thread safe grâce à Vector.
     *
     * */
    Vector< Vector< Case > > matrice;


    // Représente la largeur et la hauteur de la grille en pixels
    private int largeur;
    private int hauteur;

    private int largeurCase = 0;
    private int hauteurCase = 0;

    public enum Mode {
        CASE,
        INTERSECTION
    }

    Mode mode = Mode.CASE;


    /**
     * @param largeur - Largeur de la grille en pixels
     * @param hauteur - Hauteur de la grille en pixels
     * @param lignes - Nombre de lignes
     * @param colonnes - Nombre de colonnes
     */
    public Grille(int largeur, int hauteur, int lignes, int colonnes){
        this.lignes = lignes;
        this.colonnes = colonnes;


        this.largeur = largeur;
        this.hauteur = hauteur;


        this.position = new Vecteur2<>(0, 0);

        this.initialiser();


    }

    /**
     * Constructeur Grille
     * @param position - Position relative à la fenêtre en pixels
     * */
    public Grille(Vecteur2<Integer> position, int largeur, int hauteur, int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;


        this.largeur = largeur;
        this.hauteur = hauteur;


        this.position = position;

        this.initialiser();
    }

    /**
     * Permet d'ajouter des listeners pour l'event onCaseCliquee
     * @param listener - Implémentation de l'interface OnCaseCliqueeListener
     * */
    public void addOnCaseCliqueeCallback(OnCaseCliqueeListener listener){
        this.onCaseCliqueeListeners.add(listener);
    }

    /**
     * Trigger qui permet de déclencher l'événnement OnCaseCliquee
     * Il permet aussi de faire descendre l'event vers les objets contenus dans la case
     * */
    public void onCaseCliquee(Vecteur2<Integer> caseCliqueeCoords, MouseEvent event){

        Case caseCliquee = this.getMatrice().get( caseCliqueeCoords.getX() ).get( caseCliqueeCoords.getY());


        // Appel le callback de la case cliquée

        caseCliquee.onCaseCliquee(event);

        // Appel les callbacks lorsqu'une case quelconque a été cliquée
        this.onCaseCliqueeListeners.forEach(cb -> cb.onCliquee(
                caseCliquee
                )
        );
    }


    /**
     * @return - Largeur initiale des cases
     * */
    public int getLargeurCases(){
        return this.largeurCase;
    }

    /**
     * @return - Hauteur initiale des cases
     * */

    public int getHauteurCases(){
        return this.hauteurCase;
    }

    /**
     * Permet d'initialiser la matrice de la grille
     * */
    private void initialiser() {
        // Initialisation colonnes

        this.matrice = new Vector<>();

        this.matrice.setSize(colonnes);

        // Itération sur les colonnes pour créer des lignes

        for(int x = 0; x < colonnes; x++) {

            // Init lignes

            this.matrice.set(x, new Vector<>());

            this.matrice.get(x).setSize(this.lignes);

            for(int y = 0; y < matrice.get(x).size(); y++) {

                Case caseActuelle =  new Case(
                        Math.floorDiv(getLargeur(), this.matrice.size()),
                        Math.floorDiv(getHauteur(), this.matrice.get(x).size()),
                        new Vecteur2<>(x, y),
                        getPosition());

                /* Debug
                if(x == 3 && y == 3) {
                    caseActuelle.setCouleurFond(Couleur.BLEU);
                }*/
                // Attache les callbacks de Grille
                caseActuelle.addCaseCallbacksListener(this);

                // Place la case dans la matrice une fois créée
                this.matrice.get(x).set(y, caseActuelle);


            }

            this.largeurCase = Math.floorDiv(getLargeur(), this.matrice.size());
            this.hauteurCase = Math.floorDiv(getHauteur(), this.matrice.get(0).size());

        }
    }

    /**
     * Getter Case
     * @param x - Coordonnée X
     * @param y - Coordonnée Y
     * @return - Case en x, y
     */

    public Case getCase(int x, int y){
        return this.getMatrice().get(x).get(y);
    }

    public Case getCase(Vecteur2<Integer> position){
        return this.getMatrice().get(position.getX()).get(position.getY());
    }
    /**
     * Setter Mode
     * @param mode - Nouveau mode de placement sur la grille
     * */
    public void setMode(Mode mode){
        this.mode = mode;
    }

    /**
     * Getter Mode
     * @return - Retourne le mode actuel sur la grille
     * */

    public Mode getMode(){
        return this.mode;
    }

    /**
     * @return - Couleur des lignes horizontales
     * */
    public Couleur getCouleurLignesHorizontales(){
        return this.couleurLignesHorizontales;
    }

    /**
     * @return - Couleur des lignes verticales
     * */
    public Couleur getCouleurLignesVerticales() {
        return couleurLignesVerticales;
    }

    /**
     * Change la couleur des lignes sur la grille
     * @param horizontale - Couleur des lignes horizontale
     * @param verticale - Couleur des lignes verticales
     * */
    public void setCouleurLignes(Couleur horizontale, Couleur verticale){
        this.couleurLignesHorizontales = horizontale;
        this.couleurLignesVerticales = verticale;
    }

    /**
     * Trace les lignes de chaque case, permet de voir clairement en dév les lignes des cases
     * */
    public ArrayList<Dessin> dessinerDebug(){

        ArrayList<Dessin> d = new ArrayList<>();

        for(int x = 0; x < this.matrice.size(); x++){
            for(int y = 0; y < this.matrice.get(x).size(); y++){


                Case caseActuelle = this.matrice.get(x).get(y);

                // Dessin lignes horizontales
                Point s1 = new Point(
                        x * caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur()
                );

                s1.setX(s1.getX() + getPosition().getX());
                s1.setY(s1.getY() + getPosition().getY());

                Point t1 = new Point(
                        x * caseActuelle.getLargeur() + caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur());

                t1.setX(t1.getX() + getPosition().getX());
                t1.setY(t1.getY() + getPosition().getY());

                Ligne l = new Ligne(s1, t1);
                l.setCouleur(this.couleurLignesHorizontales);
                d.add(l);
                // Dessin lignes verticales
                Point s2 = new Point(
                        s1
                );

                s2.setX(s2.getX() + getPosition().getX());
                s2.setY(s2.getY() + getPosition().getY());

                Point t2 = new Point(
                        x * caseActuelle.getLargeur(),
                        y * caseActuelle.getHauteur() + caseActuelle.getHauteur());

                t2.setX(t2.getX() + getPosition().getX());
                t2.setY(t2.getY() + getPosition().getY());

                l = new Ligne(s2, t2);
                l.setCouleur(this.couleurLignesVerticales);
                d.add(l);


                /*
                    Rectangle r = new Rectangle(
                            caseActuelle.getCouleurFond(),
                            s1,
                            new Point(x * caseActuelle.getLargeur() + caseActuelle.getLargeur(), y * caseActuelle.getHauteur() + caseActuelle.getHauteur())
                    );

                    r.setPlein(true);
                    //fenetre.ajouter(r);*/
            }
        }

        return d;
    }

    /**
     * Permet d'ajouter une ligne à la matrice
     * */
    public void ajouterLigne(){
        for(int x = 0; x < this.matrice.size(); x++) {

            Case caseActuelle = new Case(
                    Math.floorDiv(getLargeur(), this.matrice.size()),
                    Math.floorDiv(getHauteur(), this.matrice.get(x).size() + 1),
                    new Vecteur2<>(x, this.matrice.size() + 1),
                    getPosition()
            );

            this.matrice.get(x).add(caseActuelle);

        }

        // On va recalculer la taille des cases en pixels une fois la ligne ajoutée
        for (Vector<Case> cases : matrice)
            for (int y = 0; y < cases.size() - 1; y++)
                cases.get(y).setHauteur(Math.floorDiv(this.getHauteur(), cases.size()));


        onLigneAjouteeCallbacks.forEach(cb -> cb.onLigneAjoutee());

    }

    /**
     * Ajoute une colonne à la matrice de la grille
     * */

    public void ajouterColonne(){
        this.matrice.add(new Vector<>());

        for(int y = 0; y < this.matrice.get(0).size(); y++)
            this.matrice.get(this.matrice.size() - 1).add(new Case(
                    Math.floorDiv(getLargeur(), this.matrice.size()),
                    Math.floorDiv(getHauteur(), this.matrice.get(0).size()),
                    new Vecteur2<>(this.matrice.size(), y),
                    getPosition()
            ));


    }


    /**
     * Getter colonnes
     * @return - Retourne le nombre de colonnes
     * */
    public final int getColonnes(){
        return this.colonnes;
    }

    /**
     * Setter colonnes
     *
     * Attention : Le setter va rafraîchir le layout de la grille, peut lag si beaucoup d'éléments
     *
     * @param nbColonnes - Nombre de colonnes à ajouter
     * */
    public void setColonnes(int nbColonnes){
        this.colonnes = nbColonnes;
    }

    /**
     * Getter lignes
     * @return - Retourne le nombre de lignes
     * */

    public final int getLignes(){
        return this.lignes;
    }

    public void setLignes(int nbLignes){
        this.lignes = nbLignes;

    }

    /**
     * Getter de la matrice associée à la grille
     * @return - Matrice associée à la grille
     * */
    public Vector<Vector<Case>> getMatrice(){
        return this.matrice;
    }

    /**
     * Getter de la hauteur de la grille
     * @return - Hauteur de la grille en pixels
     * */
    public int getHauteur(){
        return this.hauteur;
    }

    /**
     * Getter de la largeur de la grille
     * @return - Largeur de la grille en pixels
     * */
    public int getLargeur(){
        return this.largeur;
    }

    /**
     * Getter Position de la grille
     * @return - Position de la grille
     * */
    public Vecteur2<Integer> getPosition(){
        return this.position;
    }

    /**
     * Callback appelé lorsque la taille d'une case change (largeur ou hauteur),
     * si plus de largeur la case va s'étendre sinon elle va se réduire en créant une nouvelle case
     *
     * @param caseChangee - Case qui a changée de taille
     * */

    @Override
    public void onCaseChangeTaille(Case caseChangee) {
        for(int x = caseChangee.getPosition().getX(); x <= caseChangee.getPosition().getX() + caseChangee.getLargeurColonne(); x++)

            for(int y = caseChangee.getPosition().getY(); y <= caseChangee.getPosition().getY() + caseChangee.getHauteurLigne(); y++)
                this.matrice.get(x).set(y, caseChangee);

    }
    /**
     * Raccourci pour ajouter un acteur à la grille
     * */
    public void ajouter(Vecteur2<Integer> position, Acteur objet){
        this.matrice.get(position.getX()).get(position.getY()).getObjets().add(objet);
    }

    /**
     * Raccourci pour supprimer un acteur à la grille
     * */
    public void supprimer(Vecteur2<Integer> position, Acteur objet){
        this.matrice.get(position.getX()).get(position.getY()).getObjets().remove(objet);
    }

}
