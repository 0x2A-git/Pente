import MG2D.geometrie.Dessin;
import MG2D.geometrie.Texte;
import math.Vecteur2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ZoneTexte extends Acteur {

    ArrayList<Texte> textes = new ArrayList<>();

    Font police = null;


    public ZoneTexte(){

        this.police = new Font("Serif", Font.PLAIN, 15);


    }

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

    public ZoneTexte(ArrayList<String> textesContenu){
        textesContenu.forEach(texteContenu -> {
            Texte texte = new Texte();
            texte.setTexte(texteContenu);
            this.textes.add(texte);
        });
    }

    @Override
    public ArrayList<Dessin> dessiner() {

        return new ArrayList<>(textes);
    }

    public void ajouterTexte(String texteContenu){

        Texte texte = new Texte(
                texteContenu,
                police,
                new MG2D.geometrie.Point(getPosition().getX(),
                        getPosition().getY() + Math.floorDiv(Jeu.getInstance().getScene().getGrille().getHauteurCases(), 2)),
                Texte.GAUCHE);

        textes.add(texte);
    }

    public void ajouterTexte(Texte texte){
        texte.setPolice(police);
        textes.add(texte);
    }


    @Override
    public void onPreAjout(Case caseActuelle) {


    }


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

    public ArrayList<Texte> getTextes(){
        return this.textes;
    }
}
