package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * Classe Othello
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 */

public class Othello {
    
    /* Déclaration de l'objet othellier */
    private Othellier othellier;
    /* Couleur tour representant le joueur actuel (noir ou blanc) */
    private Couleur tour;
    /* Objet de la classe Scanner qui permet de lire les entrées de l'utilisateur */
    private static Scanner in = new Scanner(System.in);
   

    public Othello(){
        messageBienvenue();
        demanderDimension();
        othellier = new Othellier(othellier.getLignes(),othellier.getColonnes());
        othellier.pionsParDefaut();
        demanderModeDeJeu();
    }

     public void messageBienvenue(){
        System.out.println();
        System.out.println(" 〈     BIENVENUE SUR LE JEU OTHELLO     〉   ");
        System.out.println();
        System.out.println(" ⁃ Programme écrit par MEZRAGUE Chanez & FEKIH HASSEN Yassine ⁃");
        System.out.println();
        System.out.println("   ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
    }

     /**
     * Demande au joueur de choisir le mode de jeu (contre un humou ou contre un ordinateur)
     * et lance le jeu correspondant.
     */
    public void demanderModeDeJeu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println(" ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯ CHOIX MODE DE JEU ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯  ");
             String horizontalLine = ". . . . . . . . . . . . . . . . . . . . . . . . .";
             String verticalLine = ".";
             System.out.println(horizontalLine);
             System.out.println(verticalLine + "                                               " + verticalLine);
             System.out.println(verticalLine + " (1) Jouer contre un humain.                   " + verticalLine);
             System.out.println(verticalLine + " (2) Jouer contre un ordinateur facile.        " + verticalLine);
             System.out.println(verticalLine + " (3) Jouer contre un ordinateur difficile.     " + verticalLine);
             System.out.println(verticalLine + "                                               " + verticalLine);
             System.out.println(horizontalLine); 
             System.out.println();
             System.out.println(" ▾ Saisi ▾ ");
            String choix = scanner.nextLine();
            if (choix.equals("1")) {
                jouer();
            } else if (choix.equals("2")) {
                jouerContreOrdinateurAleatoire();
            } else if(choix.equals("3")){
                jouerContreOrdinateurMiniMax();
            } else {
                System.out.println("Veuillez entrer 1 ou 2.");
            }
        }
    }


    /**
     * Méthode qui demande à l'utilisateur de saisir la dimension de l'othellier
     * et vérifie si les valeurs saisies sont valides (paires). Si les valeurs 
     * sont valides, elles sont affectées aux variables othellier.getLignes() et othellier.getColonnes().
     */
    public void demanderDimension() {
        // initialisation de la variable qui indique si la valeur saisie est correct
        boolean estValide = false;
        int ligne;
        // boucle qui demande à l'utilisateur de saisir le nombre de lignes de l'othellier et vérifie si 
        // cette valeur est pair
        do {
            System.out.println();
            System.out.println(" /////////////// CHOIX DIMENSION OTHELLIER ///////////////");
            System.out.print("\n ➤ Nombre de lignes : ");
            ligne = in.nextInt();
            // vérification de la validité de la valeur
            if (ligne%2 == 0) {
                estValide = true;
            } else {
                // message d'erreur
                System.out.println("\nincorrect");
            }
        } while (!estValide);
        // réinitialisation de la variable qui indique si la valeur est correct
        estValide = false;
        int colonne;
        // boucle qui demande à l'utilisateur de saisir le nombre de colonne de l'othellier
        do {
            System.out.print("\n ➤ Nombre de colonnes :  ");
            colonne = in.nextInt();
            System.out.println();
            if (colonne%2 == 0) {
                estValide = true;
            } else {
                System.out.println("Veuillez entrer un nombre correct");
            }
        } while (!estValide);
        // instanciation de l'objet othellier avec les valeurs saisies par l'utilisateur
        othellier = new Othellier(ligne,colonne);
    }

    /**
     * Lance une partie de jeu contre un ordinateur.
     * L'ordinateur joue aléatoirement.
     */
    public void jouerContreOrdinateurAleatoire() {
        tour = Couleur.NOIR;
        boolean partieTerminee = false;
        while (!partieTerminee) {
            othellier.dessiner();
            if (!estCoupValide()) {
                System.out.println("\nLe joueur " + tour + " ne peut pas rien faire !");
                changerTour();
                if (!estCoupValide()) {
                    othellier.trouverGagnant();
                    partieTerminee = true;
                    return;
                } else { 
                    System.out.println("C'est au tour du joueur " + tour + " encore !");
                }
            } else {
                System.out.println("\n");
                System.out.println(" ☞ Au tour du joueur " + tour + "\n");
            }
            if (tour == Couleur.NOIR) {
                faireUnCoup();
            } else {
                System.out.println("Coups possibles de l'ordinateur (Arbre) : ");
                Noeud arbre = coupsPossiblesOrdinateurArbre(othellier, tour);
                arbre.afficherArbre();
                faireUnCoupOrdinateurAmeliore();
            }
            changerTour();
        }
    }


    public void jouerContreOrdinateurMiniMax() {
        tour = Couleur.NOIR;
        boolean partieTerminee = false;
        while (!partieTerminee) {
            othellier.dessiner();
            if (!estCoupValide()) {
                System.out.println("\nLe joueur " + tour + " ne peut pas rien faire !");
                changerTour();
                if (!estCoupValide()) {
                    othellier.trouverGagnant();
                    partieTerminee = true;
                    return;
                } else { 
                    System.out.println("C'est au tour du joueur " + tour + " encore !");
                }
            } else {
                System.out.println("\n");
                System.out.println(" ☞ Au tour du joueur " + tour + "\n");
            }
            if (tour == Couleur.NOIR) {
                faireUnCoup();
            } else {
                System.out.println("Coups possibles de l'ordinateur (Arbre) : ");
                Noeud arbre = coupsPossiblesOrdinateurArbre(othellier, tour);
                arbre.afficherArbre();
                int[] meilleurCoup = choisirMeilleurCoupMinimax(othellier, tour, 5);
                if(meilleurCoup != null){
                     faireUnCoup(othellier, tour, meilleurCoup[0], meilleurCoup[1]);
                }
            }
            changerTour();
        }
    }



    /**
     * Méthode qui permet de changer le tour de jeu (couleur du joueur qui joue).
     * Si le tour actuel est noir la méthode met à jour la valeur de la variable pour indiquer
     * que c'est le tour du joueur blanc.
     */
    public void changerTour(){ // pour changer le tour/couleur
        if(tour == Couleur.NOIR){
            tour = Couleur.BLANC;
        }else{
            tour = Couleur.NOIR;
        }
    }

    /**
     * Methode qui permet de faire un coup 
     * Demande à l'utilisateur de saisir les coordonnées et verifie si le coup est valide.
     * Si valide, le coup est joué sinon resaisie des coordonnées
     * Si aucun coup valide, un message est affiché et la méthode se termine
     */
    public void faireUnCoup(){
        if (!estCoupValide()) {
            System.out.println("\nAucun mouvement possible, au tour du joueur suivant");
            return;
        }
        boolean tourTermine = false;
        while (!tourTermine) {
            Scanner in = new Scanner(System.in);
            System.out.print("\nLigne : ");
            int ligne = in.nextInt() - 1;
            if (ligne < 0 || ligne >= othellier.getLignes()) {
                System.out.format("\nLe numéro de ligne doit être entre 1 et " +othellier.getLignes());
                continue;
            }
            System.out.print("Colonne : ");
            int colonne = in.nextInt() - 1;
            if (colonne < 0 || colonne >= othellier.getColonnes()) {
                System.out.format("\nLe numéro de colonne doit être entre 1 et " +othellier.getColonnes());
                continue;
            }
            if (othellier.cases[ligne][colonne].getCouleur() != Couleur.VIDE) {
                System.out.println("\nCoup impossible");
                continue;
            }
            boolean coupValide = false;
            if (verificationDirectionBas(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionBasDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionBasGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHaut(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHautDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHautGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (coupValide) {
                othellier.cases[ligne][colonne].setCouleur(tour);
                tourTermine = true;
            } else {
                System.out.println("Coup impossible");
            }
        }
    }


    /**
     * Methode qui permet de jouer une partie contre un humain
     * 
     */
    public void jouer() {
        tour = Couleur.NOIR;
        boolean partieTerminee = false;
        Sauvegarder sauvegarder = new Sauvegarder();
        sauvegarder.demanderCharger();
        while (!partieTerminee) {
            othellier.dessiner();
            if (!estCoupValide()) {
                System.out.println(tour + " ne peut pas rien faire !");
                changerTour();
                if (!estCoupValide()) {
                    othellier.trouverGagnant();
                    partieTerminee = true;
                    return;
                } else { 
                    System.out.println("encore au tour du joueur " + tour);
                }
            } else {
                System.out.format(" --> Au tour du joueur " + tour);
            }
            faireUnCoup();
            sauvegarder.demanderSauvegarder(othellier,tour);
           
            changerTour();
        }
    }
    

    public void faireUnCoup(int ligne, int colonne) {
        boolean aEteRetourne = false;
        aEteRetourne = verificationDirectionHaut(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionHautDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBasDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBas(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBasGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionHautGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        if(aEteRetourne){
            othellier.mettrePion(ligne, colonne, tour);
        }
    }
    
    /**
     * Fait jouer l'ordinateur de manière aléatoire
     */
    public void faireUnCoupOrdinateur(){
        if (!estCoupValide()) {
            System.out.println("\nAucun mouvement possible, au tour du joueur suivant");
            return;
        }
        boolean tourTermine = false;
        while (!tourTermine) {
            Scanner in = new Scanner(System.in);
            int ligne = (int) (Math.random() * othellier.getLignes());
            int colonne = (int) (Math.random() * othellier.getColonnes());
            if (othellier.cases[ligne][colonne].getCouleur() != Couleur.VIDE) {
                System.out.println("\nCoup impossible");
                continue;
            }
            boolean coupValide = false;
            if (verificationDirectionBas(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionBasDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionBasGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHaut(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHautDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionHautGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionGauche(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (verificationDirectionDroite(othellier,tour,ligne, colonne, false)) {
                coupValide = true;
            }
            if (coupValide) {
                othellier.cases[ligne][colonne].setCouleur(tour);
                tourTermine = true;
            } else {
                System.out.println("Coup impossible");
            }
        }
    }

    /**
     * Vérifie si un coup est valide pour le joueur actuel.
     * @return true si un coup est valide, false sinon
     */
    public boolean estCoupValide() {
        for (int ligne = 0; ligne < othellier.getLignes(); ligne++) {
            for (int colonne = 0; colonne < othellier.getColonnes(); colonne++) {
                if (othellier.cases[ligne][colonne].getCouleur() == Couleur.VIDE) {
                    boolean valide = false;
                    if (verificationDirectionBas(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionBasDroite(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionBasGauche(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionHaut(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionHautDroite(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionHautGauche(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionGauche(othellier,tour,ligne, colonne, true)) valide = true;
                    if (verificationDirectionDroite(othellier,tour,ligne, colonne, true)) valide = true;
                    if (valide) return true;
                }
            }
        }
        return false;
    }
    

    public void faireUnCoupOrdinateurAmeliore() {
        if (!estCoupValide()) {
            System.out.println("\nAucun mouvement possible, au tour du joueur suivant");
            return;
        }
        List<int[]> coinsVides = new ArrayList<>();
        if (othellier.cases[0][0].getCouleur() == Couleur.VIDE && (verificationDirectionBas(othellier,tour,0, 0, true) || verificationDirectionDroite(othellier,tour,0, 0, true))) {
            coinsVides.add(new int[] {0, 0});
        }
        if (othellier.cases[0][othellier.getColonnes() - 1].getCouleur() == Couleur.VIDE && (verificationDirectionBas(othellier,tour,0, othellier.getColonnes() - 1, true) || verificationDirectionGauche(othellier,tour,0, othellier.getColonnes() - 1, true))) {
            coinsVides.add(new int[] {0, othellier.getColonnes() - 1});
        }
        if (othellier.cases[othellier.getLignes() - 1][0].getCouleur() == Couleur.VIDE && (verificationDirectionHaut(othellier,tour,othellier.getLignes() - 1, 0, true) || verificationDirectionDroite(othellier,tour,othellier.getLignes() - 1, 0, true))) {
            coinsVides.add(new int[] {othellier.getLignes() - 1, 0});
        }
        if (othellier.cases[othellier.getLignes() - 1][othellier.getColonnes() - 1].getCouleur() == Couleur.VIDE && (verificationDirectionHaut(othellier,tour,othellier.getLignes() - 1, othellier.getColonnes() - 1, true) || verificationDirectionGauche(othellier,tour,othellier.getLignes() - 1, othellier.getColonnes() - 1, true))) {
            coinsVides.add(new int[] {othellier.getLignes() - 1, othellier.getColonnes() - 1});
        }
    
        if (!coinsVides.isEmpty()) {
            Random random = new Random();
            int[] coin = coinsVides.get(random.nextInt(coinsVides.size()));
            othellier.cases[coin[0]][coin[1]].setCouleur(tour);
        } else {
            boolean b = false;
            while (!b) {
                int ligne = (int) (Math.random() * othellier.getLignes());
                int colonne = (int) (Math.random() * othellier.getColonnes());
                if (othellier.cases[ligne][colonne].getCouleur() != Couleur.VIDE) {
                    //System.out.println("\nL'ordinateur a essayé de jouer sur une case vide, coup impossible");
                    continue;
                }
                boolean coupValide = false;

                if (verificationDirectionBas(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionBasDroite(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionBasGauche(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionHaut(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionHautDroite(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionHautGauche(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionGauche(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
                if (verificationDirectionDroite(othellier,tour,ligne, colonne, false)) {
                    coupValide = true;
                }
    
                if (coupValide) {
                    othellier.cases[ligne][colonne].setCouleur(tour);
                    b = true;
                } else {
                    //System.out.println("Coup impossible");
                    continue;
                }
            }
        }
    }

    /**
     * Retourne la liste des coups possibles pour l'ordinateur sur l'othellier donné.
     * 
     * @param othellier L'othellier sur lequel l'ordinateur joue.
     * @param tour La couleur de l'ordinateur.
     * @return La liste des coups possibles sous forme de tableaux d'entiers contenant les coordonnées des cases jouables.
     */
    public List<int[]> coupsPossiblesOrdinateur(Othellier othellier, Couleur tour){
        List<int[]> coupsPossibles = new ArrayList<>();
        for(int ligne = 0; ligne < othellier.getLignes(); ligne++){
            for(int colonne = 0; colonne < othellier.getColonnes(); colonne++){
                if(ligne >= 0 && ligne < othellier.getLignes() && colonne >=0 && colonne < othellier.getColonnes() && estCoupValide(othellier, tour, ligne, colonne)){
                    coupsPossibles.add(new int[]{ligne, colonne});
                }
            }
        }
        return coupsPossibles;
    }

    /**
     * Retourne le noeud racine de l'arbre des coups possibles pour l'ordinateur sur l'othellier donné.
     * Chaque noeud enfant du noeud racine représente un coup possible sous forme de coordonnées (ligne, colonne).
     * 
     * @param othellier L'othellier sur lequel l'ordinateur joue.
     * @param tour La couleur de l'ordinateur.
     * @return Le noeud racine de l'arbre des coups possibles.
     */
    public Noeud coupsPossiblesOrdinateurArbre(Othellier othellier, Couleur tour) {
        Noeud racine = new Noeud();
        for(int ligne = 0; ligne < othellier.getLignes(); ligne++) {
            for(int colonne = 0; colonne < othellier.getColonnes(); colonne++) {
                if(estCoupValide(othellier, tour, ligne, colonne)) {
                    racine.ajouterEnfant(new Noeud(ligne, colonne));
                }
            }
        }
        return racine;
    }
    
    /**
     * Verifie si un coup est valide 
     * @param othellier l'othellier sur lequel est joué le coup
     * @param joueur couleur du joueur qui joue le coup
     * @param ligne ligne ou le coup est joué
     * @param colonne colonne ou le coup est joué
     * @return true si le coup est valide, false sinon
     */
    public boolean estCoupValide(Othellier othellier, Couleur joueur, int ligne, int colonne) {
        if (othellier.cases[ligne][colonne].getCouleur() == Couleur.VIDE) {
            boolean valide = false;
            if (verificationDirectionBasDroite(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionBasGauche(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionHautDroite(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionHautGauche(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionBas(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionHaut(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionGauche(othellier, joueur, ligne, colonne, true)) valide = true;
            if (verificationDirectionDroite(othellier, joueur, ligne, colonne, true)) valide = true;

            if (valide) return true;
        }
        return false;
    }
    
     /**
     * Vérifie si un coup est possible dans la direction haut et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionHaut(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
        if (ligne > 1 && othellier.cases[ligne-1][colonne].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne;
            do {
                suivant = othellier.cases[--ligneActuelle][colonne].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse= true;
                } else if (suivant == tour && voisinInverse) {
                    if (verifieUniquement)	
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne; l > ligneActuelle ; l--)  
                        othellier.mettrePion(l, colonne, tour);
                } 
            } while (ligneActuelle-1 >= 0 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }
    
    /**
     * Vérifie si un coup est possible dans la direction de la diagonale haut-droite et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionHautDroite(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
        if (ligne > 1 && colonne < othellier.getColonnes()-2 && othellier.cases[ligne-1][colonne+1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne; 
            int colonneActuelle = colonne;
            do {
                suivant = othellier.cases[--ligneActuelle][++colonneActuelle].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse= true;
                } else if (suivant == tour && voisinInverse) { 
                    if (verifieUniquement)	
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne, c = colonne; l > ligneActuelle && c < colonneActuelle ; l--, c++)  
                        othellier.mettrePion(l, c, tour);
                }
            } while (ligneActuelle-1 >= 0 && colonneActuelle < othellier.getColonnes()-1 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }

    /**
     * Vérifie si un coup est possible dans la direction droite et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionDroite(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
    
        if (colonne < othellier.getColonnes()-2 && othellier.cases[ligne][colonne+1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int colonneActuelle = colonne;
            do {
                suivant = othellier.cases[ligne][++colonneActuelle].getCouleur();
                if(suivant == inverse){
                    voisinInverse = true;
                } else if(suivant == tour && voisinInverse){
                    if(verifieUniquement)
                        return true;
                    aEteRetourne = true;
                    for (int c = colonne; c < colonneActuelle ; c++)
                        othellier.mettrePion(ligne, c, tour);
                } 
             } while (colonneActuelle < othellier.getColonnes()-1 && suivant != Couleur.VIDE);
        } 
        return aEteRetourne;
    }
    
    /**
     * Vérifie si un coup est possible dans la direction de la diagonale bas-droite et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionBasDroite(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
        if (ligne < othellier.getLignes()-2 && colonne < othellier.getColonnes()-2 && othellier.cases[ligne+1][colonne+1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne, colonneActuelle = colonne;
            do {
                suivant = othellier.cases[++ligneActuelle][++colonneActuelle].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse= true;
                } else if (suivant == tour && voisinInverse) { 
                    if (verifieUniquement)	
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne, c = colonne; l < ligneActuelle && c < colonneActuelle ; l++, c++)  
                        othellier.mettrePion(l, c, tour);
                }
            } while (ligneActuelle < othellier.getLignes()-1 && colonneActuelle < othellier.getColonnes()-1 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }


    /**
     * Vérifie si un coup est possible dans la direction bas et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionBas(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
    
        if (ligne < othellier.getLignes()-2 && othellier.cases[ligne+1][colonne].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne;
            do {
                suivant = othellier.cases[++ligneActuelle][colonne].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse= true;
                } else if (suivant == tour && voisinInverse) { 
                    if (verifieUniquement)	
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne; l < ligneActuelle ; l++)  
                        othellier.mettrePion(l, colonne, tour);
                }
            } while (ligneActuelle < othellier.getLignes()-1 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }

    /**
     * Vérifie si un coup est possible dans la direction de la diagonale bas-gauche et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionBasGauche(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement) {
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
    
        if (ligne < othellier.getLignes()-2 && colonne > 1 && othellier.cases[ligne+1][colonne-1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne, colonneActuelle = colonne;
            do {
                suivant = othellier.cases[++ligneActuelle][--colonneActuelle].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse= true;
                } else if (suivant == tour && voisinInverse) { 
                    if (verifieUniquement)  
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne, c = colonne; l < ligneActuelle && c > colonneActuelle ; l++, c--)  
                        othellier.mettrePion(l, c, tour);
                }
            } while (ligneActuelle < othellier.getLignes()-1 && colonneActuelle > 0 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }
    
    /**
     * Vérifie si un coup est possible dans la direction gauche et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionGauche(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement){
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
    
        if (colonne > 1 && othellier.cases[ligne][colonne-1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int colonneActuelle = colonne;
            do {
                suivant = othellier.cases[ligne][--colonneActuelle].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse = true;
                } else if (suivant == tour && voisinInverse) {
                    if (verifieUniquement)  
                        return true;
                    aEteRetourne = true;
                    for (int c = colonne; c > colonneActuelle ; c--)
                        othellier.mettrePion(ligne, c, tour);
                }
            } while (colonneActuelle > 0 && suivant != Couleur.VIDE);
        }
    
        return aEteRetourne;
    }

    /**
     * Vérifie si un coup est possible dans la direction de la diagonale haut-gauche et effectue le retournement des pions
     * 
     * 
     * @param othellier L'othellier sur lequel le coup est joué.
     * @param joueur La couleur du joueur qui joue le coup.
     * @param ligne Le numéro de la ligne de la case sur laquelle le coup est joué.
     * @param colonne Le numéro de la colonne de la case sur laquelle le coup est joué.
     * @param verifieUniquement Un booléen indiquant si les pions doivent être retournés ou non.
     * @return Un booléen indiquant si au moins un pion a été retourné.
     */
    public boolean verificationDirectionHautGauche(Othellier othellier, Couleur joueur, int ligne, int colonne, boolean verifieUniquement){
        boolean aEteRetourne = false;
        Couleur inverse = (tour == Couleur.NOIR ? Couleur.BLANC : Couleur.NOIR);
        Couleur suivant;
    
        if (ligne > 1 && colonne > 1 && othellier.cases[ligne-1][colonne-1].getCouleur() == inverse) {
            boolean voisinInverse = false;
            int ligneActuelle = ligne;
            int colonneActuelle = colonne;
            do {
                suivant = othellier.cases[--ligneActuelle][--colonneActuelle].getCouleur();
                if (suivant == inverse) { 
                    voisinInverse = true;
                } else if (suivant == tour && voisinInverse) {
                    if (verifieUniquement)  
                        return true;
                    aEteRetourne = true;
                    for (int l = ligne, c = colonne; l > ligneActuelle && c > colonneActuelle; l--, c--)
                        othellier.mettrePion(l, c, tour);
                }
            } while (ligneActuelle > 0 && colonneActuelle > 0 && suivant != Couleur.VIDE);
        }
        return aEteRetourne;
    }
    

    /**
     * Méthode qui utilise l'algorithme minimax pour déterminer le meilleur coup à jouer pour l'ordinateur.
     *
     * @param othellier L'othellier sur lequel le coup doit être joué.
     * @param tour La couleur du joueur qui doit jouer (BLANC ou NOIR).
     * @param profondeur La profondeur de recherche de l'algorithme minimax. Plus la profondeur est élevée, plus l'algorithme fait de simulations de coups et meilleure est la prise de décision, mais cela a un coût en temps de calcul.
     * @return Le meilleur score (un entier) que l'ordinateur peut espérer atteindre en jouant un coup sur l'othellier donné, en utilisant l'algorithme minimax et en explorant jusqu'à la profondeur donnée.
     */
    public int minimax(Othellier othellier, Couleur tour, int profondeur) {
        if (profondeur == 0 || othellier.partieTerminee()) {
            return evaluationHeuristiques(othellier);
        }
        ArrayList<Integer> scores = new ArrayList<Integer>();
        Noeud coupsPossibles = coupsPossiblesOrdinateurArbre(othellier, tour);
        int score = 0;
        for (Noeud coup : coupsPossibles.getEnfants()) {
            Othellier othellierSimule = othellier.copier();
            faireUnCoup(othellierSimule, tour, coup.getLigne(), coup.getColonne());
            score = minimax(othellierSimule, tour.oppose(), profondeur - 1);
            scores.add(score);
        } 
        if(scores.isEmpty()){
            if(tour == Couleur.BLANC){
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        if (tour == Couleur.BLANC) {
            return Collections.max(scores);
        }else{
            return Collections.min(scores);
        }
    }
    
    /**
     * Méthode qui évalue le score d'un othellier selon différentes heuristiques.
     *
     * @param othellier L'othellier à évaluer.
     * @return Le score de l'othellier, un entier. Plus le score est élevé, plus l'othellier est avantageux pour le joueur BLANC, et plus le score est faible, plus l'othellier est avantageux pour le joueur NOIR.
     */
    public int evaluationHeuristiques(Othellier othellier) {
        int score = 0;
        for (int ligne = 0; ligne < othellier.getLignes(); ligne++) {
            for (int colonne = 0; colonne < othellier.getColonnes(); colonne++) {
                Case c = othellier.getCases()[ligne][colonne];
                if (c.getCouleur() == Couleur.VIDE) {
                    continue;
                }
                if (c.getCouleur() == Couleur.NOIR) {
                    int distanceCentre = Math.abs(ligne - (othellier.getLignes()-1)) + Math.abs(colonne - (othellier.getColonnes()-1));
                    int pointsDistanceCentre = (othellier.getLignes()-1) - distanceCentre;
                    int pointsCoins = (ligne == 0 || ligne == (othellier.getLignes()-1)) && (colonne == 0 || colonne == (othellier.getLignes()-1)) ? 5 : 0;
                    int pointsCasesLibresAutour = 0;
                    for(int i = -1; i<=1; i++){
                        for(int j =-1; j<=1; j++){
                            if(i==0 && j==0) continue;
                            int ligneAdjacente = ligne+i;
                            int colonneAdjacente = colonne +j;
                            if(ligneAdjacente<0||ligneAdjacente > (othellier.getLignes()-1) || colonneAdjacente < 0 || colonneAdjacente > (othellier.getLignes()-1)) continue;
                            Case cAdjacente = othellier.getCases()[ligneAdjacente][colonneAdjacente];
                            if(cAdjacente.getCouleur()==Couleur.VIDE) pointsCasesLibresAutour++;
                        }
                    }
                    score += 1 + pointsDistanceCentre + pointsCoins + pointsCasesLibresAutour;
                } else {
                    int distanceCentre = Math.abs(ligne - (othellier.getLignes()-1)) + Math.abs(colonne - (othellier.getColonnes()-1));
                    int pointsDistanceCentre = (othellier.getLignes()-1) - distanceCentre;
                    int pointsCoins = (ligne == 0 || ligne == (othellier.getLignes()-1)) && (colonne == 0 || colonne == (othellier.getLignes()-1)) ? 5 : 0;
                    int pointsCasesLibresAutour = 0;
                    for(int i = -1;i<=1;i++){
                        for(int j = -1; j<=1;j++){
                            if(i==0 && j==0) continue;
                            int ligneAdjacente = ligne+i;
                            int colonneAdjacente = colonne+j;
                            if(ligneAdjacente<0 || ligneAdjacente > (othellier.getLignes()-1) || colonneAdjacente < 0 || colonneAdjacente > (othellier.getLignes()-1)) continue;
                            Case cAdjacente = othellier.getCases()[ligneAdjacente][colonneAdjacente];
                            if(cAdjacente.getCouleur() == Couleur.VIDE) pointsCasesLibresAutour++;
                        }
                    }
                    score -= 1 + pointsDistanceCentre + pointsCoins + pointsCasesLibresAutour;
                }
            }
        }
        return score;
    }
    
    /**
     * Méthode qui utilise l'algorithme minimax pour déterminer le meilleur coup à jouer pour l'ordinateur sur un othellier donné.
     *
     * @param othellier L'othellier sur lequel le coup doit être joué.
     * @param tour La couleur du joueur qui doit jouer (BLANC ou NOIR).
     * @param profondeur La profondeur de recherche de l'algorithme minimax. Plus la profondeur est élevée, plus l'algorithme fait de simulations de coups et meilleure est la prise de décision, mais cela a un coût en temps de calcul.
     * @return Un tableau de deux entiers, représentant la ligne et la colonne du meilleur coup à jouer, ou null si aucun coup n'est possible.
     */
    public int[] choisirMeilleurCoupMinimax(Othellier othellier, Couleur tour, int profondeur) {
        Noeud coupsPossibles = coupsPossiblesOrdinateurArbre(othellier, tour);
        int ligneMeilleurCoup = -1, colonneMeilleurCoup = -1;
        int valeurMeilleurCoup = (tour == Couleur.BLANC) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    
        for (Noeud coup : coupsPossibles.getEnfants()) {
            Othellier othellierSimule = othellier.copier();
            faireUnCoup(othellierSimule, tour, coup.getLigne(), coup.getColonne());
            int valeurCoup = minimax(othellierSimule, tour.oppose(), profondeur - 1);
            if ((tour == Couleur.BLANC && valeurCoup > valeurMeilleurCoup) ||
                (tour == Couleur.NOIR && valeurCoup < valeurMeilleurCoup)) {
                valeurMeilleurCoup = valeurCoup;
                ligneMeilleurCoup = coup.getLigne();
                colonneMeilleurCoup = coup.getColonne();
            }
        }
        if (ligneMeilleurCoup == -1 || colonneMeilleurCoup == -1) {
            return null;
        }
        return new int[] {ligneMeilleurCoup, colonneMeilleurCoup};
    }
    

    public void faireUnCoup(Othellier othellier, Couleur tour, int ligne, int colonne) {
        boolean aEteRetourne = false;
        aEteRetourne = verificationDirectionHaut(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionHautDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBasDroite(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBas(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionBasGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        aEteRetourne = verificationDirectionHautGauche(othellier, tour, ligne, colonne, false) || aEteRetourne;
        if(aEteRetourne){
            othellier.mettrePion(ligne, colonne, tour);
        }
    }

    
}
    







