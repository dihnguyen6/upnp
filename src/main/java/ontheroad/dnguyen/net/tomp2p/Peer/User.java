package ontheroad.dnguyen.net.tomp2p.Peer;

import ontheroad.dnguyen.net.tomp2p.Interfaces.IUser;
import ontheroad.dnguyen.net.tomp2p.Interfaces.OTRConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User implements IUser {
    private static final Logger LOGGER = LogManager.getLogger(IUser.class);
    private String user;

    public User() {
        this.user = OTRConst.DEFAULT_USER;
        LOGGER.info("User() with " + user);
    }

    public User(String userName) {
        this.user = userName;
        LOGGER.info("User() with " + user);
    }

    @Override
    public String getUserName() {
        return user;
    }
}
