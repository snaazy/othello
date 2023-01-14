package main;


/**
 * Classe représentant un othellier,  le plateau de jeu de l'Othello.
 * Elle contient un tableau 2D de cases et gère l'affichage et le comptage de pions.
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 * 
 */
public class Othellier {

    /*
     * Tableau 2D de cases représentant l'othellier
     */
	Case[][] cases;
    /*
     * Nombre de lignes de l'othellier
     */
	private int LIGNES;
    /*
     * Nombre de colonnes de l'othellier
     */
    private int COLONNES;
    /*
     * Compteur de pions noirs sur l'othellier
     */
    private int nbPionsNoirs;
    /*
     * Compteur de pions blancs sur l'othellier
     */
    private int nbPionsBlancs;


    /**
     * Constructeur de la classe.
     * Initialise les champs de l'othellier en fonction des paramètres passés.
     * @param lignes : le nombre de lignes de l'othellier
     * @param colonnes : le nombre de colonnes de l'othellier
     */

	public Othellier(int lignes, int colonnes) {
		this.LIGNES = lignes;
		this.COLONNES = colonnes;
		cases = new Case[LIGNES][COLONNES]; 
		for (int l = 0; l < LIGNES; l++)
			for (int c = 0; c < COLONNES; c++)
				cases[l][c] = new Case(lignes, colonnes); 
	}


    public int getLignes(){
        return LIGNES;
    }

    public int getColonnes(){
        return COLONNES;
    }


    public void setLignes(int lignes) {
        this.LIGNES = lignes;
    }
      
    public void setColonnes(int colonnes) {
    this.COLONNES = colonnes;
    }

    public Case[][] getCases() {
        return cases;
    }
     
    
    /**
     * Dessine l'othellier en affichant les pions sur chaque case, 
     * ainsi que les compteiurs de pions noirs
     * et blancs
     */
    public void dessiner() {
		System.out.print("    ");
		for (int c = 0; c < COLONNES; c++)
			System.out.print(c+1 + (c > 8 ? " " : "  ")); 
		System.out.println();
		
		for (int r = 0; r < LIGNES; r++) {
			System.out.print(r+1 + (r > 8 ? " " : "  "));
			for (int c = 0; c < COLONNES; c++)
				cases[r][c].dessinerPion();
			System.out.println();
		}
		nbPionsNoirs = compterPionsNoirs();
		nbPionsBlancs = compterPionsBlancs();
        System.out.println();
        System.out.println(" ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        System.out.println("⏐" + "        ▾ SCORE ▾        "+ "⏐");
        System.out.print(" ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
		System.out.print("\nJoueur Noir : " + nbPionsNoirs);
        System.out.println("\nJoueur Blanc : " + nbPionsBlancs);

	}


    /**
     * Compter le nombre de pions noirs présents sur l'othellier
     * @return le nombre de poins noirs
     */
    public int compterPionsNoirs() {
		int cpt = 0;
		for (int l = 0; l < LIGNES; l++) 
			for (int c = 0; c < COLONNES; c++)
				if (cases[l][c].getCouleur() == Couleur.NOIR)
					cpt++;
		return cpt;
	}

    /**
     * Compter le nombre de pions blancs présents sur l'othellier
     * @return le nombre de pions blancs
     */
	public int compterPionsBlancs() {
		int cpt = 0;
		for (int l = 0; l < LIGNES; l++) 
			for (int c = 0; c < COLONNES; c++)
				if (cases[l][c].getCouleur() == Couleur.BLANC)
					cpt++;
		return cpt;
	}
   
    /*
     * Methode qui place les pions par défaut au centre du plateau
     * 
     */
    public void pionsParDefaut() {
        int ligneMilieu = LIGNES / 2;
        int colonneMilieu = COLONNES / 2;
        cases[ligneMilieu - 1][colonneMilieu - 1].mettreCouleur(Couleur.BLANC);
        cases[ligneMilieu][colonneMilieu].mettreCouleur(Couleur.BLANC);
        cases[ligneMilieu - 1][colonneMilieu].mettreCouleur(Couleur.NOIR);
        cases[ligneMilieu][colonneMilieu - 1].mettreCouleur(Couleur.NOIR);
    }
    

    /**
     * Place un pion de la couleur spécifiée sur la case de l'othellier correspondant à la ligne et la colonne données
     * @param lignes l'indice de la ligne de la case où placer le pion
     * @param colonnes l'indice de la colonne de la case où placer le pion
     * @param couleur la couleur du pion à placer
     */
    public void mettrePion(int lignes, int colonnes, Couleur couleur) {
		cases[lignes][colonnes].mettreCouleur(couleur);
	}


    /**
     * Affiche le gagnant de la partie en fonction du nombre de pions de chaque joueur.
     */
    public void trouverGagnant() {
        int nbPionsNoirs = compterPionsNoirs();
        int nbPionsBlancs = compterPionsBlancs();
    
        if (nbPionsNoirs > nbPionsBlancs) {
            System.out.println("\n --> Le joueur NOIR a gagné la partie !");
        } else if (nbPionsNoirs < nbPionsBlancs) {
            System.out.println("\n --> Le joueur BLANC a gagné la partie !");
        } else {
            System.out.println("\nAucun joueur ne gagne");
        }
    }

    public Othellier copier() {
        Othellier copie = new Othellier(this.getLignes(), this.getColonnes());
        for (int i = 0; i < this.getLignes(); i++) {
          for (int j = 0; j < this.getColonnes(); j++) {
            copie.getCases()[i][j].setCouleur(this.getCases()[i][j].getCouleur());
          }
        }
        return copie;
      }
      

      public boolean partieTerminee() {
        // Vérifie si toutes les cases de l'othellier sont remplies
        for (int ligne = 0; ligne < getLignes(); ligne++) {
            for (int colonne = 0; colonne < getColonnes(); colonne++) {
                if (cases[ligne][colonne].getCouleur() == Couleur.VIDE) {
                    return false;
                }
            }
        }
        // Si toutes les cases sont remplies, la partie est terminée
        return true;
    }
    
    
  
}
      


    
    
   
	

