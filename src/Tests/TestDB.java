import BD.dbUtil;
import BD.dbUtil;
import Base.*;

public class TestDB {
    public static void main(String[] args) {
        // Test de connexion
        dbUtil db = new dbUtil();
        db.testConnection();

        // Test CRUD
        System.out.println("\n========== TEST CRUD ==========");

        // CREATE
        Article a1 = new Article(999, 99.99, 50, "Article Test");
        db.insererArticle(a1);

        // READ
        db.afficherArticles();

        // UPDATE
        db.modifierStock(999, 100);
        db.afficherArticles();

        // DELETE
        db.supprimerArticle(999);
        db.afficherArticles();
    }
}