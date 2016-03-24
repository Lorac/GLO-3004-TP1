package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompteTest {

    @Test
    public void givenValidCompte_whenCreatingCompte_thenValidCompte() throws Exception {
        //given
        int solde = 100;
        int nip = 1234;
        Date ouverture = null;
        ouverture = new Date(2, 2, 2016);

        //when
        Compte compte = new Compte(solde, nip, ouverture, null);

        //then
        assertEquals(solde, compte.getSolde());
        assertEquals(0, compte.getLiquide());
        assertEquals(nip, compte.getNip());
        assertEquals(ouverture, compte.getOuverture());
        assertEquals(null, compte.getFermeture());
    }

    @Test
    public void givenValidCompteWithFermeture_whenCreatingCompte_thenValidCompte() throws Exception {
        //given
        int solde = 100;
        int nip = 1234;
        Date ouverture = null;
        Date fermeture = null;
        ouverture = new Date(2, 2, 2016);
        fermeture = new Date(3, 3, 2016);

        //when
        Compte compte = new Compte(solde, nip, ouverture, fermeture);

        //then
        assertEquals(solde, compte.getSolde());
        assertEquals(0, compte.getLiquide());
        assertEquals(nip, compte.getNip());
        assertEquals(ouverture, compte.getOuverture());
        assertEquals(fermeture, compte.getFermeture());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenLowSolde_whenCreatingCompte_thenShouldFail() throws Exception {
        //given
        int solde = 10;
        int nip = 1234;
        Date ouverture = null;
        Date fermeture = null;
        ouverture = new Date(2, 2, 2016);
        fermeture = new Date(3, 3, 2016);

        //when
        Compte compte = new Compte(solde, nip, ouverture, fermeture);
    }

    @Test
    public void givenValidCompte_whenWithdrawing_thenSoldeIsUpdated() {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();

        //when
        compte.withdrawal(100);

        assertEquals(soldeBefore - 100, compte.getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenWithdrawingWithNegative_thenThrowsError() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.withdrawal(-100);
    }

    @Test
    public void givenValidCompte_whenDeposit_thenSoldeIsUpdated() {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();

        //when
        compte.deposit(100);

        assertEquals(soldeBefore + 100, compte.getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenDepositWithNegative_thenThrowsError() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.deposit(-100);
    }

    @Test
    public void givenValidCompte_whenLiquidDeposit_thenSoldeAndLiquideIsUpdated() {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();
        int liquidBefore = compte.getLiquide();

        //when
        compte.liquidDeposit(100);
        //then
        assertEquals(soldeBefore + 100, compte.getSolde());
        assertEquals(liquidBefore + 100, compte.getLiquide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenLiquidDepositWithNegative_thenThrowsError() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.liquidDeposit(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenLiquidDepositWithMaximumLiquid_thenThrowsError() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.liquidDeposit(10000);
    }

    @Test
    public void givenValidCompte_whenReset_thenLiquidIsReset() {
        //given
        Compte compte = createValidCompte();
        compte.setLiquide(100);

        //when
        compte.resetLiquid();
        //then
        assertEquals(0, compte.getLiquide());
    }

    @Test
    public void givenValidCompte_whenModifyPin_thenPinIsChanged() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.modifyPin(2222);
        //then
        assertEquals(2222, compte.getNip());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenModifyPinWithSamePin_thenThrowException() {
        //given
        Compte compte = createValidCompte();

        //when
        compte.modifyPin(1234);
    }


    private Compte createValidCompte() {
        int solde = 1000;
        int nip = 1234;
        Date ouverture = null;
        Date fermeture = null;
        try {
            ouverture = new Date(2, 2, 2016);
        } catch (Exception e) {

        }
        return new Compte(solde, nip, ouverture, fermeture);
    }

}