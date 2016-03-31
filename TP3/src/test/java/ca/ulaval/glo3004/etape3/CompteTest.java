package ca.ulaval.glo3004.etape3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertTrue(compte.estOuvert());
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

        //when
        Compte compte = new Compte(solde, nip, ouverture, fermeture);
    }

    @Test
    public void givenValidCompte_whenWithdrawing_thenSoldeIsUpdated() throws Exception {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();

        //when
        compte.retrait(100);

        assertEquals(soldeBefore - 100, compte.getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenWithdrawingWithNegative_thenThrowsError() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.retrait(-100);
    }

    @Test
    public void givenValidCompte_whenDeposit_thenSoldeIsUpdated() throws Exception {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();

        //when
        compte.depot(100);

        assertEquals(soldeBefore + 100, compte.getSolde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenDepositWithNegative_thenThrowsError() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.depot(-100);
    }

    @Test
    public void givenValidCompte_whenLiquidDeposit_thenSoldeAndLiquideIsUpdated() throws Exception {
        //given
        Compte compte = createValidCompte();
        int soldeBefore = compte.getSolde();
        int liquidBefore = compte.getLiquide();

        //when
        compte.depotLiquide(100);
        //then
        assertEquals(soldeBefore + 100, compte.getSolde());
        assertEquals(liquidBefore + 100, compte.getLiquide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenLiquidDepositWithNegative_thenThrowsError() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.depotLiquide(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenLiquidDepositWithMaximumLiquid_thenThrowsError() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.depotLiquide(10000);
    }

    @Test
    public void givenValidCompte_whenReset_thenLiquidIsReset() throws Exception {
        //given
        Compte compte = createValidCompte();
        compte.setLiquide(100);

        //when
        compte.miseAZeroLiquide();
        //then
        assertEquals(0, compte.getLiquide());
    }

    @Test
    public void givenValidCompte_whenModifyPin_thenPinIsChanged() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.modifierNIP(2222);
        //then
        assertEquals(2222, compte.getNip());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidCompte_whenModifyPinWithSamePin_thenThrowException() throws Exception {
        //given
        Compte compte = createValidCompte();

        //when
        compte.modifierNIP(1234);
    }


    private Compte createValidCompte() throws Exception {
        int solde = 1000;
        int nip = 1234;
        Date ouverture = null;
        Date fermeture = null;
        ouverture = new Date(2, 2, 2016);

        return new Compte(solde, nip, ouverture, fermeture);
    }

}