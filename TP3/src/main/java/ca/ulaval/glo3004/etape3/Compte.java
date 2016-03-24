package ca.ulaval.glo3004.etape3;

public class Compte {

    private int solde;
    private int nip;
    private Date ouverture;
    private Date fermeture;
    private int liquide;
    private final int maxLiquide = 1000;
    private final int minSolde = 50;


    public Compte(int solde, int nip, Date ouverture, Date fermeture){
        if (solde < minSolde){
            throw new IllegalArgumentException();
        }
        this.solde = solde;
        this.nip = nip;
        this.ouverture = ouverture;
        this.fermeture = fermeture;
        this.liquide = 0;
    }

    public int withdrawal(int amount){
        if ( amount <= 0){
            throw new IllegalArgumentException();
        }
        this.solde -= amount;
        return solde;
    }

    public void deposit(int amount){
        if ( amount <= 0){
            throw new IllegalArgumentException();
        }
        this.solde += amount;
    }

    public void liquidDeposit(int amount){
        if (amount <= 0 || this.liquide + amount > this.maxLiquide){
            throw new IllegalArgumentException();
        }
        this.liquide += amount;
        this.solde += amount;
    }

    public void resetLiquid(){
        this.liquide = 0;
    }

    public void modifyPin(int newPin){
        if (this.nip == newPin){
            throw new IllegalArgumentException();
        }
        this.nip = newPin;
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

    public int getLiquide() {
        return liquide;
    }

    public void setLiquide(int liquide) {
        this.liquide = liquide;
    }
}
