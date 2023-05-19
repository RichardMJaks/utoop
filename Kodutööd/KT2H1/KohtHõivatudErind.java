package Kodutööd.KT2H1;

public class KohtHõivatudErind extends Exception {
    public KohtHõivatudErind() {
    }

    public KohtHõivatudErind(String message) {
        super(message);
    }

    public KohtHõivatudErind(String message, Throwable cause) {
        super(message, cause);
    }

    public KohtHõivatudErind(Throwable cause) {
        super(cause);
    }

    public KohtHõivatudErind(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
