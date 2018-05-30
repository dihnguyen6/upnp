package ontheroad.dnguyen.net.tomp2p.Interfaces;

/**
 * This interface represent ID-Generator for PeerOTR in On The Road - Network
 *
 * @author dnguyen
 */
public interface IIDGenerator {

    IUser getUser();
    String getId();
}
