import MG2D.Couleur;
public class Joueur{
        //Ensemble des atributs

    // id du joueur
    private int id ;
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
    // nombvre de objet de type joueur
    private static int nbr_joueur = 0 ;


        // Ensemble des constructeurs

    public Joueur(){}

    // prenant les paramètres nom, prénom, couleur
    public Joueur( String prenom , String nom, MG2D.Couleur couleur){
        this.idSet();
        this.prenom = prenom ;
        this.nom = nom ;
        this.couleur = couleur ;
    }

    public Joueur(int id ,int pion){
        this.id = id ;
        this.nbrPion = pion ;
    }



        // Ensembles des methodes 
    public void idSet(){
        
    }

    

    
        //  Ensemble des getteurs
    
    /**
     * getteur de l'id du joueur 
     * @return -retourne l'id
     */
    public int getId(){return this.id;}

    /**
     * getteur de nbrPion
     * @return -retourne le nombre restant de pion du joueur 
     */
    public int getNbrPion(){return this.nbrPion;}

    /**
     * getteur de nbrCapture
     * @return -retourne le nombre de capture effectué par le joueur 
     */
    public int getNbrCapture(){return this.nbrCapture;}






//      Ensemble des setteurs

    /**
     * setteur de l'id 
     * @param that -id du jouer 
     */
    public void setId(){
        //atribut static pour voir le nombre de joueur deja crée
    }

    /**
     * setteur de nbrPion
     * @param that _nombre de pion du joueur
     */
    public void setNbrPion(int that){this.nbrPion=that;}

    /**
     * setteur de nbrCapture
     * @param that -nbr de cature effectuer par le joueur 
     */
    public void setNbrCapture(int that){this.nbrCapture=that;}
}
