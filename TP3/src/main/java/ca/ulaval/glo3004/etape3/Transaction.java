// Équipe #1
// Antoine Giasson (111 044 655), GLO
// Maxime Charron (111 038 084), GLO
// Maxime Roussin-Bélanger (111 018 750), IFT
// Julien Duchesne (111 042 624), GLO
package ca.ulaval.glo3004.etape3;

import java.util.Map;

public class Transaction {

    private Map<NumBanque, Banque> banques;

    public Transaction(Map<NumBanque, Banque> banques) {
        this.banques = banques;
    }

    public void versExterieur(NumBanque numBanqueSource, NumBanque numBanqueDest, NumCompte numCompteSource, NumCompte numCompteDest, int montant) {
        if (numBanqueSource == numBanqueDest) {
            throw new AssertionError("Les numéros de banque doivent être différent");
        }
        if (!banques.containsKey(numBanqueSource)) {
            throw new AssertionError("La banque #1 doit exister");
        }
        if (!banques.containsKey(numBanqueDest)) {
            throw new AssertionError("La banque #2 doit exister");
        }

        Banque banqueSource = banques.get(numBanqueSource);
        Banque banqueDest = banques.get(numBanqueDest);

        Map<NumCompte, Compte> comptesSource = banqueSource.getComptes();
        Map<NumCompte, Compte> comptesDest = banqueDest.getComptes();

        if (!comptesSource.containsKey(numCompteSource)) {
            throw new AssertionError("Le compte source n'existe pas");
        }
        if (!comptesDest.containsKey(numCompteDest)) {
            throw new AssertionError("Le compte destination n'existe pas");
        }

        Compte compteSource = comptesSource.get(numCompteSource);
        Compte compteDest = comptesDest.get(numCompteDest);

        if (!compteSource.estOuvert()) {
            throw new AssertionError("Le compte source est fermé");
        }
        if (!compteDest.estOuvert()) {
            throw new AssertionError("Le compte destination est fermé");
        }

        if (compteSource.getSolde() - (montant + banqueSource.getFrais()) < Constants.minSolde) {
            throw new AssertionError("Le solde du compte source n'est " +
                    "pas assez élevé");
        }

        if (0 >= banqueSource.getFrais() + montant) {
            throw new AssertionError();
        }

        if (banqueDest.getFrais() > (compteDest.getSolde() + montant)) {
            throw new AssertionError();
        }
        if (banqueDest.getFrais() >= montant) {
            throw new AssertionError();
        }


        banqueSource.versExterieur(numCompteSource, montant);
        banqueDest.versInterieur(numCompteDest, montant);
    }

}
