package ca.ulaval.glo3004.etape3;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;

import static java.util.Calendar.DAY_OF_YEAR;

public class Date {

    private final int jour;
    private final int mois;
    private final int annee;

    public Date(int jour, int mois, int annee) throws DateInvalide {
        if (isLeapYear(annee) && mois == 2) {
            assert (jour < 30);
        }
        try {
            LocalDate.of(annee, mois, jour);
        } catch (DateTimeException e) {
            throw new DateInvalide(e);
        }
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(DAY_OF_YEAR) > 365;
    }
}
