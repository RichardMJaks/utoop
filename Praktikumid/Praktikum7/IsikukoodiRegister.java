package Praktikumid.Praktikum7;

import java.util.ArrayList;
import java.util.List;

public class IsikukoodiRegister {
    private List<String> isikukoodid = new ArrayList<>();

    public synchronized void registreeri(String isikukood) {
        if (!isikukoodid.contains(isikukood)) isikukoodid.add(isikukood);
    }

    public synchronized int järjekorranumber (String isikukood) {
        return isikukoodid.indexOf(isikukood);
    }
}
