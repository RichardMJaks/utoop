package Kodutööd.KT2H2;
public class FormaadiErind extends Exception {
    public FormaadiErind() {
    }

    public FormaadiErind(String message) {
        super(message);
    }

    public FormaadiErind(String message, Throwable cause) {
        super(message, cause);
    }

    public FormaadiErind(Throwable cause) {
        super(cause);
    }

    public FormaadiErind(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
