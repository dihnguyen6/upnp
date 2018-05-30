package ontheroad.dnguyen.net.tomp2p.Interfaces;

import java.math.BigInteger;

public interface OTRConst {
    // Standard Port for Network
    int OTR_PORT = 55556;
    int UDP_PORT = 55555;
    int TCP_PORT = 55555;

    // Default file configuration
    BigInteger MEGABYTES = BigInteger.valueOf(1024 * 1024);
    BigInteger DEFAULT_MAX_FILE_SIZE = BigInteger.valueOf(25).multiply(MEGABYTES);// 25 MB

    String NETWORK_FILE_NAME = "otr-network.cfg";

    // Standard timeout for discover, bootstrapp and disconnect
    long DISCOVERY_TIMEOUT_MS = 20000;

    long BOOTSTRAPPING_TIMEOUT_MS = 20000;

    long DISCONNECT_TIMEOUT_MS = 20000;

    // Standard timeout for Thread
    long SLEEP = 6000;

    // Information of Ontheroad-User
    String OTR_FLAG = "On The Road";
    String DEFAULT_USER = "OTR.User";

    boolean ENABLE_RELAY = true;
}
