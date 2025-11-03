/* Un constructeur sans paramètres qui initialise id et capital à 0 et les attributs de type chaine
à NULL -> OK
b. Un constructeur avec paramètres. -> OK
c. Gérer à travers une exception une erreur dans le cas où id est <=0 ou que le capital est <1000 -> OK
d. Un constructeur par recopie -> OK
e. Les méthodes getters (get_id(), get_raison_sociale(), get_adresse(), get_matricule_fiscal(),
get_chiffre_affaire() et get_groupe() -> OK
f. Les méthodes setters (set_id(), set_raison_sociale(), set_adresse(),set_matricule_fiscal(à,
set_chiffre_affaire() et set_groupe() -> OK
g. La méthode get_ca_en_euro() (valeur de retour double) -> OK
h. La méthode toString() qui affiche les attributs de la classe fournisseur. -> OK

i. Implémenter la méthode fusion pour la classe fournisseur. La fusion de deux fournisseurs A et
B n’est possible que si
 A et B ont la même matricule fiscale
 A et B ont le même groupe
Cette condition est contrôlée à travers une exception.
La fusion de deux fournisseurs donne lieu à un fournisseur ayant comme attributs ceux de
A sauf pour les attributs suivants :
id : id de A *10000+id de B
raison_sociale : le groupe de A
chiffre_affaire : chiffre d’affaire de A + chiffre d’affaire de B
*/

package Base;

public class Fournisseur {
    private int id;
    private String raisonSociale;
    private String adresse;
    private String matriculeFiscale;
    private int chiffreAffaire;
    private String groupe;

    // constructeur sans paramètres
    public Fournisseur() {
        this.id = 0;
        this.raisonSociale = null;
        this.adresse = null;
        this.matriculeFiscale = null;
        this.chiffreAffaire = 0;
        this.groupe = null;
    }
    // constructeur avec paramètres
    public Fournisseur (int id, String raisonSociale, String adresse, String matriculeFiscale, int chiffreAffaire, String groupe) {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.matriculeFiscale = matriculeFiscale;
        this.chiffreAffaire = chiffreAffaire;
        this.groupe = groupe;

        if (id <=0 || chiffreAffaire <1000) {
            throw new InvalidFoException("message explicite");
        }
    }

    // constructeur par recopie
    public Fournisseur (Fournisseur autre) {
        this.id = autre.id;
        this.raisonSociale = autre.raisonSociale;
        this.adresse = autre.adresse;
        this.matriculeFiscale = autre.matriculeFiscale;
        this.chiffreAffaire = autre.chiffreAffaire;
        this.groupe = autre.groupe;
    }

    public class InvalidFoException extends RuntimeException {
        public InvalidFoException(String message) {
            super(message);
        }
    }
    public int getId() { return id;}
    public String getRaisonSociale() {return raisonSociale;}
    public String getAdresse() {return adresse;}
    public String getMatriculeFiscale() {return matriculeFiscale;}
    public double getChiffreAffaire() {return chiffreAffaire;}
    public String getGroupe() {return groupe;}

    public void setId(int id) {
        this.id = id;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public double get_ca_en_euro() {
        return chiffreAffaire;
    }

    public Fournisseur fusion(Fournisseur A, Fournisseur B) {
        if (!A.groupe.equals(B.groupe)) {
            throw new IllegalArgumentException("Le groupe doit être le même");
        }
        if (!A.matriculeFiscale.equals(matriculeFiscale)) {
            throw new IllegalArgumentException("La matricule fiscale doit être la même");
        }
        Fournisseur fusionFournisseur = new Fournisseur();
        fusionFournisseur.setId(A.id*10000 + B.id);
        fusionFournisseur.setRaisonSociale(A.groupe);
        fusionFournisseur.setChiffreAffaire(A.chiffreAffaire + B.chiffreAffaire);
        return fusionFournisseur;
    }

    // méthode toString affichage informations Fournisseur
    @Override
    public String toString() {
        return "Fournisseur " + ": " +
                "\nId : " + id +
                "\nRaison sociale : " + raisonSociale +
                "\nAdresse : " + adresse +
                "\nMatricule fiscale : " + matriculeFiscale +
                "\nChiffre d'affaires : " + chiffreAffaire +
                "\nGroupe : " + groupe;
    }
}
