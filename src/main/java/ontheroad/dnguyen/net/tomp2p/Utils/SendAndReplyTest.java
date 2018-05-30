package ontheroad.dnguyen.net.tomp2p.Utils;

import net.tomp2p.futures.FutureDirect;
import net.tomp2p.message.CountConnectionOutboundHandler;
import net.tomp2p.p2p.Peer;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public final class SendAndReplyTest {

    private static final Logger LOGGER = LogManager.getLogger(SendAndReplyTest.class);

    private static final CountConnectionOutboundHandler CCOH_TCP =
            new CountConnectionOutboundHandler();
    private static final CountConnectionOutboundHandler CCOH_UDP =
            new CountConnectionOutboundHandler();

    //private static SendAndReplyTest instance = null;

    private SendAndReplyTest() {
    }

    /*
    public static SendAndReplyTest getInstance() {
        if (instance == null) {
            instance = new SendAndReplyTest();
        }
        return instance;
    }
    */

    public static void send(Peer host, PeerAddress des) {
        try {
            String send = "Hello";
            FutureDirect fd = host.sendDirect(des).object(send).start();
            LOGGER.info("Send: " + send);
            fd.awaitUninterruptibly();

            LOGGER.info("received " + fd.object() + " connections: "
                    + CCOH_TCP.total() + "/" + CCOH_UDP.total());

        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
    }

    public static void reply(Peer des) {
        des.objectDataReply(new ObjectDataReply() {
            @Override
            public Object reply(final PeerAddress sender, final Object request) throws Exception {
                return "World!";
            }
        });
    }

}
