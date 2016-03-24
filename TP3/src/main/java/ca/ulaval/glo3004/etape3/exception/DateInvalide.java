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
