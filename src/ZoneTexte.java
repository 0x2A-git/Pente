import MG2D.geometrie.Dessin;
import MG2D.geometrie.Texte;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Classe qui représente une zone texte et est donc composée d'objets de type Texte
 *
 * Auteurs :
 *
 * BERNARD Manon
 * BOURRE Maxime
 * BUTELLE Dorine
 * VASSEUR Maxence
 * DELSART Eloise
 * MARTIN Lucas
 */

public class ZoneTexte extends Acteur {

    ArrayList<Texte> textes = new ArrayList<>();

    Font police = null;


    /**
     * Constructeur par défaut
     */
    public ZoneTexte(){

        this.police = new Font("Serif", Font.PLAIN, 15);


    }

    /**
     * Constructeur ZoneTexte
     *
     * @param texteContenu - Texte à placer dans la zone du texte
     */
    public ZoneTexte(String texteContenu){
        this.police = new Font("Serif", Font.PLAIN, 10);

        Texte texte = new Texte(
                texteContenu,
                police,
                new MG2D.geometrie.Point(getPosition().getX(),
                        getPosition().getY() + Math.floorDiv(Jeu.getInstance().getScene().getGrille().getHauteurCases(), 2)),
                Texte.GAUCHE);

        textes.add(texte);

    }

    /**
     * Constructeur ZoneTexte
     *
     * @param textesContenu - Les textes à ajouter
     */
    public ZoneTexte(ArrayList<String> textesContenu){
        textesContenu.forEach(texteContenu -> {
            Texte texte = new Texte();
            texte.setTexte(texteContenu);
            this.textes.add(texte);
        });
    }

    /**
     * Dessin attachés à ZoneTexte
     * @return - Retourne les dessins attachés à la zone de texte
     */

    @Override
    public ArrayList<Dessin> dessiner() {

        return new ArrayList<>(textes);
    }

    /**
     * Ajouter texte
     * @param texteContenu - Texte à ajouter
     */
    public void ajouterTexte(String texteContenu){

        Texte texte = new Texte(
                texteContenu,
                police,
                new MG2D.geometrie.Point(getPosition().getX(),
                        getPosition().getY() + Math.floorDiv(Jeu.getInstance().getScene().getGrille().getHauteurCases(), 2)),
                Texte.GAUCHE);

        textes.add(texte);
    }

    /**
     * Ajouter texte
     * @param texte - Texte à ajouter
     */
    public void ajouterTexte(Texte texte){
        texte.setPolice(police);
        textes.add(texte);
    }


    @Override
    public void onPreAjout(Case caseActuelle) {


    }

    /**
     * Lorsque zone texte est ajouté, empiler les textes
     * @param caseActuelle - Case où la zone de texte a été placé
     */

    @Override
    public void onAjoute(Case caseActuelle) {

        setPosition(
                new Vecteur2<>(
                        caseActuelle.getPositionEcran().getX() + getPosition().getX(),
                        caseActuelle.getPositionEcran().getY() + getPosition().getY()
                )
        );


        for (int i = 0; i < this.textes.size(); i++) {

            this.textes.get(i).setA(new MG2D.geometrie.Point(
                    getPosition().getX(),

                    i > 0 ?
                            getPosition().getY() + textes.get(i - 1).getPolice().getSize() :
                            getPosition().getY()
                    )
            );


        }
    }

    @Override
    public void onClique(MouseEvent event) {

    }

    /**
     * Getter textes
     * @return - Récupère les textes de la zone de texte
     */
    public ArrayList<Texte> getTextes(){
        return this.textes;
    }
}
