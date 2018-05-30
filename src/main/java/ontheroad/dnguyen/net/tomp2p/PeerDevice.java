package ontheroad.dnguyen.net.tomp2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDN;

import java.io.IOException;

public class PeerDevice {
    private static final Logger LOGGER = LogManager.getLogger(PeerDevice.class);

    public static LocalDevice createPeer() throws ValidationException, LocalServiceBindingException, IOException {

            DeviceIdentity identity =
                    new DeviceIdentity(
                            UDN.uniqueSystemIdentifier("User")
                    );

            DeviceType type = new DeviceType("Ontheroad", "User",1);

            DeviceDetails details =
                    new DeviceDetails(
                            "For User of OTR"
                    );
            LocalService<TestUPnP> service =
                    new AnnotationLocalServiceBinder().read(TestUPnP.class);

            service.setManager(
                    new DefaultServiceManager(service, TestUPnP.class)
            );

            return new LocalDevice(identity, type, details, service);

    }
}
