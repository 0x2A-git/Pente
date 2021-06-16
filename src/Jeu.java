import MG2D.Fenetre;
import MG2D.geometrie.Dessin;

import java.util.*;

/**
 * Singleton qui représente l'état du jeu
 *
 * Auteurs :
 *
 * BERNARD Manon
 * BOURRE Maxime
 * BUTELLE Dorine
 * VASSEUR Maxence
 * DELSART Eloise
 * MARTIN Lucas
 * */
public class Jeu {


    // Joueur qui actuellement en train de jouer
    private Joueur joueurActuel = null;

    private Queue<Joueur> joueurs = null;

    private static volatile Jeu instance = null;

    private Scene sceneActuelle = null;

    private Fenetre fenetre = null;

    private int nbJoueurs = -1;

    private Pion dernierPionPlace = null;


    private ArrayList<String> logs = new ArrayList<>();

    private Map<Acteur, Vecteur2<Integer>> derniersPionsSupprimes = new HashMap<>();

    /**
     * Constructeur par défaut
     */
    private Jeu(){
        super();
    }

    /**
     * Implémentation reprise de wikipedia car thread safe
     * @return - Retourne l'instance de jeu et va l'instancier si pas déjà créée
     * */
    public static Jeu getInstance() {

        if(Jeu.instance == null){

            synchronized (Jeu.class) {
                if(Jeu.instance == null){
                    Jeu.instance = new Jeu();

                    Jeu.instance.joueurs = new ArrayDeque<Joueur>();
                }
            }

        }

        return Jeu.instance;

    }

    /**
     * Initialisation de Jeu
     *
     * @param fenetre - Fenetre du jeu
     * @param sceneDepart - Scene de départ
     */
    public void init(Fenetre fenetre, Scene sceneDepart) {
        this.fenetre = fenetre;
        this.sceneActuelle = sceneDepart;

        this.joueurActuel = null;

        this.joueurs = new ArrayDeque<>();

        this.nbJoueurs = -1;

        this.dernierPionPlace = null;
    }

    /**
     * Getter fenetre
     * @return - Retourne la fenetre actuelle du jeu
     */
    public Fenetre getFenetre(){
        return this.fenetre;
    }

    /**
     * Getter dernier pion placé
     * @return - Retourne le dernier pion placé
     */

    public Pion getDernierPionPlace() {
        return dernierPionPlace;
    }

    /**
     * Setter dernier pion placé
     * @param pion -
     */
    public void setDernierPionPlace(Pion pion){
        this.dernierPionPlace = pion;
    }

    /**
     * Ajouter derniers pions supprimes
     * @param p - Pion à ajouter
     * @param positionPion - Position du pion
     */
    public void ajouterDerniersPionSupprimes(Acteur p, Vecteur2<Integer> positionPion){
        this.derniersPionsSupprimes.put(p, positionPion);
    }

    /**
     * Getter derniers pions supprimes
     * @return - Retourne les pions supprimés
     */
    public Map<Acteur, Vecteur2<Integer>> getDerniersPionsSupprimes(){
        return this.derniersPionsSupprimes;
    }

    /**
     * Setter Nombre Joueurs
     * @param nb - Nombre de joueurs
     */
    public void setNombreJoueurs(int nb){
        this.nbJoueurs = nb;
    }

    /**
     * Getter Nombre Joueurs
     * @return - Retourne le nombre de joueurs
     */
    public int getNombreJoueurs(){
        return this.nbJoueurs;
    }

    /**
     * Dessiner un seul dessin
     * @param dessin - Dessin à dessiner
     */
    public void dessiner(Dessin dessin){
        this.fenetre.ajouter(dessin);
    }

    /**
     * Dessiner plusieurs dessins
     * @param dessins - Les dessins à dessiner
     */
    public void dessiner(ArrayList<Dessin> dessins){
        dessins.forEach(d -> this.fenetre.ajouter(d));
    }

    /**
     * Getter scene
     * @return - Retourne la scène actuelle
     */

    public Scene getScene(){
        return this.sceneActuelle;
    }

    /**
     * Setter scene
     * @param scene - Scene à changer
     */
    public void setScene(Scene scene){
        this.sceneActuelle = scene;

    }


    /**
     * Getter queue des joueurs
     * @return - Retourne la queue contenant les joueurs
     */
    public Queue<Joueur> getJoueursQueue(){
        return this.joueurs;
    }

    /**
     * Getter joueur actuel
     * @return - Retourne le joueur qui joue actuellement
     */

    public Joueur getJoueurActuel(){
        return this.joueurActuel;
    }

    /**
     * Setter joueur actuel
     * @param j - Prochain joueur qui va jouer
     */
    public void setJoueurActuel(Joueur j){
        this.joueurActuel = j;
    }

    /**
     * Getter Logs
     * @return - Retourne les logs de la partie
     */
    public ArrayList<String> getLogs(){
        return this.logs;
    }

    /**
     * Ajoouter log
     * @param log - Log à ajouter
     */
    public void ajouterLog(String log){
        this.logs.add(log);
    }
}
