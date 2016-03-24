package ca.ulaval.glo3004.etape3.exception;

import java.time.DateTimeException;

public class DateInvalide extends Exception {


    public DateInvalide(String msg, DateTimeException e) {
        super(msg, e);
    }

    public DateInvalide(DateTimeException e) {
        super(e);
    }

    public DateInvalide(String msg) {
        super(msg);
    }
}
