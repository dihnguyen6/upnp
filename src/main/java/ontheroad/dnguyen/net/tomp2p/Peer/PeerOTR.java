package ontheroad.dnguyen.net.tomp2p.Peer;

import net.tomp2p.connection.Bindings;
import net.tomp2p.connection.ChannelClientConfiguration;
import net.tomp2p.connection.ChannelServerConfiguration;
import net.tomp2p.connection.Ports;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.replication.IndirectReplication;
import ontheroad.dnguyen.net.tomp2p.Interfaces.IIDGenerator;
import ontheroad.dnguyen.net.tomp2p.Interfaces.INetworkConfiguration;
import ontheroad.dnguyen.net.tomp2p.Interfaces.IPeer;
import ontheroad.dnguyen.net.tomp2p.Interfaces.OTRConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PeerOTR implements IPeer {

    private static final Logger LOGGER = LogManager.getLogger(IPeer.class);

    private PeerDHT peerDHT;
    private Bindings binding;
    private boolean status;

    private IIDGenerator id;

    private static final ChannelClientConfiguration clientConfig
            = PeerBuilder.createDefaultChannelClientConfiguration();
    private static final ChannelServerConfiguration serverConfig
            = PeerBuilder.createDefaultChannelServerConfiguration();

    public PeerOTR(IDGenerator id) {
        try {
            this.id = id;
            this.binding = new Bindings().setListenAny(true);
            serverConfig.ports(new Ports(OTRConst.TCP_PORT, OTRConst.UDP_PORT));

            this.peerDHT = new PeerBuilderDHT(
                    new PeerBuilder(
                            new Number160(id.hashCode())
                    ).bindings(binding)
                            .channelClientConfiguration(clientConfig)
                            .channelServerConfiguration(serverConfig)
                            .ports(OTRConst.OTR_PORT).start()
            ).start();
            //startReplication();
            this.status = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startReplication() {
        if (OTRConst.ENABLE_RELAY) {
            IndirectReplication replication = new IndirectReplication(peerDHT);
            replication.replicationFactor(5);
            replication.intervalMillis(1000);

            replication.keepData(true);
            replication.start();
        }
    }

    public boolean connect(INetworkConfiguration config){
        return true;
    }
    /**
     * Disconnects a peer from the network.
     *
     * @return <code>true</code>, if disconnection was successful, <code>false</code> otherwise.
     */
    public boolean disconnect() {
        boolean isDisconnected = true;
        if (isConnected()) {
            // notify neighbors about shutdown
            peerDHT.peer().announceShutdown().start().awaitUninterruptibly(OTRConst.DISCONNECT_TIMEOUT_MS);
            // shutdown the peer, giving a certain timeout
            isDisconnected = peerDHT.shutdown().awaitUninterruptibly(OTRConst.DISCONNECT_TIMEOUT_MS);

            if (isDisconnected) {
                LOGGER.debug("Peer successfully disconnected.");
            } else {
                LOGGER.warn("Peer disconnection failed.");
            }
        } else {
            LOGGER.warn("Peer disconnection failed. Peer is not connected.");
        }

        return isDisconnected;
    }

    public boolean isConnected() {
        return peerDHT != null && !peerDHT.peer().isShutdown();
    }

    @Override
    public boolean isOnline() {
        return status;
    }

    @Override
    public PeerDHT getPeerDHT() {
        return peerDHT;
    }

    @Override
    public String getPeerId() {
        return peerDHT.peerID().toString();
    }

    @Override
    public PeerAddress getPeerAddress() {
        return peerDHT.peerAddress();
    }
}
