//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Base.Article;
import Base.Fournisseur;
import Base.FournisseurEtranger;
public class Main {
    public static void main(String[] args) {
        // instancier des articles
        Article article1 = new Article(1, 10.90, 100, "Tata");
        Article article2 = new Article(2, 12.99, 50, null);
        Article article3 = new Article(3, 8.99, 110, null);
        Fournisseur normal = new Fournisseur(1, "Pepito Store", "123 rue de la Rigolade", "SO-123-PIPE", 12305, "Laos");
        FournisseurEtranger etranger = new FournisseurEtranger(2, null, "En CHine quoi", null, 23000, "Laos", "Chine", "Yen", 20.50);
        // System.out.println(article1);
        System.out.println(normal);
        System.out.println(etranger);
    }

}