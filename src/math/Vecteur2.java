package math;

import java.util.Objects;

/**
 * Implémentation générique du vecteur 2D
 * */

public class Vecteur2<T extends Number> {
    private T x;
    private T y;

    public Vecteur2(T x, T y){
        this.x = x;
        this.y = y;
    }

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

    /***/
    public T getY(){
        return this.y;
    }

    public double getDistance(){
        return Math.sqrt( Math.pow(this.x.longValue(), 2) + Math.pow(this.y.longValue(), 2) );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vecteur2<?> vecteur2 = (Vecteur2<?>) o;
        return Objects.equals(x, vecteur2.x) && Objects.equals(y, vecteur2.y);
    }


}
