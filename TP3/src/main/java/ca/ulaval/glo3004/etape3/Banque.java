package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;

import java.util.HashMap;

public class Banque {
    private final int maxNum = 29999;
    private final int minSolde = 50;
    private HashMap<Integer, Compte> comptes;
    private int soldeG;
    private int soldeV;
    private int entrees;
    private int sorties;
    private int gains;
    private int frais;
    private Date dateExercice;

    public Banque() throws DateInvalide {
        comptes = new HashMap<>();
        soldeG = 0;
        soldeV = 0;
        entrees = 0;
        sorties = 0;
        gains = 0;
        frais = 1;
        dateExercice = new Date(1, 3, 1900);
    }

    public void ouvrirCompte(int soldeInitial, int numeroCompte, Date ouverture) {
        if (soldeInitial < minSolde || numeroCompte > maxNum || comptes.containsKey(numeroCompte)) {
            throw new IllegalArgumentException();
        }
        comptes.put(numeroCompte, new Compte(soldeInitial, 0, ouverture, null));
    }

    public void fermerCompte(int numeroCompte, Date fermeture) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() != minSolde || comptes.get(numeroCompte).getFermeture() != null) {
            throw new IllegalArgumentException();
        }
        comptes.get(numeroCompte).setFermeture(fermeture);
    }

    public void supprimerCompte(int numeroCompte, Date date){
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() != minSolde || comptes.get(numeroCompte).getFermeture() == null) {
            throw new IllegalArgumentException();
        }
        //TODO: Add weird condition when not tired
        comptes.remove(numeroCompte);
    }

    public void retraitC(int numeroCompte, int montant){
        if (!comptes.containsKey(numeroCompte) || montant > 0 || comptes.get(numeroCompte).getSolde() - montant < minSolde) {
            throw new IllegalArgumentException();
        }
        soldeG -= montant;
        sorties += montant;
        comptes.get(numeroCompte).withdrawal(montant);
    }

    public HashMap<Integer, Compte> getComptes() {
        return comptes;
    }

    public int getFrais() {
        return frais;
    }
}
