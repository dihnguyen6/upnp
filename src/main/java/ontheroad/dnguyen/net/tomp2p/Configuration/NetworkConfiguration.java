package ontheroad.dnguyen.net.tomp2p.Configuration;

import net.tomp2p.p2p.Peer;
import ontheroad.dnguyen.net.tomp2p.Interfaces.INetworkConfiguration;
import ontheroad.dnguyen.net.tomp2p.Interfaces.OTRConst;

import java.net.InetAddress;
import java.util.UUID;

public class NetworkConfiguration implements INetworkConfiguration {
    private static final int AUTO_PORT = -1;

    private String nodeID = UUID.randomUUID().toString();
    private int port = AUTO_PORT;
    private InetAddress bootstrapAddress = null;
    private boolean isLocal = false;
    private Peer bootstrapPeer = null;
    private int bootstrapPort = OTRConst.OTR_PORT;
    private boolean isFirewalled = false;
    private boolean tryUpnp = false;

    /**
     * @param nodeID defines the location of the peer in the DHT. Should not be null
     * @return this instance
     */
    public NetworkConfiguration setNodeId(String nodeID) {
        this.nodeID = nodeID;
        return this;
    }

    /**
     * @param port defines the port to bind. Should be free or negative (autodetect)
     * @return this instance
     */
    public NetworkConfiguration setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * @param bootstrapAddress the address to bootstrap to. If it is <code>null</code>, the peer is 'initial'
     *            and does not bootstrap to any other peer.
     * @return this instance
     */
    public NetworkConfiguration setBootstrap(InetAddress bootstrapAddress) {
        return setBootstrap(bootstrapAddress, OTRConst.OTR_PORT);
    }

    /**
     * @param bootstrapPort the port to bootstrap to
     * @return this instance
     */
    public NetworkConfiguration setBootstrapPort(int bootstrapPort) {
        this.bootstrapPort = bootstrapPort;
        return this;
    }

    /**
     *
     * @param bootstrapAddress the address to bootstrap to. If it is <code>null</code>, the peer is 'initial'
     *            and does not bootstrap to any other peer.
     * @param bootstrapPort the port to bootstrap to
     * @return this instance
     */
    public NetworkConfiguration setBootstrap(InetAddress bootstrapAddress, int bootstrapPort) {
        this.bootstrapAddress = bootstrapAddress;
        this.bootstrapPort = bootstrapPort;
        return this;
    }

    /**
     * @param bootstrapPeer the initial local peer to bootstrap to. Note: this is just for testings
     * @return this instance
     */
    public NetworkConfiguration setBootstrapLocal(Peer bootstrapPeer) {
        this.bootstrapPeer = bootstrapPeer;
        return setLocal();
    }

    /**
     * Set the peer to only connect locally
     *
     * @return this instance
     */
    public NetworkConfiguration setLocal() {
        this.isLocal = true;
        return this;
    }

    /**
     * Set whether this peer is firewalled (or behind a NAT) or not.
     *
     * @param isFirewalled the flag whether this peer is behind a firewall
     * @return this instance
     */
    public NetworkConfiguration setFirewalled(boolean isFirewalled) {
        this.isFirewalled = isFirewalled;
        return this;
    }

    /**
     * If this peer is {@link #isFirewalled}, you could use UPnP to configure the port mapping at the NAT
     * device.
     *
     * @param tryUpnp the flag whether upnp setup should be tried
     * @return this instance
     */
    public NetworkConfiguration tryUPnP(boolean tryUpnp) {
        this.tryUpnp = tryUpnp;
        return this;
    }

    /**
     * Create network configuration for initial peer with random node id
     *
     * @return the network configuration
     */
    public static NetworkConfiguration createInitial() {
        return createInitial(UUID.randomUUID().toString());
    }

    /**
     * Create network configuration for initial peer with given node id.
     *
     * @param nodeID defines the location of the peer in the DHT
     * @return the network configuration
     */
    public static NetworkConfiguration createInitial(String nodeID) {
        return new NetworkConfiguration().setNodeId(nodeID).setPort(AUTO_PORT);
    }

    /**
     * Create network configuration for 'normal' peer with random node id. The bootstrapping happens at the
     * default port {@link OTRConst#OTR_PORT}.
     *
     * @param bootstrapAddress the address to bootstrap to. This can be address of the initial peer or any
     *            other peer connected to the DHT.
     * @return the network configuration
     */
    public static NetworkConfiguration create(InetAddress bootstrapAddress) {
        return create(UUID.randomUUID().toString(), bootstrapAddress);
    }

    /**
     * Create network configuration for 'normal' peer. The bootstrapping happens at the default port
     * {@link OTRConst#OTR_PORT}.
     *
     * @param nodeID defines the location of the peer in the DHT. Should not be null
     * @param bootstrapAddress the address to bootstrap to. This can be address of the initial peer or any
     *            other peer connected to the DHT.
     * @return the network configuration
     */
    public static NetworkConfiguration create(String nodeID, InetAddress bootstrapAddress) {
        return new NetworkConfiguration().setNodeId(nodeID).setPort(AUTO_PORT).setBootstrap(bootstrapAddress,
                OTRConst.OTR_PORT);
    }

    /**
     * Create network configuration for 'normal' peer. The bootstrapping happens to the specified address and
     * port
     *
     * @param nodeID defines the location of the peer in the DHT. Should not be null
     * @param bootstrapAddress the address to bootstrap to. This can be address of the initial peer or any
     *            other peer connected to the DHT.
     * @param bootstrapPort the port to bootstrap
     * @return the network configuration
     */
    public static NetworkConfiguration create(String nodeID, InetAddress bootstrapAddress, int bootstrapPort) {
        return new NetworkConfiguration()
                .setNodeId(nodeID).setPort(AUTO_PORT).setBootstrap(bootstrapAddress, bootstrapPort);
    }

    /**
     * Creates a local peer that is only able to bootstrap to a peer running on the same host.
     *
     * @param nodeID the id of the peer to create
     * @param initialPeer the peer to bootstrap to
     * @return the network configuration for local peers
     */
    public static NetworkConfiguration createLocalPeer(String nodeID, Peer initialPeer) {
        return new NetworkConfiguration().setNodeId(nodeID).setPort(AUTO_PORT).setBootstrapLocal(initialPeer);
    }

    /**
     * Create a local initial peer. Regard that bootstrapping may only work for peers running on the same
     * host.
     *
     * @param nodeID the id of the initial peer
     * @return the network configuration for local peers (initial)
     */
    public static NetworkConfiguration createInitialLocalPeer(String nodeID) {
        return new NetworkConfiguration().setNodeId(nodeID).setPort(AUTO_PORT).setLocal();
    }

    @Override
    public String getNodeID() {
        return nodeID;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isInitial() {
        return bootstrapAddress == null;
    }

    @Override
    public InetAddress getBootstrapAddress() {
        return bootstrapAddress;
    }

    @Override
    public boolean isLocal() {
        return isLocal;
    }

    @Override
    public Peer getBootstapPeer() {
        return bootstrapPeer;
    }

    @Override
    public int getBootstrapPort() {
        return bootstrapPort;
    }

    @Override
    public boolean isFirewalled() {
        return isFirewalled;
    }

    @Override
    public boolean tryUPnP() {
        return tryUpnp;
    }
}
