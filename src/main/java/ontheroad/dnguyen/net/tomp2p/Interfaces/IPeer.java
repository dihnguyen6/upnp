package ontheroad.dnguyen.net.tomp2p.Interfaces;

import net.tomp2p.dht.PeerDHT;
import net.tomp2p.peers.PeerAddress;

/**
 * This interface represent the peer of On The Road
 *
 * @author dnguyen
 */
public interface IPeer {

    boolean connect(INetworkConfiguration config);
    boolean isOnline();
    PeerDHT getPeerDHT();
    String getPeerId();
    PeerAddress getPeerAddress();
}
