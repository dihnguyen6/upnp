package ontheroad.dnguyen.net.tomp2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;

import static ontheroad.dnguyen.net.tomp2p.PeerDevice.createPeer;

public final class Main implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private Main() {
    }

    public static void main(String[] args) throws NumberFormatException, Exception {
        //Test.startServer();
        //Test.startUser("Versus");
        //Test.startPeer("192.168.0.102");
        //LocalConfiguration.getNetworkInterfaces();
        //Demo.startPeer("Versus");

        // UPnP discovery is asynchronous, we need a callback
        Thread serverThread = new Thread(new Main());
        serverThread.setDaemon(false);
        serverThread.start();
    }

    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createPeer()
            );

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
