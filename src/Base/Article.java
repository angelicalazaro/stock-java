package Base;//1- Article, qui décrit un article. Cette classe offre les opérations suivantes :
//        - Constructeur
//- GetCode : retourne la valeur du code
//- GetPrix : retourne le prix
//- GetQtitStock : retourne la quantité en stock d’un article.
//        - SetCode : modifie le code
//- SetPrix : modifie le prix de l’article
//- SetQtitStock : modifie la quantité en stock de l’article
//- Compare : qui permet de compare deux articles selon leur code.

public class Article {
    private int code;
    private double prix;
    private int quantiteStock;
    private String nomArticle;
    private Fournisseur fournisseur;

    public Article (int code, double prix, int quantiteStock, String nomArticle, Fournisseur fournisseur) {
        this.code = code;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        this.nomArticle = nomArticle;
        this.fournisseur = fournisseur;
    }    public Article (int code, double prix, int quantiteStock, String nomArticle) {
        this.code = code;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        this.nomArticle = nomArticle;
        this.fournisseur = null;
    }

    public Article (Article autre) {
        this.code = autre.code;
        this.prix = autre.prix;
        this.quantiteStock = autre.quantiteStock;
        this.nomArticle = autre.nomArticle;
        this.fournisseur = autre.fournisseur;
    }
    public int getCode() {
        return code;
    }
    public double getPrix() {
        return prix;
    }
    public int getQuantiteStock() {
        return quantiteStock;
    }
    public String getNomArticle() { return nomArticle; }
    public Fournisseur getFournisseur() {return fournisseur;}
    public void setCode(int code) {
        this.code = code;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }
    public void setNomArticle(String nomArticle) {this.nomArticle = nomArticle; }
    public void setFournisseur(Fournisseur fournisseur) {this.fournisseur = fournisseur;}

    public int compare(Article a) {
        if (this.code < a.code) return -1;
        if (this.code > a.code) return 1;
        return 0;
        }

    // rédefinir la méthode toString() ->
    @Override
    public String toString() {
        String result = "Article " + ": " +
                "\ncode : " + code +
                "\nprix : " + prix +
                "\nquantité en stock : " + quantiteStock +
                "\nnom de l'article : " + nomArticle;
        if (fournisseur != null) {
            result += "\nFournisseur : " + fournisseur.getRaisonSociale();
        }
        return result;
    }

}
