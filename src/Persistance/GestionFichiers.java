package Persistance;

import Base.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionFichiers {
    private static final String FICHIER_ARTICLES = "articles.txt";
    private static final String FICHIER_FOURNISSEURS = "fournisseurs.txt";

    private static HashMap<Integer, Fournisseur> fournisseurs = new HashMap<>();

    public static void sauvegardeFournisseurs(Stock stock) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_FOURNISSEURS))) {
            for (Article art : stock.getArticles()) {
                Fournisseur f = art.getFournisseur();

                if (f != null && !fournisseurs.containsKey(f.getId())) {
                    fournisseurs.put(f.getId(), f);

                    if (f instanceof FournisseurEtranger fe) {
                        writer.write("ETRANGER;" + fe.getId() + ";" +
                                fe.getRaisonSociale() + ";" +
                                fe.getAdresse() + ";" +
                                fe.getMatriculeFiscale() + ";" +
                                fe.getChiffreAffaire() + ";" +
                                fe.getGroupe() + ";" +
                                fe.getPays() + ";" +
                                fe.getDevise() + ";" +
                                fe.getCoursDeChange());
                    } else {
                        writer.write("LOCAL;" + f.getId() + ";" +
                                f.getRaisonSociale() + ";" +
                                f.getAdresse() + ";" +
                                f.getMatriculeFiscale() + ";" +
                                f.getChiffreAffaire() + ";" +
                                f.getGroupe() + ";;;");
                    }
                    writer.newLine();
                }
            }
            System.out.println("Fournisseurs sauvegardés (" + fournisseurs.size() + ")");
        } catch (IOException e) {
            System.out.println("Erreurs sauvegarde fournisseurs : " + e.getMessage());
        }
    }
    // sauvegarde des articles
    public static void sauvegardeArticles(Stock stock) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_ARTICLES))) {
            for (Article art : stock.getArticles()) {
                int idFournisseur = (art.getFournisseur() != null)
                        ? art.getFournisseur().getId()
                        : -1;
                writer.write(art.getCode() + ";" +
                        art.getPrix() + ";" +
                        art.getQuantiteStock() + ";" +
                        art.getNomArticle() + ";" +
                        idFournisseur);
                writer.newLine();
            }
            System.out.println("Articles sauvegardés (" + stock.getArticles().size() + ")");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde articles : " + e.getMessage());
        }
    }
    // charger les fournisseurs
    public static void chargerFournisseurs() {
        fournisseurs.clear();

        File fichier = new File(FICHIER_FOURNISSEURS);
        if (!fichier.exists()) {
            System.out.println("Aucun fichier fournisseurs trouvé");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int compteur = 0;

            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split(";");

                if (parties[0].equals("LOCAL")) {
                    Fournisseur f = new Fournisseur(
                            Integer.parseInt(parties[1]),
                            parties[2],
                            parties[3],
                            parties[4],
                            Integer.parseInt(parties[5]),
                            parties[6]
                    );
                    fournisseurs.put(f.getId(), f);
                    compteur++;
                } else if (parties[0].equals("ETRANGER")){
                    FournisseurEtranger fe = new FournisseurEtranger(
                            Integer.parseInt(parties[1]),
                            parties[2],
                            parties[3],
                            parties[4],
                            Integer.parseInt(parties[5]),
                            parties[6],
                            parties[7],
                            parties[8],
                            Double.parseDouble(parties[9])
                    );
                    fournisseurs.put(fe.getId(), fe);
                    compteur++;
                }
            }
            System.out.println("Fournisseurs chargés (" + compteur + ")");
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des fournisseurs : " + e.getMessage());
        }
    }
    // charger les articles
    public static void chargerArticles (Stock stock) {
        File fichier = new File(FICHIER_ARTICLES);
        if(!fichier.exists()) {
            System.out.println("Aucun fichier articles retrouvé");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int compteur = 0;

            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split(";");
                int code = Integer.parseInt(parties[0]);
                double prix = Double.parseDouble(parties[1]);
                int quantiteStock = Integer.parseInt(parties[2]);
                String nomArticle = parties[3];
                int idFournisseur = Integer.parseInt(parties[4]);

                Fournisseur f = (idFournisseur != -1)
                        ? fournisseurs.get(idFournisseur)
                        : null;
                Article article = new Article(code, prix, quantiteStock, nomArticle, f);
                stock.insertion(article);
                compteur++;
            }
            System.out.println("Articles chargés (" + compteur + ")");
        } catch (Exception e) {
            System.out.println("Erreur avec le chargement des articles : " + e.getMessage());
        }
    }
    // sauvegarder
    public static void sauvegarderFichiers(Stock stock) {
        System.out.println("Sauvegarde en cours ...");
        sauvegardeFournisseurs(stock);
        sauvegardeArticles(stock);
        System.out.println("Sauvegarde terminée !\n");
    }
    // charger les fichiers
    public static void chargerFichiers(Stock stock) {
        System.out.println("\n Chargement des donées...");
        chargerFournisseurs();
        chargerArticles(stock);
        System.out.println("Chargement terminé !\n");
    }

}
