package Praktikumid.Praktikum3;

public abstract class Telefon {
    private String nr;
    private String helin;

    public Telefon(String nr, String helin) {
        this.nr = nr;
        this.helin = helin;
    }
    public String getNumber() {
        return this.nr;
    };
    public String getHelin() {
        return this.helin;
    }

    public String viimasedNumbrid(int n) {
        int offset = this.nr.length() - 1 - n;
        return new String(this.nr.toCharArray(), offset, this.nr.length() - offset);
    }
    abstract String tähtisInfo();
}
