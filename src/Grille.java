import MG2D.Couleur;
import MG2D.Fenetre;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Ligne;
import MG2D.geometrie.Point;
import math.Vecteur2;

import java.util.Vector;

/**
 * Classe à utiliser pour tout ajout de nouveaux éléments dans la fenêtre.
 *
 * La grille permet de rendre responsive les éléments à l'intérieur de la fenêtre.
 *
 * */

public class Grille implements Case.Callbacks{

    private int lignes = -1;
    private int colonnes = -1;

    /**
     * Les 2 premiers vecteurs représentent colonne, ligne ; Le 3ème représente les objects à l'intérieur d'une case
     * de la grille.
     *
     * La matrice est thread safe grâce à Vector.
     *
     * */
    Vector< Vector< Case > > matrice;

    private Fenetre fenetre = null;


    private int largeurCase = 0;
    private int hauteurCase = 0;

    // TODO : bind on resize fenetre
    public Grille(int lignes, int colonnes, Fenetre fenetre){
        this.lignes = lignes;
        this.colonnes = colonnes;

        this.fenetre = fenetre;

        // Initialisation colonnes

        this.matrice = new Vector<Vector<Case>>();

        this.matrice.setSize(colonnes);

        // Itération sur les colonnes pour créer des lignes

        for(int x = 0; x < colonnes; x++) {

            // Init lignes

            this.matrice.set(x, new Vector<Case>());

            this.matrice.get(x).setSize(this.lignes);

            for(int y = 0; y < matrice.get(x).size(); y++) {

                Case caseActuelle =  new Case(
                        Math.floorDiv(fenetre.getWidth(), this.matrice.size()),
                        Math.floorDiv(fenetre.getHeight(), this.matrice.get(x).size()),
                        new Vecteur2<Integer>(x,y));

                // Attache les callbacks de Grille
                caseActuelle.addCaseCallbacksListener(this);

                // Place la case dans la matrice une fois créée
                this.matrice.get(x).set(y, caseActuelle);


            }

        }


    }

    /**
     * Trace les lignes de chaque case, permet de voir clairement en dév les lignes des cases
     * */
    public void dessinerDebug(){
        this.matrice.forEach(ligne -> {
            ligne.forEach(objet -> {

                Point source = new Point(
                        objet.getPosition().getX() * objet.getLargeur(),
                        (objet.getPosition().getY() * objet.getHauteur()) + objet.getHauteur()


                );

                System.out.println(objet.getPosition().getY() + " - " + objet.getHauteur());

                Point target = new Point(
                        objet.getPosition().getX() * objet.getLargeur() + objet.getLargeur(),
                        (objet.getPosition().getY() * objet.getHauteur()) + objet.getHauteur()
                );

                Ligne l = new Ligne(source, target);
                l.setCouleur(Couleur.ROUGE);
                fenetre.ajouter(l);


            });
        });
    }

    public void ajouterLigne(int largeur){
    }

    public void ajouter(int x, int y, Dessin dessin){
        this.matrice.get(x).get(y).ajouter(dessin);
    }

    public void supprimer(int x, int y, Dessin dessin){
        this.matrice.get(x).get(y).supprimer(dessin);
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
        // TODO : Réadapter layout
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

        // TODO : même que setColonnes
    }

    public Vector<Vector<Case>> getMatrice(){
        return this.matrice;
    }

    // Case callbacks

    /**
     * Callback appelé lorsque la taille d'une case change (largeur ou hauteur),
     * si plus de largeur la case va s'étendre sinon elle va se réduire en créant une nouvelle case
     * */

    @Override
    public void onCaseChangeTaille(Case caseChangee) {
        for(int x = caseChangee.getPosition().getX(); x < caseChangee.getLargeurColonne(); x++) {

            this.matrice.get(caseChangee.getPosition().getX() + x).set(caseChangee.getPosition().getY(), caseChangee);


            for (int y = caseChangee.getPosition().getY(); y < caseChangee.getHauteurLigne(); y++)
                this.matrice.get(caseChangee.getPosition().getX() + x).set(caseChangee.getPosition().getY() + y, caseChangee);
        }
    }


}
