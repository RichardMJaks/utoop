package Kodutööd.KT2H1;

public class VabadKohadPuuduvadErind extends Exception {
    public VabadKohadPuuduvadErind() {
    }

    public VabadKohadPuuduvadErind(String message) {
        super(message);
    }

    public VabadKohadPuuduvadErind(String message, Throwable cause) {
        super(message, cause);
    }

    public VabadKohadPuuduvadErind(Throwable cause) {
        super(cause);
    }

    public VabadKohadPuuduvadErind(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
