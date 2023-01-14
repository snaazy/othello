package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * Classe permettant de sauvegarder et charger des parties 
 * @author MEZRAGUE Chanez, FEKIH HASSEN Yassine
 * @version 1.0
 */

public class Sauvegarder {
    
    private Othellier othellier;
    private Couleur tour;


    /**
     * Methode permettant de sauvegarder une partie dans un fichier
     * @param nomfichier le nom du fichier de sauvegarde
     * @param othellier l'objet othellier à sauvegarder
     * @param tour la couleur indiquant le joueur
     */
    public void sauvegarderPartie(String nomfichier, Othellier othellier, Couleur tour) {
        try (FileWriter writer = new FileWriter(nomfichier)) {
            writer.write(othellier.getLignes() + "\n");
            writer.write(othellier.getColonnes() + "\n");
            for (int i = 0; i < othellier.getLignes(); i++) {
                for (int j = 0; j < othellier.getColonnes(); j++) {
                    writer.write(othellier.cases[i][j].getCouleur().toString() + "\n");
                }
            }
            writer.write(tour.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode permettant de charger une partie depuis un fichier
     * @param nomfichier le nom du fichier de sauvegarde
     * @param othellier l'objet othellier à remplir avec les informations sauvegardées dans le fichier
     * @param tour la couleur à remplir avec le joueur dont c'est le tour dans la partie sauvegardée
     */
    public void chargerPartie(String nomfichier, Othellier othellier, Couleur tour) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomfichier))) {
            int lignes = Integer.parseInt(reader.readLine());
            int colonnes = Integer.parseInt(reader.readLine());
            othellier.setLignes(lignes);
            othellier.setColonnes(colonnes);
            othellier.cases = new Case[lignes][colonnes];
            for (int i = 0; i < othellier.getLignes(); i++) {
                for (int j = 0; j < othellier.getColonnes(); j++) {
                    String couleurStr = reader.readLine();
                    if ("NOIR".equals(couleurStr)) {
                        othellier.cases[i][j] = new Case(Couleur.NOIR);
                    } else if ("BLANC".equals(couleurStr)) {
                        othellier.cases[i][j] = new Case(Couleur.BLANC);
                    } else if ("VIDE".equals(couleurStr)) {
                        othellier.cases[i][j] = new Case(Couleur.VIDE);
                    } else {

                    }
                }
            }
            String s = reader.readLine();
            if ("NOIR".equals(s)) {
                tour = Couleur.NOIR;
            } else if ("BLANC".equals(s)) {
                tour = Couleur.BLANC;
            } else {
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void demanderCharger() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("                                 (voulez-vous charger une partie?) [oui][non]");
        String reponse = scanner.nextLine();
        if ("oui".equals(reponse)) {
            System.out.println("(veuillez entrer le nom du fichier : )");
            String nomFichier = scanner.nextLine();
            File fichier = new File(nomFichier);
            if (fichier.exists()) {
                chargerPartie(nomFichier, othellier, tour);
            } else {
                System.out.println("(ce fichier n'existe pas, reprise du jeu...)");
            }
        }
    }

    public void demanderSauvegarder(Othellier othellier, Couleur tour){
        this.othellier = othellier;
        this.tour = tour;
        Scanner scanner = new Scanner(System.in);
        Sauvegarder sauvegarder = new Sauvegarder();
        while (true) {
            System.out.println("                             (voulez-vous sauvegarder la partie?) [oui][non]");
            String response = scanner.nextLine();
            if (response.equals("oui")) {
                sauvegarder.sauvegarderPartie("partiesauvegardee.txt", othellier, tour);
                System.out.println("                         (partie sauvegardée avec succès)");
                break;
            } else if (response.equals("non")) {
                System.out.println(" ");
                break;
            } else {
                System.out.println("veuillez entrer [oui] ou [non]");
            }
        }
    }

    
}