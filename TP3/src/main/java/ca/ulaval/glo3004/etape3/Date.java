package ca.ulaval.glo3004.etape3;

import ca.ulaval.glo3004.etape3.exception.DateInvalide;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;

import static java.util.Calendar.DAY_OF_YEAR;

public class Date {

    private final int jour;
    private final int mois;
    private final int annee;

    public Date(int jour, int mois, int annee) throws DateInvalide {
        if (isLeapYear(annee) && mois == 2 && jour > 29) {
            throw new DateInvalide("Febuary month can't have 29 days");
        }
        try {
            LocalDate.of(annee, mois, jour);
            this.jour = jour;
            this.mois = mois;
            this.annee = annee;
        } catch (DateTimeException e) {
            throw new DateInvalide(e);
        }
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(DAY_OF_YEAR) > 365;
    }

    public int getJour() {
        return jour;
    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }
}
