// Équipe #1
// Antoine Giasson (111 044 655), GLO
// Maxime Charron (111 038 084), GLO
// Maxime Roussin-Bélanger (111 018 750), IFT
// Julien Duchesne (111 042 624), GLO
package ca.ulaval.glo3004.etape3.exception;

import java.time.DateTimeException;

public class DateInvalide extends Exception {


    public DateInvalide(DateTimeException e) {
        super(e);
    }

    public DateInvalide(String msg) {
        super(msg);
    }
}
