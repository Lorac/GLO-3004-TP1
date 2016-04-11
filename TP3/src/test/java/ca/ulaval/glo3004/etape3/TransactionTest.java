package ca.ulaval.glo3004.etape3;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

    private static final NumCompte VALIDNUMCOMPTE = new NumCompte(1);
    private static final NumCompte VALIDNUMCOMPTE2 = new NumCompte(2);
    private static final NumCompte INVALIDUMCOMPTE = null;
    private static final NumBanque VALIDNUMBANQUE = new NumBanque(1);
    private static final NumBanque VALIDNUMBANQUE2 = new NumBanque(2);
    private static final NumBanque INVALIDNUMBANQUE = null;
    private static final int VALIDBALANCE = 10000;
    private static final int VALIDBALANCE2 = 100;
    private static Banque banque;
    private static Banque banque2;
    private Transaction transaction;
    private Map<NumBanque, Banque> banques = new HashMap<>();

    @Before
    public void setup() throws Exception {
        banque = new Banque();
        banque2 = new Banque();
        Date date = new Date(1, 1, 1990);
        banque.ouvrirCompte(VALIDBALANCE, VALIDNUMCOMPTE, date);
        banque2.ouvrirCompte(VALIDBALANCE2, VALIDNUMCOMPTE2, date);

        banques.put(VALIDNUMBANQUE, banque);
        banques.put(VALIDNUMBANQUE2, banque2);

        transaction = new Transaction(banques);
    }

    @Test
    public void givenCorrectArguments_WhenDoingExternalTransaction_ThenValidTransaction() throws Exception {
        int amount = 500;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenSameBankNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenIncorrectSourceBankNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        transaction.versExterieur(INVALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenIncorrectDestinationBankNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        transaction.versExterieur(VALIDNUMBANQUE, INVALIDNUMBANQUE, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenIncorrectSourceAccountNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, INVALIDUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenIncorrectDestinationAccountNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE, VALIDNUMCOMPTE, INVALIDUMCOMPTE, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenClosedSourceAccountNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        Date date = new Date(1, 1, 1991);
        banque.retraitC(VALIDNUMCOMPTE, 9950);
        banque.fermerCompte(VALIDNUMCOMPTE, date);
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenClosedDestinationAccountNumber_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 50;
        Date date = new Date(1, 1, 1991);
        banque2.retraitC(VALIDNUMCOMPTE2, 50);
        banque2.fermerCompte(VALIDNUMCOMPTE2, date);
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenOutgoingMoneyHigherThanSourceAccountCurrentMoney_WhenDoingExternalTransaction_ThenInvalidTransaction()
            throws Exception {
        int amount = 10000;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenSourceBankFeesHigherThanOutgoingMoney_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = -10000;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenDestinationBankFeesHigherThanSumOfCurrentAndIncomingMoney_WhenDoingExternalTransaction_ThenInvalidTransaction()
            throws Exception {
        int amount = 50;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }

    @Test(expected = AssertionError.class)
    public void givenDestinationBankFeesHigherThanIncomingMoney_WhenDoingExternalTransaction_ThenInvalidTransaction() throws Exception {
        int amount = 150;
        transaction.versExterieur(VALIDNUMBANQUE, VALIDNUMBANQUE2, VALIDNUMCOMPTE, VALIDNUMCOMPTE2, amount);
    }
}