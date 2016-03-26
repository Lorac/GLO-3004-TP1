package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void givenValidDate_whenConstructingDate_thenConstructedDateIsValid() throws DateInvalide {
        //given
        int jour = 10;
        int mois = 9;
        int annee = 1991;
        //when
        Date date = new Date(jour, mois, annee);
        //then
        assertEquals(date.getJour(), jour);
        assertEquals(date.getMois(), mois);
        assertEquals(date.getAnnee(), annee);
    }

    @Test(expected = DateInvalide.class)
    public void givenInvalidDate_whenConstructingDate_thenThrowsDateInvalide() throws DateInvalide {
        //given
        int jour = 32;
        int mois = 9;
        int annee = 1991;
        //when
        Date date = new Date(jour, mois, annee);
        //then

    }

    @Test(expected = DateInvalide.class)
    public void givenInvalidLeapDate_whenConstructingDate_thenThrowsDateInvalide() throws DateInvalide {
        //given
        int jour = 30;
        int mois = 2;
        int annee = 1992;
        //when
        Date date = new Date(jour, mois, annee);
        //then

    }

}