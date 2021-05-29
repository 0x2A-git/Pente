import MG2D.*;
import math.Vecteur2;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Pente {


    public static void main(String[] args){


        Fenetre fenetrePrincipale = new Fenetre("Test", 800, 600);

        Jeu jeu = Jeu.getInstance();



        Scene scenePrincipale = new Scene(
                fenetrePrincipale,
                new Grille(
                        fenetrePrincipale.getP().getWidth(),
                        fenetrePrincipale.getP().getHeight(),
                        8,
                        8
                ));


        jeu.init(fenetrePrincipale, scenePrincipale);


        scenePrincipale.getGrille().dessinerDebug().forEach(d -> fenetrePrincipale.ajouter(d));

        scenePrincipale.getGrille().getMatrice().get(1).get(1).etendreColonne(5);
        scenePrincipale.getGrille().getMatrice().get(1).get(1).etendreLigne(5);

        Case caseDepart = scenePrincipale.getGrille().getMatrice().get(1).get(1);

        // La position est passée par référence, ainsi lorsque la case de départ bouge la grille bouge avec


        Plateau plateau = new Plateau();

        scenePrincipale.ajouter(
                new Vecteur2<Integer>(1,1),
                plateau
        );
        plateau.getGrille().setCouleurLignes(Couleur.NOIR, Couleur.NOIR);


        //test.getMatrice().get(0).get(0).etendreLargeurColonne(3);
        //test.getMatrice().get(0).get(0).etendreHauteurLigne(14);


        //test.getMatrice().get(0).get(14).etendreLargeurColonne(3);

        //test.ajouterColonne();


        //plateau.getMatrice().get(1).get(0).etendreLigne(4);

        //test.ajouterLigne();

        //plateau.dessiner();

        Clavier clavier = fenetrePrincipale.getClavier();
        fenetrePrincipale.addKeyListener(clavier);

        scenePrincipale.getGrille().addOnCaseCliqueeCallback(new Grille.OnCaseCliqueeListener() {
            @Override
            public void onCliquee(Case caseCliquee) {
            }
        });

        // Attache un callback lorsque clique gauche sur la fenêtre ( via awt )
        fenetrePrincipale.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                System.out.println(fenetrePrincipale.getInsets());

                // Conversion du clique de souris en coordonnées de la grille
                int xGrid =
                        scenePrincipale.getGrille().getMatrice().size() -
                                Math.floorDiv(scenePrincipale.getGrille().getLargeur() - mouseEvent.getX(), scenePrincipale.getGrille().getLargeurCases());

                int yGrid = Math.floorDiv(
                        scenePrincipale.getGrille().getHauteur() - mouseEvent.getY() + fenetrePrincipale.getInsets().top,
                        scenePrincipale.getGrille().getHauteurCases());

                System.out.println(xGrid - 1 + ", " + yGrid);

                // Trigger l'event case cliquee
                scenePrincipale.getGrille().onCaseCliquee(new Vecteur2<Integer>(xGrid - 1, yGrid), mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
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

