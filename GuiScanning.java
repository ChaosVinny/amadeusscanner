package it.amadeus.client.scanner;

import it.amadeus.client.scanner.api.Pinger;
import it.amadeus.client.scanner.api.Request;

import java.util.ArrayList;

public class GuiScanning {

    public static void main(String[] args) {
        Pinger pinger = new Pinger();
        Request request = new Request();
        ArrayList<Pinger> pingers = new ArrayList<>();
        for (int port = 25565; port < 25573; port++){
            pinger.ping("185.25.204.*", port);
            pingers.add(pinger);
        }
        request.update(pingers);
        System.out.println(Request.working_ips.get(0));
        try {
            Thread.sleep(300L);
            Request.working_ips.clear();
            pingers.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
