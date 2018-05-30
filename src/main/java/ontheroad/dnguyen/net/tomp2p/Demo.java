package ontheroad.dnguyen.net.tomp2p;

import ontheroad.dnguyen.net.tomp2p.Interfaces.IUser;
import ontheroad.dnguyen.net.tomp2p.Peer.IDGenerator;
import ontheroad.dnguyen.net.tomp2p.Peer.PeerOTR;
import ontheroad.dnguyen.net.tomp2p.Peer.User;

public class Demo {

    public static void startPeer(String userName) {
        IUser user = new User(userName);
        IDGenerator id = new IDGenerator(user);
        PeerOTR peer = new PeerOTR(id);
    }
}
