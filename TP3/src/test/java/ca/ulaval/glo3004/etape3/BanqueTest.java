package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BanqueTest {
    private static final NumCompte VALIDNUMCOMPTE = new NumCompte(1);
    private static final NumCompte VALIDNUMCOMPTE2 = new NumCompte(2);
    private static final int VALIDBALANCE = 10000;
    private static final int INVALIDBALANCE = -40;
    private Banque banque;

    @Before
    public void setup() throws Exception {
        banque = new Banque();
    }

    @Test
    public void givenNoArguments_WhenCreatingBanque_ThenValidBanque() throws DateInvalide {
        //then
        assertEquals(new HashMap<NumCompte, Compte>(), banque.getComptes());
        assertEquals(200, banque.getFrais());
    }

    @Test
    public void givenCorrectArguments_WhenOpeningCompte_ThenValidCompte() throws Exception {
        Date date = new Date(1, 1, 1990);
        //when
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        //then
        assertTrue(banque.getComptes().containsKey(VALIDNUMCOMPTE));
        assertEquals(VALIDBALANCE, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
        assertEquals(date, banque.getComptes().get(VALIDNUMCOMPTE).getOuverture());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenIncorrectBalance_WhenOpeningCompte_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        //when
        banque.ouvrirCompte(INVALIDBALANCE, VALIDNUMCOMPTE, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueAtCapacity_WhenOpeningCompte_ThenThrowsError() throws Exception {
        for (int i = 0; i < Constants.maxNum; i++) {
            banque.getComptes().put(new NumCompte(i), null);
        }
        Date date = new Date(1, 1, 1990);
        //when
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenOpeningCompteWithSameNumber_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        //when
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
    }

    @Test
    public void givenABanqueWithACompte_WhenClosingCompteWithSameNumber_ThenCompteIsClosed() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        //when
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //then
        assertTrue(banque.getComptes().get(VALIDNUMCOMPTE).estFerme());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompteWithHighBalance_WhenClosingCompte_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        //when
        banque.fermerCompte(VALIDNUMCOMPTE, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenClosingCompte_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        //when
        banque.fermerCompte(VALIDNUMCOMPTE, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithCompte_WhenClosingCompteWithNullClosingDate_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        //when
        banque.fermerCompte(VALIDNUMCOMPTE, null);
    }

    @Test
    public void givenABanqueWithAClosedCompte_WhenDeletingCompte_ThenCompteIsDeleted() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 1993);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
        //then
        assertFalse(banque.getComptes().containsKey(VALIDNUMCOMPTE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenDeletingCompte_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        //when
        banque.supprimerCompte(VALIDNUMCOMPTE, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompteWithHighBalance_WhenDeletingCompte_ThenThrowsError() throws Exception {
        //given
        Banque banque = new Banque();
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 2000);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAnOpenCompte_WhenDeletingCompte_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 2000);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAClosedCompte_WhenDeletingCompteTooSoonByYear_ThenThrowsError() throws Exception {
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 1991);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAClosedCompte_WhenDeletingCompteTooSoonByMonth_ThenThrowsError() throws Exception {
        Date date = new Date(1, 2, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 1992);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAClosedCompte_WhenDeletingCompteTooSoonByDay_ThenThrowsError() throws Exception {
        Date date = new Date(2, 1, 1990);
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, date);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        //when
        Date date2 = new Date(1, 1, 1992);
        banque.supprimerCompte(VALIDNUMCOMPTE, date2);
    }

    @Test
    public void givenABanqueWithACompte_WhenWithdrawingFromCompte_ThenCompteIsWithdrawnFrom() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.retraitC(VALIDNUMCOMPTE, amount);
        //then
        assertEquals(VALIDBALANCE - amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenWithdrawingBadAmountFromCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 0;
        banque.retraitC(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenWithdrawingFromCompte_ThenThrowsError() throws Exception {
        //when
        int amount = 50;
        banque.retraitC(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithInsufficientBalanceCompte_WhenWithdrawingFromCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = VALIDBALANCE;
        banque.retraitC(VALIDNUMCOMPTE, amount);
    }

    @Test
    public void givenABanqueWithACompte_WhenDepositingInCompte_ThenDepositIsCompleted() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.depotC(VALIDNUMCOMPTE, amount);
        //then
        assertEquals(VALIDBALANCE + amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenDepositingBadAmountInCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 0;
        banque.depotC(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenDepositingInCompte_ThenThrowsError() throws Exception {
        //when
        int amount = 50;
        banque.depotC(VALIDNUMCOMPTE, amount);
    }

    @Test
    public void givenABanqueWithACompte_WhenDepositingLiquidInCompte_ThenDepositIsCompleted() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.depotLC(VALIDNUMCOMPTE, amount);
        //then
        assertEquals(VALIDBALANCE + amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
        assertEquals(amount, banque.getComptes().get(VALIDNUMCOMPTE).getLiquide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenDepositingBadLiquidAmountInCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 0;
        banque.depotLC(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenDepositingTooMuchLiquidAmountInCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = Constants.maxLiquide + 1;
        banque.depotLC(VALIDNUMCOMPTE, amount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenDepositingLiquidInCompte_ThenThrowsError() throws Exception {
        //when
        int amount = 50;
        banque.depotLC(VALIDNUMCOMPTE, amount);
    }

    @Test
    public void givenABanqueWithTwoComptes_WhenTransferingValidAmount_ThenTransferIsCompleted() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE2, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.virementC(VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
        //then
        assertEquals(VALIDBALANCE - amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
        assertEquals(VALIDBALANCE + amount, banque.getComptes().get(VALIDNUMCOMPTE2).getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithOneCompte_WhenTransferingAmountToInvalidCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.virementC(VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithOneCompte_WhenTransferingAmountFromInvalidCompte_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE2, new Date(2, 1, 1990));
        //when
        int amount = 50;
        banque.virementC(VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithTwoComptes_WhenTransferingInvalidAmount_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE2, new Date(2, 1, 1990));
        //when
        int amount = 0;
        banque.virementC(VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithTwoComptes_WhenTransferingTooMuch_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE2, new Date(2, 1, 1990));
        //when
        int amount = VALIDBALANCE;
        banque.virementC(VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test
    public void givenABanqueWithACompte_WhenChangingNIP_ThenNIPIsChanged() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int NIP = 50;
        banque.chNIP(VALIDNUMCOMPTE, NIP);
        //then
        assertEquals(NIP, banque.getComptes().get(VALIDNUMCOMPTE).getNip());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenChangingNIP_ThenThrowsError() throws Exception {
        //when
        int NIP = 50;
        banque.chNIP(VALIDNUMCOMPTE, NIP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenChangingNIPToSameNIP_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int NIP = 50;
        banque.chNIP(VALIDNUMCOMPTE, NIP);
        banque.chNIP(VALIDNUMCOMPTE, NIP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanque_WhenDoingTheAssessementOnWrongDay_ThenThrowsError() throws Exception {
        //when
        Date date = new Date(2, 3, 2000);
        banque.bilanV(date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanque_WhenDoingTheAssessementInWrongMonth_ThenThrowsError() throws Exception {
        //given
        Banque banque = new Banque();
        //when
        Date date = new Date(1, 4, 2000);
        banque.bilanV(date);
    }

    @Test
    public void givenABanque_WhenDoingTheAssessement_ThenDoesNotThrowAnError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        Date date = new Date(1, 3, 2000);
        banque.bilanV(date);
    }

    @Test
    public void givenABanqueWithACompte_WhenTransferringToOutside_ThenComptePaysFeesAndAmount() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 250;
        banque.versExterieur(VALIDNUMCOMPTE, amount);
        //then
        assertEquals(VALIDBALANCE - banque.getFrais() - amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenTransferringToOutside_ThenThrowsError() throws Exception {
        //when
        int amount = 250;
        banque.versExterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenTransferringTooMuchToOutside_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = VALIDBALANCE - Constants.minSolde;
        banque.versExterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAClosedCompte_WhenTransferringToOutside_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        banque.fermerCompte(VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 250;
        banque.versExterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenTransferringTooLittleOutside_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = -201;
        banque.versExterieur(VALIDNUMCOMPTE, amount);
    }

    @Test
    public void givenABanqueWithACompte_WhenTransferringToInside_ThenCompteGetsAmountMinusFees() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 250;
        banque.versInterieur(VALIDNUMCOMPTE, amount);
        //then
        assertEquals(VALIDBALANCE - banque.getFrais() + amount, banque.getComptes().get(VALIDNUMCOMPTE).getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithNoCompte_WhenTransferringToInside_ThenThrowsError() throws Exception {
        //when
        int amount = 250;
        banque.versInterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenFeesAreGreaterThanResultingBalanceOnInsideTransfer_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(Constants.minSolde, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 10;
        banque.versInterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithAClosedCompte_WhenTransferringToInside_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        banque.fermerCompte(VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 250;
        banque.versInterieur(VALIDNUMCOMPTE, amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenABanqueWithACompte_WhenTransferringTooLittleInside_ThenThrowsError() throws Exception {
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, new Date(2, 1, 1990));
        //when
        int amount = 200;
        banque.versInterieur(VALIDNUMCOMPTE, amount);
    }
}