//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Base.*;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static Scanner scanner= new Scanner(System.in);
    private static Magasin magasin;

    public static void main(String[] args) {
        // initialisation du magasin
        System.out.println("--------------BIENVENUE À VOTRE LOGICIEL DE GESTION POUR VOTRE MAGASIN---------");
        System.out.println("Nom du magasin : ");
        String nom = scanner.nextLine();
        System.out.println("Propietaire : ");
        String propietaire = scanner.nextLine();
        System.out.println("Description : ");
        String description = scanner.nextLine();

        magasin = new Magasin(nom, propietaire, description);
        System.out.println("\nMagasin crée avec succès :) ");

        menuPrincipal();
    }

    public static void menuPrincipal() {
        while (true) {
            System.out.println("  __  __                               _            _             _     \n" +
                    " |  \\/  | ___ _ __  _   _   _ __  _ __(_)_ __   ___(_)_ __   __ _| |  _ \n" +
                    " | |\\/| |/ _ \\ '_ \\| | | | | '_ \\| '__| | '_ \\ / __| | '_ \\ / _` | | (_)\n" +
                    " | |  | |  __/ | | | |_| | | |_) | |  | | | | | (__| | |_) | (_| | |  _ \n" +
                    " |_|  |_|\\___|_| |_|\\__,_| | .__/|_|  |_|_| |_|\\___|_| .__/ \\__,_|_| (_)\n" +
                    "                           |_|                       |_|                ");
            System.out.println("1. Gestion Article");
            System.out.println("2. Gestion Magasin (Vente ou Achat)");
            System.out.println(("3. Quitter le menu"));
            System.out.println("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    menuGestionArticle();
                    break;
                case 2:
                    menuGestionMagasin();
                    break;
                case 3:
                    System.out.println("Au revoir et à bientôt.");
                    return;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }
    public static void menuGestionArticle() {
        while (true) {
            System.out.println("\n--- GESTION ARTICLE ---");
            System.out.println("1. Ajouter un article");
            System.out.println("2. Afficher tous les articles");
            System.out.println("3. Supprimer un article");
            System.out.println("4. Retour au menu principal");
            System.out.println("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterArticle();
                    break;
                case 2:
                    afficherArticles();
                    break;
                case 3:
                    supprimerArticle();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }
    public static void menuGestionMagasin() {
        while (true) {
            System.out.println("\n--- GESTION MAGASIN ---");
            System.out.println("1. Vendre un article");
            System.out.println("2. Approvisionner");
            System.out.println("3. Afficher le stock");
            System.out.println("4. Retour au menu principal");
            System.out.println("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    vendreArticle();
                    break;
                case 2:
                    approvisionner();
                    break;
                case 3:
                    afficherStock();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }
    // fonctions utilitaires

    // AJOUTER ARTICLE
    public static void ajouterArticle() {
        System.out.println("\n--- AJOUTER UN ARTICLE ---");
        System.out.println("Code : ");
        int code = scanner.nextInt();
        System.out.println("Prix : ");
        double prix = scanner.nextDouble();
        System.out.println("Quantité en stock : ");
        int qteStock = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nom de l'article : ");
        String nom = scanner.nextLine();

        System.out.println("\nCet article a-t-il un fournisseur ? (O/N)");
        String reponse = scanner.nextLine().toUpperCase();

        Fournisseur fournisseur = null;

        if (reponse.equals("O")) {
            System.out.println("\nType de fournisseur : ");
            System.out.println("1. Fournisseur local");
            System.out.println("2. Fournisseur étranger");
            System.out.println("Votre choix : ");
            int typeFournisseur = scanner.nextInt();
            scanner.nextLine();

            if (typeFournisseur == 1) {
                fournisseur = creerFournisseurLocal();
            } else if (typeFournisseur == 2){
                fournisseur = creerFournisseurEtranger();
            }
        }
        Article article = new Article(code, prix, qteStock, nom, fournisseur);
        magasin.getStock().insertion(article);
    }
    // AFFICHER LES ARTICLES
    public static void afficherArticles() {
        System.out.println("\n----- LISTE DES ARTICLES -----");

        if (magasin.getStock().getArticles().isEmpty()) {
            System.out.println("Aucun article en stock");
        } else {
            for (Article art : magasin.getStock().getArticles()) {
                System.out.println(art.toString());
                System.out.println("-------------------------");
            }
        }
    }
    // creer un fournisseur local

    public static Fournisseur creerFournisseurLocal() {
        System.out.println("\n-- FOURNISSEUR LOCAL ---");
        System.out.print("ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Raison sociale : ");
        String raison = scanner.nextLine();
        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();
        System.out.print("Matricule fiscale : ");
        String matricule = scanner.nextLine();
        System.out.print("Chiffre d'affaires : ");
        int caffaires = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Groupe : ");
        String groupe = scanner.nextLine();

        try {
            return new Fournisseur(id, raison, adresse, matricule, caffaires, groupe);
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
            return null;
        }
    }
    // CRÉER UN FOURNISSEUR ÉTRANGER
    public static FournisseurEtranger creerFournisseurEtranger() {
        System.out.println("\n--- FOURNISSEUR ÉTRANGER ---");
        System.out.println("ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Raison sociale : ");
        String raison = scanner.nextLine();
        System.out.println("Adresse : ");
        String adresse = scanner.nextLine();
        System.out.print("Matricule fiscale : ");
        String matricule = scanner.nextLine();
        System.out.print("Chiffre d'affaires : ");
        int caffaires = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Groupe : ");
        String groupe = scanner.nextLine();
        System.out.println("Pays : ");
        String pays = scanner.nextLine();
        System.out.println("Devise : ");
        String devise = scanner.nextLine();
        System.out.println("Cours de change : ");
        double coursChange = scanner.nextDouble();
        scanner.nextLine();

        try {
            return new FournisseurEtranger(id, raison, adresse, matricule, caffaires, groupe, pays, devise, coursChange);
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
            return null;
        }
    }
    // VENDRE ARTICLE
    public static void vendreArticle() {
        System.out.println("\n--- VENTE ---");
        System.out.println("Code de l'article : ");
        int code = scanner.nextInt();
        System.out.println("Quantité à vendre : ");
        int qte = scanner.nextInt();

        double total = magasin.getStock().vendre(code, qte);
        if (total > 0) {
            System.out.println("Total à payer : " + total + " €");
        }
    }
    // APPROVISIONNER
    public static void approvisionner() {
        System.out.println("\n--- APPROVISSIONER ---");
        System.out.println("Code de l'article : ");
        int code = scanner.nextInt();
        System.out.println("Quantité à ajouter : ");
        int qte = scanner.nextInt();

        magasin.getStock().achat(code, qte);
    }
    // AFFICHER LE STOCK
    public static void afficherStock() {
        magasin.getStock().affiche();
    }
    // SUPPRIMER ARTICLE
    public static void supprimerArticle() {
        System.out.println("\n--- SUPPRIMER UN ARTICLE ---");
        System.out.println("Code de l'article : ");
        int code = scanner.nextInt();

        magasin.getStock().supprime(code);
    }




}