import MG2D.*;
import MG2D.geometrie.*;
import MG2D.geometrie.Point;


public class Pente {


    static final byte NB_CASES = 18;

    public static void main(String[] args){


        Fenetre fenetrePrincipale = new Fenetre("Test", 800, 600);

        final int TAILLE_CASES = (int)Math.ceil(fenetrePrincipale.getWidth() / NB_CASES);

        Grille test = new Grille(15,3, fenetrePrincipale);

        test.getMatrice().get(0).get(0).etendreLargeurColonne(3);
        test.getMatrice().get(0).get(0).etendreHauteurLigne(14);


        test.getMatrice().get(0).get(14).etendreLargeurColonne(3);

        System.out.println(test.getMatrice().toString());

        test.dessinerDebug();

        Clavier clavier = fenetrePrincipale.getClavier();
        Souris souris = fenetrePrincipale.getSouris();

        fenetrePrincipale.addKeyListener(clavier);

        // *** Partie test ***

        /*
        Carre haut = new Carre(Couleur.ROUGE, new Point(fenetrePrincipale.getMilieu().getX(),fenetrePrincipale.getMilieu().getX()), 200);

        fenetrePrincipale.setBackground(Couleur.JAUNE);

        fenetrePrincipale.addMouseListener(souris);

        System.out.println(fenetrePrincipale.getWidth() / TAILLE_CASES);
        for(int x = 0; x < fenetrePrincipale.getWidth(); x += TAILLE_CASES){
            for(int y = 0; y < fenetrePrincipale.getHeight(); y += TAILLE_CASES){

                Ligne ligneHorizontale = new Ligne(new Point(x, y), new Point(x + TAILLE_CASES, y ));
                ligneHorizontale.setCouleur(Couleur.NOIR);
                fenetrePrincipale.ajouter(ligneHorizontale);

                Ligne ligneVerticale = new Ligne(new Point(x, y), new Point(x, y + TAILLE_CASES ));
                ligneVerticale.setCouleur(Couleur.NOIR);
                fenetrePrincipale.ajouter(ligneVerticale);
            }
        }*/



        while(!clavier.getEchapTape()) {
            try {
                Thread.sleep(200);
            } catch(Exception ex){

            }
            fenetrePrincipale.rafraichir();
        }
        fenetrePrincipale.fermer();


    }
}
