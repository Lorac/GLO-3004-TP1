// Équipe #1
// Antoine Giasson (111 044 655), GLO
// Maxime Charron (111 038 084), GLO
// Maxime Roussin-Bélanger (111 018 750), IFT
// Julien Duchesne (111 042 624), GLO
package ca.ulaval.glo3004.etape3;

import static ca.ulaval.glo3004.etape3.Constants.minSolde;

public class Compte {

    private int solde;
    private int nip;
    private Date ouverture;
    private Date fermeture;
    private int liquide;

    public Compte(int solde, int nip, Date ouverture, Date fermeture) {
        if (solde < minSolde || fermeture != null) {
            throw new IllegalArgumentException();
        }
        this.solde = solde;
        this.nip = nip;
        this.ouverture = ouverture;
        this.fermeture = fermeture;
        this.liquide = 0;
        assert estValide();
    }

    public int retrait(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.solde -= amount;
        assert estValide();
        return solde;
    }

    public void depot(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.solde += amount;
        assert estValide();
    }

    public void depotLiquide(int amount) {
        if (amount <= 0 || this.liquide + amount > Constants.maxLiquide) {
            throw new IllegalArgumentException();
        }
        this.liquide += amount;
        this.solde += amount;
        assert estValide();
    }

    public void miseAZeroLiquide() {
        this.liquide = 0;
        assert estValide();
    }

    public void modifierNIP(int nNIP) {
        if (this.nip == nNIP) {
            throw new IllegalArgumentException();
        }
        this.nip = nNIP;
        assert estValide();
    }

    public int getSolde() {
        return solde;
    }

    public int getNip() {
        return nip;
    }

    public Date getOuverture() {
        return ouverture;
    }

    public Date getFermeture() {
        return fermeture;
    }

    public void setFermeture(Date fermeture) {
        this.fermeture = fermeture;
    }

    public boolean estOuvert() {
        return fermeture == null;
    }

    public boolean estFerme() {
        return fermeture != null;
    }

    public int getLiquide() {
        return liquide;
    }

    public void setLiquide(int liquide) {
        this.liquide = liquide;
        assert estValide();
    }

    private boolean estValide() {
        return (this.liquide <= Constants.maxLiquide && (this.solde >= minSolde || this.fermeture != null));
    }
}
