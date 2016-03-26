package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;

import java.util.HashMap;

public class Banque {

    private HashMap<NumCompte, Compte> comptes;
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
        frais = 200;
        dateExercice = new Date(1, 3, 1900);
        assert estValide();
    }

    public void ouvrirCompte(int soldeInitial, NumCompte numeroCompte, Date ouverture) {
        if (soldeInitial < Constants.minSolde || comptes.size() >= Constants.maxNum || comptes.containsKey(numeroCompte)) {
            throw new IllegalArgumentException();
        }
        comptes.put(numeroCompte, new Compte(soldeInitial, 0, ouverture, null));
        assert estValide();
    }

    public void fermerCompte(NumCompte numeroCompte, Date fermeture) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() != Constants.minSolde ||
                comptes.get(numeroCompte).getFermeture() != null) {
            throw new IllegalArgumentException();
        }
        comptes.get(numeroCompte).setFermeture(fermeture);
        assert estValide();
    }

    public void supprimerCompte(NumCompte numeroCompte, Date date) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() != Constants.minSolde ||
                comptes.get(numeroCompte).getFermeture() == null) {
            throw new IllegalArgumentException();
        }
        Date fermeture = comptes.get(numeroCompte).getFermeture();
        if (date.getAnnee() < fermeture.getAnnee() + 2 ||
                (date.getAnnee() == fermeture.getAnnee() + 2 && date.getMois() < fermeture.getMois()) ||
                (date.getAnnee() == fermeture.getAnnee() + 2 && date.getMois() == fermeture.getMois() &&
                        date.getJour() < fermeture.getJour())) {
            throw new IllegalArgumentException("La date de fermeture doit être au moins deux ans avant la date donnée");
        }
        comptes.remove(numeroCompte);
        assert estValide();
    }

    public void retraitC(NumCompte numeroCompte, int montant) {
        if (!comptes.containsKey(numeroCompte) || montant <= 0 ||
                comptes.get(numeroCompte).getSolde() - montant < Constants.minSolde) {
            throw new IllegalArgumentException();
        }
        soldeG -= montant;
        sorties += montant;
        comptes.get(numeroCompte).retrait(montant);
        assert estValide();
    }

    public void depotC(NumCompte numeroCompte, int montant) {
        if (!comptes.containsKey(numeroCompte) || montant <= 0) {
            throw new IllegalArgumentException();
        }
        soldeG += montant;
        entrees += montant;
        comptes.get(numeroCompte).depot(montant);
        assert estValide();
    }

    public void depotLC(NumCompte numeroCompte, int montant) {
        if (!comptes.containsKey(numeroCompte) || montant <= 0 ||
                comptes.get(numeroCompte).getLiquide() + montant > Constants.maxLiquide) {
            throw new IllegalArgumentException();
        }
        soldeG += montant;
        entrees += montant;
        comptes.get(numeroCompte).depotLiquide(montant);
        assert estValide();
    }

    public void virementC(NumCompte numeroCompte1, NumCompte numeroCompte2, int montant) {
        if (!comptes.containsKey(numeroCompte1) || !comptes.containsKey(numeroCompte2) || montant <= 0 ||
                comptes.get(numeroCompte1).getSolde() - montant < Constants.minSolde) {
            throw new IllegalArgumentException();
        }
        comptes.get(numeroCompte1).retrait(montant);
        comptes.get(numeroCompte2).depot(montant);
        assert estValide();
    }

    public void chNIP(NumCompte numeroCompte, int nNIP) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getNip() == nNIP) {
            throw new IllegalArgumentException();
        }
        comptes.get(numeroCompte).modifierNIP(nNIP);
        assert estValide();
    }

    public void bilanV(Date date) {
        if (date.getJour() != dateExercice.getJour() || date.getMois() != dateExercice.getMois()) {
            throw new IllegalArgumentException();
        }
        soldeV = soldeG;
        entrees = 0;
        sorties = 0;
        assert estValide();
    }

    public void versExterieur(NumCompte numeroCompte, int montant) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() - (montant + frais) < Constants.minSolde ||
                comptes.get(numeroCompte).getFermeture() != null || (montant + frais) <= 0) {
            throw new IllegalArgumentException();
        }
        soldeG = soldeG - montant + frais;
        entrees += frais;
        sorties += montant;
        comptes.get(numeroCompte).retrait(montant + frais);
        assert estValide();
    }

    public void versInterieur(NumCompte numeroCompte, int montant) {
        if (!comptes.containsKey(numeroCompte) || comptes.get(numeroCompte).getSolde() + montant < frais ||
                comptes.get(numeroCompte).getFermeture() != null || (montant - frais) <= 0) {
            throw new IllegalArgumentException();
        }
        soldeG += montant;
        entrees += montant;
        comptes.get(numeroCompte).depot(montant - frais);
        assert estValide();
    }

    public HashMap<NumCompte, Compte> getComptes() {
        return comptes;
    }

    public int getFrais() {
        return frais;
    }

    private boolean estValide() {
        return (comptes.size() <= Constants.maxNum && (soldeG - entrees + sorties) == soldeV);
    }
}
