package ca.ulaval.glo3004.etape3;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    private Map<NumBanque, Banque> banques = new HashMap<>();


    public Transaction(Map<NumBanque, Banque> banques) {
        this.banques = banques;
    }

    public void versExterieur(NumBanque numBanqueSource, NumBanque numBanqueDest, NumCompte numCompteSource, NumCompte numCompteDest, int montant) {
        assert (numBanqueSource != numBanqueDest) : "Les numéros de banque doivent être différent";
        assert (banques.containsKey(numBanqueSource)) : "La banque #1 doit exister";
        assert (banques.containsKey(numBanqueDest)) : "La banque #2 doit exister";

        Banque banqueSource = banques.get(numBanqueSource);
        Banque banqueDest = banques.get(numBanqueDest);

        HashMap<NumCompte, Compte> comptes = banqueSource.getComptes();
        HashMap<NumCompte, Compte> comptes2 = banqueDest.getComptes();

        assert (comptes.containsKey(numCompteSource)) : "Le compte source n'existe pas";
        assert (comptes2.containsKey(numCompteDest)) : "Le compte destination n'existe pas";

        Compte compteSource = comptes.get(numCompteSource);
        Compte compteDest = comptes2.get(numCompteDest);

        assert (compteSource.estOuvert()) : "Le compte source est fermé";
        assert (compteDest.estOuvert()) : "Le compte destination est fermé";

        assert (compteSource.getSolde() - (montant + banqueSource.getFrais()) >= Constants.minSolde) : "Le solde du compte source n'est " +
                "pas assez élevé";

        assert (0 < banqueSource.getFrais() + montant);

        assert (banqueDest.getFrais() <= (compteDest.getSolde() + montant));
        assert (banqueDest.getFrais() < montant);


        banqueSource.versExterieur(numCompteSource, montant);
        banqueDest.versInterieur(numCompteDest, montant);
    }

}
