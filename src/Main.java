//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Base.*;

public class Main {
    public static void main(String[] args) {
        // instancier des articles
        Stock stock = new Stock();

        Fournisseur f = new Fournisseur(1, "TechCorp", "Paris", "FR-001", 50000, "Tech");

        Article a1 = new Article(105, 29.99, 50, "Clavier", f);
        Article a2 = new Article(102, 15.50, 100, "Souris");

        stock.insertion(a1);
        stock.insertion(a2);
        stock.affiche();
    }

}