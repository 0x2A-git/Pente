import MG2D.Fenetre;
import MG2D.geometrie.Dessin;

import java.util.ArrayList;

/**
 * Singleton qui représente l'état du jeu
 *
 * */
public class Jeu {

    private static volatile Jeu instance = null;

    private Scene sceneActuelle = null;

    private Fenetre fenetre = null;

    private Jeu(){
        super();
    }

    public Fenetre getFenetre(){
        return this.fenetre;
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
                }
            }

        }

        return Jeu.instance;

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
    }
}
