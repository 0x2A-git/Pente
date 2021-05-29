import MG2D.Couleur;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class Joueur{

    // prenom du joueur
    private String prenom = null;
    // nom du joueur 
    private String nom = null;
    // couleur du joueur
    private MG2D.Couleur couleur ;
    // nombre de pions 
    private int nbrPion;
    // nombre de pions capturé
    private int nbrCapture = 0 ;

    // Garde les adresses mémoires des pions placés par ce joueur
    private ArrayList<Reference<Pion>> pions;

    // Ensemble des constructeurs

    public Joueur(){

        this.pions = new ArrayList<>();

    }

    // prenant les paramètres nom, prénom, couleur
    public Joueur(String prenom, String nom, MG2D.Couleur couleur){

        this.pions = new ArrayList<>();


        this.prenom = prenom;
        this.nom = nom;
        this.couleur = couleur;
    }

    /**
     * Ajouter Pion
     * @param ref - référence vers le pion ajouté sur le plateau ou capturé
     */
    public void ajouterPion(Reference<Pion> ref) {
        this.pions.add(ref);
    }

    /**
     * Retirer pion
     * @param ref - référence du pion à retirer du joueur
     */

    public void retirerPion(Reference<Pion> ref){
        this.pions.remove(ref);
    }


    /**
     * Getter de nbrPion
     * @return -retourne le nombre restant de pion du joueur 
     */
    public int getNbrPion(){
        return this.pions.size();
    }

    /**
     * getteur de nbrCapture
     * @return -retourne le nombre de capture effectué par le joueur 
     */
    public int getNbrCapture(){return this.nbrCapture;}

    // Ensemble des setteurs


    /**
     * Setter de nbrPion
     * @param nbrPions _nombre de pion du joueur
     */
    public void setNbrPions(int nbrPions){
        this.nbrPion = nbrPions;
    }

    /**
     * Setter de nbrCapture
     * @param nbrCapture -nbr de cature effectuer par le joueur
     */
    public void setNbrCapture(int nbrCapture){
        this.nbrCapture = nbrCapture;
    }
}
