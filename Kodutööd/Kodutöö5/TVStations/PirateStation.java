package Kodutööd.Kodutöö5.TVStations;

public class PirateStation extends Broadcaster implements BroadcastListener {
    @Override
    public void listen(String s) {
        broadcast(s);
    }

}
