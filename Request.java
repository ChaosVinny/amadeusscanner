package it.amadeus.client.scanner.api;

import java.util.ArrayList;
import java.util.List;

public class Request {

    public static final List<String> working_ips = new ArrayList<>();
    private int checked;
    private int working;

    public void update(ArrayList<Pinger> pingers) {
        for (int i = 0; i < pingers.size(); i++) {
            if (!pingers.get(i).isStillPinging()) {
                this.checked++;
                if (pingers.get(i).isWorking()) {
                    this.working++;
                    if (!alreadyScanned(pingers.get(i).server.serverIP)) {
                        working_ips.add(pingers.get(i).server.serverIP);
                    }
                }
                pingers.remove(i);
            }
        }
    }

    private boolean alreadyScanned(String ip) {
        for (String working_ip : working_ips) {
            if (working_ip.equals(ip)) {
                return true;
            }
        }
        return false;
    }
}
