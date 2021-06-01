import MG2D.Couleur;
import java.util.ArrayList;

public class Joueur{

    // prenom du joueur
    private String prenom = null;
    // nom du joueur
    private String nom = null;
    // couleur du joueur
    private Couleur couleur ;
    // nombre de pions capturé
    private int nbrCapture = 0 ;

    // Garde les adresses mémoires des pions placés par ce joueur
    private ArrayList<Pion> pions;

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
     * @param pion - référence forte vers le pion ajouté sur le plateau ou capturé
     */
    public void ajouterPion(Pion pion) {
        this.pions.add(pion);
    }

    /**
     * Getter Pions
     * @return - Retourne un tableau de référence des pions du joueur
     */
    public ArrayList<Pion> getPions(){
        return this.pions;
    }

    /**
     * Retirer pion
     * @param pion - référence forte du pion à retirer du joueur
     */

    public void retirerPion(Pion pion){
        this.pions.remove(pion);
    }


    /**
     * Getter pour récupérer le nombre de pions
     * @return -retourne le nombre restant de pion du joueur 
     */
    public int getNbrPions(){
        return this.pions.size();
    }

    /**
     * Getter de nbrCapture
     * @return -retourne le nombre de capture effectué par le joueur 
     */
    public int getNbrCapture(){return this.nbrCapture;}


    /**
     * Getter Couleur
     * @return - Couleur du joueur
     */
    public Couleur getCouleur(){
        return this.couleur;
    }

    /**
     * Getter Nom
     * @return - Nom joueur
     */

    public String getNom(){
        return this.nom;
    }

    /**
     * Getter Prenom
     */
    public String getPrenom() {
        return prenom;
    }
    // Ensemble des Setters

    /**
     * Setter de nbrCapture
     * @param nbrCapture -nbr de cature effectuer par le joueur
     */
    public void setNbrCapture(int nbrCapture){
        this.nbrCapture = nbrCapture;
    }

    /**
     * Setter Couleur
     * @param couleur - Nouvelle couleur
     */
    public void setCouleur(Couleur couleur){
        this.couleur = couleur;
    }

    /**
     * Setter Nom
     * @param nom - Nom du joueur
     */
    public void setNom(String nom){
        this.nom = nom;
    }

    /**
     * Setter Prenom
     * @param prenom - Prenom du joueur
     */
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
}
