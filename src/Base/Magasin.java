package Base;

public class Magasin {
    private String nomMagasin;
    private String proprietaire;
    private String description;
    private Stock stock;

    public Magasin (String nomMagasin, String proprietaire, String description) {
        this.nomMagasin = nomMagasin;
        this.proprietaire = proprietaire;
        this.description = description;
        this.stock = new Stock();
    }

    public String getNomMagasin() {
        return nomMagasin;
    }
    public String getProprietaire() {
        return proprietaire;
    }
    public String getDescription() {
        return description;
    }

    public Stock getStock() {
        return stock;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }
    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Magasin : " + nomMagasin +
                "\nPropietaire : " + proprietaire +
                "\nDescription : " + description +
                "\n" + stock;
    }
}