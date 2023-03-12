package Kodutööd.Kodutöö5.TVStations;

public class TV implements BroadcastListener {
    String owner;

    public TV (String owner) {
        this.owner = owner;
    }

    @Override
    public void listen(String s) {
        System.out.printf("%s heard the following news: %s%n", owner, s);
    }
}
