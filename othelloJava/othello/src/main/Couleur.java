package main;
import java.util.List;
/**
 * Enumération représentant les différentes couleurs possibles d'un pion
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 */

public enum Couleur {
    /** Couleur noire */
    NOIR,
    /** Couleur blanche */
    BLANC,
    /** Pas de couleur */
    VIDE;


public Couleur oppose() {
    if (this == NOIR) {
        return BLANC;
    } else {
        return NOIR;
    }

}
}

