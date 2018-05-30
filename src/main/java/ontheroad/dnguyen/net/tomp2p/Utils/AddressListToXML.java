package ontheroad.dnguyen.net.tomp2p.Utils;

import net.tomp2p.peers.PeerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class AddressListToXML {
    private static final Logger LOGGER = LogManager.getLogger(AddressListToXML.class);

    public static void creatAddressList(List<PeerAddress> peerAddressList) {
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("Network-Member");
            doc.appendChild(rootElement);

            // P2P element
            Element P2PNetwork = doc.createElement("P2P");
            rootElement.appendChild(P2PNetwork);

            // setting attribute to element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            P2PNetwork.setAttributeNode(attr);

            for(int i = 0; i < peerAddressList.size(); i++) {
                Element peer = doc.createElement("peer");
                Attr attrId = doc.createAttribute("id");
                attrId.setValue(peerAddressList.get(i).peerId().toString());
                peer.setAttributeNode(attrId);
                peer.appendChild(doc.createTextNode(peerAddressList.get(i).inetAddress().getHostAddress()));
                P2PNetwork.appendChild(peer);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("\\Network-Test.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            //StreamResult consoleResult = new StreamResult(System.out);
            //transformer.transform(source, consoleResult);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
