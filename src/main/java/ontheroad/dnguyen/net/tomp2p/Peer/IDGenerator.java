package ontheroad.dnguyen.net.tomp2p.Peer;

import ontheroad.dnguyen.net.tomp2p.Interfaces.IIDGenerator;
import ontheroad.dnguyen.net.tomp2p.Interfaces.IUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class IDGenerator implements IIDGenerator {

    private static final Logger LOGGER = LogManager.getLogger(IDGenerator.class);
    private IUser user;
    private static String id;

    public IDGenerator(IUser user) {
        this.user = user;
        id = UUID.randomUUID().toString();
        LOGGER.info("IDGenerator() with " + user.getUserName());
        LOGGER.info(id);
    }

    @Override
    public int hashCode() {
        int hashCode = id.hashCode();
        LOGGER.info(hashCode);
        return hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        return id.equals(obj);
    }

    public IUser getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

}
