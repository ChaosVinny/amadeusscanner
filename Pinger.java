package it.amadeus.client.scanner.api;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

public class Pinger {

    public static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger threadNumber = new AtomicInteger(0);
    public ServerData server;
    private boolean done = false;
    private boolean failed = false;

    public void ping(final String ip, final int port) {
        this.server = new ServerData("", ip + ":" + port, false);
        (new Thread("Server Connector #" + threadNumber.incrementAndGet()) {
            public void run() {
                OldServerPinger pinger = new OldServerPinger();
                try {
                    System.out.println("Pinging " + ip + ":" + port + "...");
                    pinger.ping(server);
                    Thread.sleep(250L);
                    System.out.println("Pinger successful: " + ip + ":" + port);
                } catch (UnknownHostException e) {
                    System.out.println("Unknown host: " + ip + ":" + port);
                    failed = true;
                } catch (Exception e2) {
                    System.out.println("Pinger failed: " + ip + ":" + port);
                    failed = true;
                }
                pinger.clearPendingNetworks();
                done = true;
            }
        }).start();
    }

    public boolean isStillPinging() {
        return !this.done;
    }

    public boolean isWorking() {
        return !this.failed;
    }
}