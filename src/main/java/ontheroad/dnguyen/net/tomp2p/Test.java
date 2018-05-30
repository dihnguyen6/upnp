package ontheroad.dnguyen.net.tomp2p;

import net.tomp2p.connection.Bindings;
import net.tomp2p.connection.ChannelClientConfiguration;
import net.tomp2p.connection.DiscoverNetworks;
import net.tomp2p.connection.PeerConnection;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFutureAdapter;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDiscover;
import net.tomp2p.message.Message;
import net.tomp2p.nat.FutureNAT;
import net.tomp2p.nat.PeerBuilderNAT;
import net.tomp2p.nat.PeerNAT;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.p2p.builder.BootstrapBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.relay.BaseRelayClient;
import net.tomp2p.relay.RelayClientConfig;
import net.tomp2p.relay.RelayType;
import net.tomp2p.storage.Data;
import ontheroad.dnguyen.net.tomp2p.Interfaces.IUser;
import ontheroad.dnguyen.net.tomp2p.Peer.IDGenerator;
import ontheroad.dnguyen.net.tomp2p.Peer.User;
import ontheroad.dnguyen.net.tomp2p.Utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public final class Test {
    private static final Logger LOGGER = LogManager.getLogger("DEMO");

    private static Peer server;
    private static Peer client;
    private static Test instance = null;
    private static Peer peer;
    private static final int SERVER_PORT = 6000;
    private static final int CLIENT_PORT = 6016;
    private static final int OTR_PORT = 5555;
    private static final int SLEEP = 2000;
    private static final int TCP = 61568;
    private static final int UDP = 61568;
    private static Collection<PeerAddress> addressList;
    private static PeerDHT[] peers;


    private Test() {

    }

    public static Test getInstance() {
        if (instance == null) {
            instance = new Test();
        }
        return instance;
    }

    public static void startUser(String userName) {
        IUser user = new User(userName);
        IDGenerator id = new IDGenerator(user);
        /*try {
            Bindings b = new Bindings()
                    .listenAny();
            //b.addInterface("eth1");
            peer = new PeerBuilder(
                    new Number160(id.generatedID().getBytes()))
                    .ports(OTR_PORT)
                    .bindings(b)
                    .behindFirewall(false)
                    //.enableBroadcast(true)
                    .tcpPort(TCP)
                    .udpPort(UDP)
                    .start();
            LOGGER.debug(peer);
            final PeerNAT peerNAT = new PeerBuilderNAT(peer).start();
            LOGGER.debug(peerNAT);

            final PeerAddress pa = new PeerAddress(Number160.ZERO, InetAddress.getByName("192.168.0.102"), SERVER_PORT, SERVER_PORT, SERVER_PORT + 1);
            final FutureDiscover fd = peer.discover().peerAddress(pa).start();
            final FutureNAT fn = peerNAT.startSetupPortforwarding(fd);
            LOGGER.debug(peer.peerBean().peerMap().all());
        } catch (IOException e) {
            LOGGER.error(e);
        }*/
    }

    /*public static void startPeer(String ip) {
        try {
            peer = new PeerBuilder(new Number160(IDGenerator.generatedID().getBytes())).ports(OTR_PORT).behindFirewall().start();
            LOGGER.info(peer.peerAddress());
            final PeerNAT peerNAT = new PeerBuilderNAT(peer).start();
            final PeerAddress pa = new PeerAddress(Number160.ZERO, InetAddress.getByName(ip), SERVER_PORT, SERVER_PORT, SERVER_PORT + 1);

            final FutureDiscover fd = peer.discover().peerAddress(pa).start();
            fd.awaitUninterruptibly();
            final FutureNAT fn = peerNAT.startSetupPortforwarding(fd);
            fn.awaitUninterruptibly();
            fn.addListener(new BaseFutureAdapter<FutureNAT>() {

                @Override
                public void operationComplete(FutureNAT future) throws Exception {
                    if(future.isFailed()) {
                        peerNAT.startRelay(new RelayClientConfig(RelayType.BUFFERED_OPENTCP, 1000, 1000, 1000) {
                            @Override
                            public BaseRelayClient createClient(PeerConnection peerConnection, PeerOTR peer) {
                                return null;
                            }

                            @Override
                            public void prepareSetupMessage(Message message) {

                            }

                            @Override
                            public void prepareMapUpdateMessage(Message message) {

                            }
                        }, fd.reporter());
                        //LOGGER.debug("StartRelay Failed");
                    }

                }
            });


            if (fd.isSuccess()) {
                LOGGER.info("Found that my outside address is " + fd.peerAddress());
            } else {
                LOGGER.info("Failed " + fd.failedReason());
            }

            if (fn.isSuccess()) {
                LOGGER.info("NAT success: " + fn.peerAddress());
            } else {
                LOGGER.info("Failed " + fn.failedReason());
                //this is enough time to print out the status of the relay search
                Thread.sleep(SLEEP);
            }

            peer.shutdown();
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void startServer() throws Exception {
        Bindings b = new Bindings();
        peer = new PeerBuilder(new Number160(IDGenerator.generatedID().getBytes()))
                .ports(SERVER_PORT).bindings(b).start();

        LOGGER.info("peer started.");
        for (;;) {
            for (PeerAddress pa : peer.peerBean().peerMap().all()) {
                LOGGER.info("peer online (TCP):" + pa);
                addressList = peer.peerBean().peerMap().all();
                LOGGER.info("List Address size(): " + addressList.size());
                LOGGER.info(((List<PeerAddress>) addressList).get(0).inetAddress().getHostAddress());
                //startClient(((List<PeerAddress>) addressList).get(0).inetAddress().getHostAddress());
                LOGGER.info(((List<PeerAddress>) addressList).get(0).peerId());
                //AddressListToXML.creatAddressList((List<PeerAddress>)addressList);
                peers = new PeerDHT[2];
                peers[0] = new PeerBuilderDHT(peer).start();
                peers[1] = new PeerBuilderDHT(new PeerBuilder(pa.peerId()).ports(pa.tcpPort()).start()).start();
                *//*BootstrapBuilder bb = peers[1].peer().bootstrap();
                bb.bootstrapTo();
                bb.start().awaitUninterruptibly();*//*
                Util.bootstrap(peers);
                Data data = new Data("Hello");
                Number160 nr = new Number160(new Random());
                FuturePut fp = peers[0].put(nr).data(data).start();
                fp.awaitUninterruptibly();
            }
            *//*PeerOTR another = new PeerBuilder(((List<PeerAddress>) addressList).get(0).peerId()).ports(6789).masterPeer(peer).start();
            FutureDiscover future = another.discover().peerAddress(peer.peerAddress()).start();
            future.awaitUninterruptibly();
            FutureBootstrap fb = another.bootstrap().peerAddress(peer.peerAddress()).start();
            fb.awaitUninterruptibly();
            PeerDHT pdht = new PeerBuilderDHT(another).start();
            Data data = new Data("Hello");
            Number160 nr = new Number160(new Random());
            FuturePut fp = pdht.put(nr).data(data).start();
            fp.awaitUninterruptibly();*//*
            //Util.bootstrap(peers);
            //Thread.sleep(SLEEP);

        }
    }*/


    /*public static void startClient(String ipAddress) throws Exception {
        IDGenerator idClient = new IDGenerator("Client");
        IDGenerator.generatedID();
        ChannelClientConfiguration ccc = PeerBuilder.createDefaultChannelClientConfiguration();
        Bindings b = new Bindings().listenAny();

        client = new PeerBuilder(new Number160("Client".getBytes()))
                .ports(CLIENT_PORT)
                .bindings(b)
                .channelClientConfiguration(ccc)
                .start();
        LOGGER.info("Client started and Listening to: "
                + DiscoverNetworks.discoverInterfaces(b));
        LOGGER.info("Client's address " + client.peerAddress());

        InetAddress address = Inet4Address.getByName(ipAddress);
        int masterPort = SERVER_PORT;
        PeerAddress pa = new PeerAddress(Number160.ZERO,
                address,
                masterPort,
                masterPort,
                masterPort + 1);

        LOGGER.info("PeerAddress: " + pa);

        // Future Discover
        FutureDiscover futureDiscover = client
                .discover()
                .expectManualForwarding()
                .inetAddress(address)
                .ports(masterPort)
                .start();
        futureDiscover.awaitUninterruptibly();

        // Future Bootstrap - slave
        FutureBootstrap futureBootstrap = client
                .bootstrap()
                .inetAddress(address)
                .ports(masterPort)
                .start();
        futureBootstrap.awaitUninterruptibly();

        addressList = client.peerBean().peerMap().all();
        LOGGER.info(" List Address size(): " + addressList.size());
        LOGGER.info(((List<PeerAddress>) addressList).get(0).toString());

        if (futureDiscover.isSuccess()) {
            *//*for (int i = 0; i < addressList.size(); i++){
                SendAndReplyTest.send(client,((List<PeerAddress>) addressList).get(i));
            }*//*

            LOGGER.info("Found " + futureDiscover.peerAddress());
        } else {
            LOGGER.info("Not Found " + futureDiscover.failedReason());
        }
        Thread.sleep(SLEEP);
        client.shutdown();
    }*/

    public Collection<PeerAddress> getAddressList() {
        return addressList;
    }

    public Peer getClient() {
        return client;
    }

    public Peer getMaster() {
        return server;
    }
}
