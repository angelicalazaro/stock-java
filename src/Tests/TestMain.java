package Tests;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Base.*;

public class TestMain {
    public static void main(String[] args) {
        // creer un magasin
        Magasin techMagasin = new Magasin("TechClé", "Jean DuClé", "Magasin de matériel technologique en plein centre de Bogotá");
        // creer un fournisseur
        Fournisseur fournisseurLocal = new Fournisseur(1, "SideCorp", "44 rue de la Tech", "FR-001", 80000, "Tech");
        FournisseurEtranger fournisseurChine = new FournisseurEtranger(2, "ChinaTech", "Beijing", "CH-002", 150000, "Tech", "Chine", "Yuan", 0.13);
        Article a1 = new Article(1, 5000, 5, "iMac", fournisseurLocal);
        Article a2 = new Article(2, 250, 10, "Clavier LogiTech", fournisseurLocal);
        Article a3 = new Article(3, 500, 50, "Casque Bose", fournisseurChine);
        Article a4 = new Article(4, 150, 200, "Souris Logitech", fournisseurLocal);

        techMagasin.getStock().insertion(a1);
        techMagasin.getStock().insertion(a2);
        techMagasin.getStock().insertion(a3);
        techMagasin.getStock().insertion(a4);

        // afficher le stock

        techMagasin.getStock().affiche();

        // vendre des articles
        techMagasin.getStock().vendre(1, 2);
        techMagasin.getStock().vendre(2, 68);
        techMagasin.getStock().vendre(3, 17);
        techMagasin.getStock().vendre(4, 89);

        techMagasin.getStock().affiche();

        // approvisionnement

        techMagasin.getStock().achat(1, 15);
        techMagasin.getStock().achat(2, 15);
        techMagasin.getStock().achat(3, 15);
        techMagasin.getStock().achat(4, 15);

        techMagasin.getStock().affiche();

        // supprimer

        techMagasin.getStock().supprime(2);

        techMagasin.getStock().affiche();

    }

}