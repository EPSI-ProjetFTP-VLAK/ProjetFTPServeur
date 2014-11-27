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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    private URI usersXMLFile;
    private final String nodeUser = "user";
    private final String attributeUsername = "username";
    private  final  String attributePassword = "password";

    public XMLParser() {
        this.usersXMLFile = null;
        setUsersXMLFile();
    }

    private void setUsersXMLFile(){
        try {
            this.usersXMLFile = getClass().getClassLoader().getResource("users.xml").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean userXMLFileIsValid(){
        return this.usersXMLFile != null;
    }

    public synchronized Map<String, String> parseUsersXMLFile(){
        Map<String, String> credentials = new HashMap<String, String>();

        if(userXMLFileIsValid()){
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
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(credentials == null || credentials.isEmpty()){
            credentials = Collections.emptyMap();
        }

        return credentials;
    }

}
