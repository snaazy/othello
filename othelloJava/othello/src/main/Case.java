package main;
/**
 * Classe représentant une case de l'othellier
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 */

public class Case {
    /** Couleur de la case */
    private Couleur couleur;
    /** Numero de la ligne de la case */
    private int lignes;
    /** Numero de la colonne de la case */
    private int colonnes;

    /**
     * Constructeur par défaut.
     * Crée une case vide à la position spécifiée.
     * @param lignes numéro de ligne de la case
     * @param colonnes numéro de colonne de la case
     */
    public Case(int lignes, int colonnes){
        this.lignes = lignes;
        this.colonnes = colonnes;
        clear();
    }
    /**
     * Constructeur parametré permettant de créer une case avec une couleur spécifiée.
     * @param lignes numéro de ligne de la case
     * @param colonnes numéro de colonne de la case
     * @param c couleur de la case
     */
    public Case(int lignes, int colonnes, Couleur c){
        this.lignes = lignes;
        this.colonnes = colonnes;
        
    }

    public Couleur getCouleur(){
        return this.couleur;
    }

    public void setCouleur(Couleur couleur){
        this.couleur = couleur;
    }

    public Case(Couleur c){
        this.couleur = c;
    }
    /**
     * Méthode permettant de mettre la couleur de la case à vide.
     */
    public void clear(){
        couleur = Couleur.VIDE;
    }
    
 
    /**
     * Méthode permettant de dessiner le pion de la case.
     * Le pion est dessiné en fonction de sa couleur.
     */
    public  void dessinerPion(){
        switch(couleur){
            case NOIR:
                System.out.print(" ● ");
                break;
            case BLANC:
                System.out.print(" ○ ");
                break;
            case VIDE:
                System.out.print(" ∙ ");
                break;
        }
    }


    /**
     * Méthode permettant de définir la couleur de la case.
     * @param couleur couleur de la case
     */
    public void mettreCouleur(Couleur couleur){
       this.couleur = couleur;
    }
    
}

