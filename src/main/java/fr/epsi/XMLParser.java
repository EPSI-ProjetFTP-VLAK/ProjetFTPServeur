package fr.epsi;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    private URI usersXMLFile;
    private final String nodeUser = "user";
    private final String attributeUsername = "username";
    private  final  String attributePassword = "password";

    public XMLParser() {
        this.usersXMLFile = null;
        try {
            this.usersXMLFile = getClass().getClassLoader().getResource("users.xml").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public synchronized void parseUsersXMLFile(){
        Map<String, String> credentials = new HashMap<String, String>();

        try {
            File fXmlFile = new File(usersXMLFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList usersNodeList = doc.getElementsByTagName(nodeUser);

            for (int i = 0; i < usersNodeList.getLength(); i++){
                Node userNode = usersNodeList.item(i);

                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    credentials.put(userElement.getAttribute(attributeUsername), userElement.getAttribute(attributePassword));
                }

            }

            System.out.println(credentials.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
