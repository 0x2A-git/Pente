import java.lang.reflect.InvocationTargetException;

/**
 * Classe représentant un composant
 *
 * Permet d'avoir une modularité au niveau des acteurs
 * @param <T> - Type de composant
 */

public class Composant<T> {

    public interface Callbacks {
        void onAttache(final Acteur acteur);
        void onDetache();
    }

    private T composant;


    public Composant(Class<T> composant) {

        try {
            this.composant = composant.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T get(){
        return this.composant;
    }
}
