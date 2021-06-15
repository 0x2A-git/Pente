import MG2D.Fenetre;
import MG2D.geometrie.Dessin;

import java.util.*;

/**
 * Singleton qui représente l'état du jeu
 *
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


    private Jeu(){
        super();
    }

    public Fenetre getFenetre(){
        return this.fenetre;
    }


    public Pion getDernierPionPlace() {
        return dernierPionPlace;
    }

    public void setDernierPionPlace(Pion pion){
        this.dernierPionPlace = pion;
    }

    private Map<Acteur, Vecteur2<Integer>> derniersPionsSupprimes = new HashMap<>();

    public void ajouterDerniersPionSupprimes(Acteur p, Vecteur2<Integer> positionPion){
        this.derniersPionsSupprimes.put(p, positionPion);
    }

    public Map<Acteur, Vecteur2<Integer>> getDerniersPionsSupprimes(){
        return this.derniersPionsSupprimes;
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

    public void setNombreJoueurs(int nb){
        this.nbJoueurs = nb;
    }

    public int getNombreJoueurs(){
        return this.nbJoueurs;
    }

    public void dessiner(Dessin dessin){
        this.fenetre.ajouter(dessin);
    }
    public void dessiner(ArrayList<Dessin> dessins){
        dessins.forEach(d -> this.fenetre.ajouter(d));
    }

    public Scene getScene(){
        return this.sceneActuelle;
    }

    public void setScene(Scene scene){
        this.sceneActuelle = scene;

    }
    public void init(Fenetre fenetre, Scene sceneDepart) {
        this.fenetre = fenetre;
        this.sceneActuelle = sceneDepart;

        this.joueurActuel = null;

        this.joueurs = new ArrayDeque<>();

        this.nbJoueurs = -1;

        this.dernierPionPlace = null;
    }

    public Queue<Joueur> getJoueursQueue(){
        return this.joueurs;
    }

    public Joueur getJoueurActuel(){
        return this.joueurActuel;
    }

    public void setJoueurActuel(Joueur j){
        this.joueurActuel = j;
    }

    public ArrayList<String> getLogs(){
        return this.logs;
    }

    public void ajouterLog(String log){
        this.logs.add(log);
    }
}
