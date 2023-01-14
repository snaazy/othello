package main;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * Classe representant un Noeud
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 */
public class Noeud {
    // coordonnées du coup représenté par ce noeud
    private int ligne;
    private int colonne;
    // liste des noeuds enfants
    private List<Noeud> enfants;
    private Othellier othellier;
    private Couleur couleurJoueur;
    private int valeur;
    
    // constructeur
    public Noeud(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.enfants = new ArrayList<>();
    }
    
    public int getValeur(){
        return valeur;
    }
    
    // constructeur par défaut
    public Noeud() {
        this.ligne = -1;
        this.colonne = -1;
        this.enfants = new ArrayList<>();
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getColonne(){
        return this.colonne;
    }

    public boolean estFeuille(){
        return enfants.isEmpty();
    }

    public List<Noeud> getEnfants(){
        return enfants;
    }

    // méthode pour ajouter un noeud enfant
    public void ajouterEnfant(Noeud enfant) {
        this.enfants.add(enfant);
    }

    // méthode pour afficher l'arbre à partir de ce noeud
    public void afficherArbre() {
        System.out.println("(" + Math.incrementExact(this.ligne) + ", " + Math.incrementExact(this.colonne) + ")");
        for (Noeud enfant : this.enfants) {
            enfant.afficherArbre();
        }
    }   



    
}

