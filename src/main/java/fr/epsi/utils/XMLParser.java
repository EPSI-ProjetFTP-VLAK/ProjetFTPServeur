package fr.epsi.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    private URI usersXMLFile;
    private URI serverXMLFile;

    private final String nodeUser = "user";
    private final String attributeUsername = "username";
    private  final  String attributePassword = "password";

    public XMLParser() {
        this.usersXMLFile = null;
        this.serverXMLFile = null;

        setServerXMLFile();
        setUsersXMLFile();
    }

    private void setServerXMLFile() {
        try {
            this.serverXMLFile = getClass().getClassLoader().getResource("server-configuration.xml").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void setUsersXMLFile(){
        try {
            this.usersXMLFile = getClass().getClassLoader().getResource("users.xml").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean userXMLFileIsValid() {
        return this.usersXMLFile != null;
    }

    private boolean serverXMLFileIsValid() {
        return this.serverXMLFile != null;
    }

    public synchronized String parseXMLForNode(String node) {
        String value = null;

        if(serverXMLFileIsValid()){
            try {
                File fXmlFile = new File(serverXMLFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();
                NodeList portNodeList = doc.getElementsByTagName(node);

                value = portNodeList.item(0).getTextContent();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public synchronized Map<String, String> parseAndGetUsersXMLFile(){
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
