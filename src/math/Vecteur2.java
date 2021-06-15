package math;

import java.util.Objects;

/**
 * Implémentation générique du vecteur 2D
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

public class Vecteur2<T extends Number> {
    private T x;
    private T y;


    /**
     * Constructeur
     * @param x - Coordonnée X
     * @param y - Coordonnée Y
     */
    public Vecteur2(T x, T y){
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur par copie
     * @param that
     */
    public Vecteur2(Vecteur2<T> that){
        this.x = that.x;
        this.y = that.y;
    }

    /**
     * Setter X
     * @param nouveauX - Coordonnée X à assigner au vecteur
     * */
    public void setX(T nouveauX){
        this.x = nouveauX;
    }

    /**
     * Getter X
     * @return - Coordonnée X du vecteur
     * */
    public T getX(){
        return this.x;
    }

    /**
     * Setter Y
     * @param nouveauY - Coordonnée Y à assigner au vecteur
     * */

    public void setY(T nouveauY){
        this.y = nouveauY;
    }

    /**
     * Getter Y
     * @return - Retourne la coordonnée Y
     */
    public T getY(){
        return this.y;
    }

    /**
     * Equals
     *
     * Permet de faire un equals en fonction des attributes X et Y
     *
     * @param o - autre vecteur
     * @return - Si le vecteur est identique retourne vrai sinon faux
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vecteur2<?> vecteur2 = (Vecteur2<?>) o;
        return Objects.equals(x, vecteur2.x) && Objects.equals(y, vecteur2.y);
    }

    /**
     * HashCode
     *
     * Implémentation du hashcode en fonction de X et Y
     * @return - HashCode de l'objet
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
