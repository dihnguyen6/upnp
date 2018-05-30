package ontheroad.dnguyen.net.tomp2p.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.StringTokenizer;

public final class LocalConfiguration {
    private static final Logger LOGGER = LogManager.getLogger(LocalConfiguration.class);
    private static InetAddress inet;
    private static String mac = "";
    private static String ip = "";
    private static String defaultAddress = "";

    private LocalConfiguration() {

    }
    public static void getNetworkInterfaces() {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
                displayInterfaceInformation(netint);
        } catch (SocketException e) {
            LOGGER.error(e);
        }

    }

    private static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        LOGGER.info("Display name: " + netint.getDisplayName());
        LOGGER.info("Name: " + netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            LOGGER.info("InetAddress: " + inetAddress);
        }

    }

    public static String getDefaultAddress() {

        try {
            Process result = Runtime.getRuntime().exec("netstat -rn");

            BufferedReader output = new BufferedReader(new InputStreamReader(
                    result.getInputStream()));

            String line = output.readLine();
            while (line != null) {
                if (line.contains("0.0.0.0")) {

                    StringTokenizer stringTokenizer = new StringTokenizer(line);
                    stringTokenizer.nextElement(); // first element is 0.0.0.0
                    stringTokenizer.nextElement(); // second element is 0.0.0.0
                    defaultAddress = (String) stringTokenizer.nextElement();
                    break;
                }

                line = output.readLine();

            } // while

            LOGGER.info(defaultAddress);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return defaultAddress;

    } // getDefaultAddress

    public static String getIP() {
        try {
            inet = InetAddress.getLocalHost();
            ip = inet.toString();

            LOGGER.info(inet.getHostAddress());
            LOGGER.info(ip);
        } catch (UnknownHostException e) {
            LOGGER.error(e);
        }
        return ip;
    }

    public static String getMAC() {
        try {
            inet = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(inet);
            byte[] macByte = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < macByte.length; i++) {
                sb.append(String
                        .format("%02X%s", macByte[i], ""));
            }
            mac = sb.toString();
            LOGGER.info("getMAC()" + mac);
        } catch (SocketException e) {
            LOGGER.error(e);
        } catch (UnknownHostException e) {
            LOGGER.error(e);
        }
        return mac;
    }

}
