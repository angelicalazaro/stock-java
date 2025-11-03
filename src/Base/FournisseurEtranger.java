/* Un fournisseur étranger est un fournisseur qui a d’autres données supplémentaires à savoir :
• Pays chaine
• Devise chaine
• Cours de change double
7- Ecrire le code source de la classe fournisseur_etranger qui dérive de la classe fournisseur.
Prévoir, un constructeur, la méthode toString() et la méthode get_ca_en_euro()
La chiffre d’affaires en euro pour un fournisseur étranger est le chiffre d’affaire * cours de
change.

 */

package Base;

public class FournisseurEtranger extends Fournisseur {
    private String pays;
    private String devise;
    private double coursDeChange;

    public FournisseurEtranger (int id, String raisonSociale, String adresse, String matriculeFiscale, int chiffreAffaire, String groupe, String pays, String devise, double coursDeChange) {
        // dans le super, on passe juste les noms des variables dans l'ordre sans les types
        super(id, raisonSociale, adresse, matriculeFiscale, chiffreAffaire, groupe);
        this.pays = pays;
        this.devise = devise;
        this.coursDeChange = coursDeChange;
    }
    // getters
    public String getPays() {return pays;}
    public String getDevise() {return devise;}
    public double getCoursDeChange() {return coursDeChange;}
    // setters
    public void setPays(String pays) {this.pays = pays;}
    public void setDevise (String devise) {this.devise = devise;}
    public void setCoursDeChange (double coursDeChange) {this.coursDeChange = coursDeChange;}

    @Override
    public double get_ca_en_euro() {
        double caFournisseur = super.get_ca_en_euro();
        double caFournisseurEtranger = caFournisseur * coursDeChange;
        return caFournisseurEtranger;
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nPays : " + pays +
               "\nDevise : " + devise +
               "\nCours de change : " + coursDeChange;
    }
}
