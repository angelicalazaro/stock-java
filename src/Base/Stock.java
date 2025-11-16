package Base;

import java.util.ArrayList;

public class Stock {
    private ArrayList<Article> articles;

    public Stock() {
        this.articles = new ArrayList<>();
        System.out.println("Stock vide créé");
    }
    // prend un Stock "autre" en parametre
    public Stock(Stock autre) {
        this.articles = new ArrayList<>();
        for (Article art : autre.articles) {
            this.articles.add(new Article(art));
        }
        System.out.println("Stock copié (" + articles.size() + " articles)");
    }
    private void trierParCode() {
        for(int i = 0; i < articles.size() - 1; i++) {
            for (int j = 0; j < articles.size() - i - 1; j++) {
                if (articles.get(j).compare(articles.get(j + 1)) > 0) {
                    Article temp = articles.get(j);
                    articles.set(j, articles.get(j + 1));
                    articles.set(j + 1, temp);
                }
            }
        }
    }
    public boolean insertion(Article nouvelArticle) {
        for (Article art : articles) {
            if (art.getCode() == nouvelArticle.getCode()) {
                System.out.println("Code " + nouvelArticle.getCode() + " existe déjà !");
                return false;
            }
        }
        articles.add(nouvelArticle);
        trierParCode();
        System.out.println("Article " + nouvelArticle.getCode() + " ajouté.");
        return true;
    }
    public double prix(int code) {
        for (Article art : articles) {
            if (art.getCode() == code) {
                return art.getPrix();
            }
        }
        System.out.println("Article " + code + " non trouvé");
        return -1;
    }

    public double vendre(int code, int quantite) {
        for (Article art : articles) {
            if (art.getCode() == code) {
                if (art.getQuantiteStock() < quantite) {
                    System.out.println("Stock insuffisant ! (Dispo: " +
                            art.getQuantiteStock() + ", Demandé : " + quantite + ")");
                    return -1;
                }
            art.setQuantiteStock(art.getQuantiteStock() - quantite);
            double prixTotal = art.getPrix() * quantite;
            System.out.println("Vente : " + quantite + "x " + art.getNomArticle() +
                    " = " + prixTotal + " €");
            return prixTotal;
            }
        }
        System.out.println("Article " + code + " non trouvé");
        return -1;
    }

    public void achat(int code, int quantite) {
        for (Article art : articles) {
            if (art.getCode() == code) {
                art.setQuantiteStock(art.getQuantiteStock() + quantite);
                System.out.println("Stock mis à jour : " + art.getNomArticle() +
                        " (nouveau stock : " + art.getQuantiteStock() + ")");
                return;
            }
        }
        System.out.println("Article " + code + " non trouvé. Utilisez insertion() pour l'ajouter.");
    }

    public boolean supprime(int code) {
        for (int i = 0; i < articles.size(); i++) {
            Article art = articles.get(i);
            if (art.getCode() == code) {
                if (art.getQuantiteStock() == 0) {
                    articles.remove(i);
                    System.out.println("Article " + code + " supprimé");
                    return true;
                } else {
                    System.out.println("Impossible de supprimer : stock = " +
                            art.getQuantiteStock() + " (doit être 0)");
                    return false;
                }
            }
        }
        System.out.println("Article " + code + " non trouvé");
        return false;
    }
    public void affiche() {
        System.out.println("\n -----STOCK DU MAGASIN-----");

        if (articles.isEmpty()) {
            System.out.println("Stock vide");
        } else {
            for (Article art : articles) {
                String fournisseurNom = (art.getFournisseur() != null)
                        ? art.getFournisseur().getRaisonSociale()
                        : "Aucun";
                System.out.println("Code : " + art.getCode() +
                        " | Prix : " + art.getPrix() +
                        " | Stock : " + art.getQuantiteStock() +
                        " | Nom : " + art.getNomArticle() +
                        " | Fournisseur : " + fournisseurNom);
            }
        }
        System.out.println("---------------------\n");
    }
    @Override
    public String toString() {
        return "Stock contenant " + articles.size() + " article(s)";
    }
    public ArrayList<Article> getArticles(){
        return articles;
    }
}
