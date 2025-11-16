package BD;
import Base.Article;
import Base.Stock;
import Base.Fournisseur;
import Base.FournisseurEtranger;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashMap;

public class dbUtil {
    private static String dbURL = "jdbc:mysql://localhost:3306/gestionStock";
    private static String dbUserName = "root";
    private static String dbPwd = "";

    private static Connection connection = null;

    public dbUtil() {

    }

    public dbUtil(String dbURL, String dbUserName, String dbPwd) {
        this.dbURL = dbURL;
        this.dbUserName = dbUserName;
        this.dbPwd = dbPwd;
    }
    // connexion

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connexion à la base de données");
                connection = DriverManager.getConnection(dbURL, dbUserName, dbPwd);
                System.out.println("Connexion réussie");
                return connection;
            } else {
                return connection;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    // test connexion
    public boolean testConnection() {
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUserName, dbPwd);
            System.out.println("Test de connexion réussi");
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Test de connexion échoué : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // C R U D ARTICLES

    // Create un article
    public void insererArticle(Article a) {
        try {
            Connection conn = getConnection();
            String query = "INSERT INTO Article VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, a.getCode());
            stmt.setDouble(2, a.getPrix());
            stmt.setString(3, a.getNomArticle());
            stmt.setInt(4, a.getQuantiteStock());

            int result = stmt.executeUpdate();
            System.out.println("Article " + a.getCode() + " inseré (" + result + " ligne(s) affectée (s))");
        } catch (SQLException e) {
            System.out.println("Erreur d'insertion d'article " + e.getMessage());
        }
    }
    // Read un article
    public void afficherArticles() {
        try {
            Connection conn = getConnection();
            String query = "SELECT * FROM Article";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- ARTICLES DANS LA BASE ---");
            while (rs.next()) {
                int code = rs.getInt("codeArticle");
                double prix = rs.getDouble("prixArticle");
                String nom = rs.getString("nomArticle");
                int stock = rs.getInt("qtiteStock");

                System.out.println("Code : " + code + " | Prix : " + prix + "€ | Nom " + nom + " | Quantité en stock : " + stock );
            }
            System.out.println("-----------------------\n");
        } catch (SQLException e) {
            System.out.println("Erreur de lecture de l'article : " + e.getMessage());
        }
    }
    // Update la quantité d'un article
    public void modifierStock(int code, int nouvelleQuantite) {
        try {
            Connection conn = getConnection();
            String query = "UPDATE Article SET qtiteStock = ? WHERE codeArticle = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, nouvelleQuantite);
            stmt.setInt(2, code);

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Stock de l'article " + code + " mis à jour");
            } else {
                System.out.println("Article non trouvé");
            }
        } catch (SQLException e) {
            System.out.println("Erreur dans la modification de la quantité du stock : " + e.getMessage());
        }
    }
    // Delete un article
    public void supprimerArticle(int code) {
        try {
            Connection conn = getConnection();
            String query = "DELETE FROM Article WHERE codeArticle = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, code);

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Article " + code + " supprimé");
            } else {
                System.out.println("Article " + code + " non trouvé");
            }
        } catch (SQLException e) {
            System.out.println("Erreur dans la suppression de l'article : " + e.getMessage());
        }
    }

    // sauvegarder le stock
    public void sauvegarderStock(Stock stock) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Article");
            stmt.executeUpdate("DELETE FROM Fournisseur");
            System.out.println("Tables vidées");

            HashMap<Integer, Fournisseur> fournisseursSauvegardes = new HashMap<>();

            for (Article art : stock.getArticles()) {
                Fournisseur f = art.getFournisseur();
                if (f != null && !fournisseursSauvegardes.containsKey(f.getId())) {
                    insererFournisseur(f);
                    fournisseursSauvegardes.put(f.getId(), f);
                }
            }

            System.out.println(fournisseursSauvegardes.size() + " fournisseur(s) sauvegardé(s)");

            String query = "INSERT INTO Article VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            int compteur = 0;
            for (Article art : stock.getArticles()) {
                pstmt.setInt(1, art.getCode());
                pstmt.setDouble(2, art.getPrix());
                pstmt.setString(3, art.getNomArticle());
                pstmt.setInt(4, art.getQuantiteStock());
                if (art.getFournisseur() != null) {
                    pstmt.setInt(5, art.getFournisseur().getId());
                } else {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }
                pstmt.executeUpdate();
                compteur++;
            }

            System.out.println(compteur + " article(s) sauvegardé(s) dans la BD");

        } catch (SQLException e) {
            System.out.println("Erreur sauvegarde stock : " + e.getMessage());
        }
    }
    // charger le stock
    public void chargerStock(Stock stock) {
        try {
            Connection conn = getConnection();
            HashMap<Integer, Fournisseur> fournisseurs = new HashMap<>();
            String queryFournisseurs = "SELECT * FROM Fournisseur";
            Statement stmtF = conn.createStatement();
            ResultSet rsF = stmtF.executeQuery(queryFournisseurs);

            while (rsF.next()) {
                int id = rsF.getInt("idFournisseur");
                String type = rsF.getString("type");

                if ("ETRANGER".equals(type)) {
                    FournisseurEtranger fe = new FournisseurEtranger(
                            id,
                            rsF.getString("raisonSociale"),
                            rsF.getString("adresse"),
                            rsF.getString("matriculeFiscale"),
                            rsF.getInt("chiffreAffaire"),
                            rsF.getString("groupe"),
                            rsF.getString("pays"),
                            rsF.getString("devise"),
                            rsF.getDouble("coursDeChange")
                    );
                    fournisseurs.put(id, fe);
                } else {
                    Fournisseur f = new Fournisseur(
                            id,
                            rsF.getString("raisonSociale"),
                            rsF.getString("adresse"),
                            rsF.getString("matriculeFiscale"),
                            rsF.getInt("chiffreAffaire"),
                            rsF.getString("groupe")
                    );
                    fournisseurs.put(id, f);
                }
            }

            System.out.println(fournisseurs.size() + " fournisseur(s) chargé(s)");

            String queryArticles = "SELECT * FROM Article";
            Statement stmtA = conn.createStatement();
            ResultSet rsA = stmtA.executeQuery(queryArticles);

            int compteur = 0;
            while (rsA.next()) {
                int code = rsA.getInt("codeArticle");
                double prix = rsA.getDouble("prixArticle");
                String nom = rsA.getString("nomArticle");
                int qte = rsA.getInt("qtiteStock");
                int idFournisseur = rsA.getInt("idFournisseur");
                Fournisseur f = null;
                if (!rsA.wasNull()) {
                    f = fournisseurs.get(idFournisseur);
                }

                Article article = new Article(code, prix, qte, nom, f);
                stock.insertion(article);
                compteur++;
            }

            System.out.println(compteur + " article(s) chargé(s) depuis la BD");

        } catch (SQLException e) {
            System.out.println("Erreur chargement stock : " + e.getMessage());
        }
    }
    // C R U D FOURNISSEURS
    public void insererFournisseur(Fournisseur f) {
        try {
            Connection conn = getConnection();
            String query = "INSERT INTO Fournisseur VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, f.getId());
            stmt.setString(2, f.getRaisonSociale());
            stmt.setString(3, f.getAdresse());
            stmt.setString(4, f.getMatriculeFiscale());
            stmt.setInt(5, (int)f.getChiffreAffaire());
            stmt.setString(6, f.getGroupe());

            if (f instanceof FournisseurEtranger fe) {
                stmt.setString(7, "ETRANGER");
                stmt.setString(8, fe.getPays());
                stmt.setString(9, fe.getDevise());
                stmt.setDouble(10, fe.getCoursDeChange());
            } else {
                stmt.setString(7, "LOCAL");
                stmt.setString(8, null);
                stmt.setString(9, null);
                stmt.setDouble(10, 0);
            }
            stmt.executeUpdate();
            System.out.println("Fournisseur " + f.getId() + " inséré");
        } catch (SQLException e) {
            System.out.println("Erreur insertion fournisseur : " + e.getMessage());
        }
    }

    // Read : charger un fournisseur
    public Fournisseur chargerFournisseur (int id) {
        try {
            Connection conn = getConnection();
            String query = "SELECT * FROM Fournisseur WHERE idFournisseur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("type");

                if ("ETRANGER".equals(type)) {
                    return new FournisseurEtranger(
                            rs.getInt("idFournisseur"),
                            rs.getString("raisonSociale"),
                            rs.getString("adresse"),
                            rs.getString("matriculeFiscale"),
                            rs.getInt("chiffreAffaire"),
                            rs.getString("groupe"),
                            rs.getString("pays"),
                            rs.getString("devise"),
                            rs.getDouble("coursDeChange")
                    );
                } else {
                    return new Fournisseur(
                            rs.getInt("idFournisseur"),
                            rs.getString("raisonSociale"),
                            rs.getString("adresse"),
                            rs.getString("matriculeFiscale"),
                            rs.getInt("chiffreAffaire"),
                            rs.getString("groupe")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur chargement fournisseur : " + e.getMessage());
        }
        return null;
    }
    // Delete un fournisseur
    public void supprimerFournisseur(int id) {
        try {
            Connection conn = getConnection();
            String query = "DELETE FROM Fournisseur WHERE idFournisseur = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Fournisseur " + id + " supprimé");
            } else {
                System.out.println("Fournisseur " + id + " introuvable");
            }

        } catch (SQLException e) {
            System.out.println(" Erreur suppression fournisseur : " + e.getMessage());
        }
    }

}



