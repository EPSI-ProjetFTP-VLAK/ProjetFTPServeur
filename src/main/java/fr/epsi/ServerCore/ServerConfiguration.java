package fr.epsi.ServerCore;

import fr.epsi.Utils.AbstractLogger;
import fr.epsi.Utils.XMLParser;
import org.w3c.dom.DOMException;

public class ServerConfiguration {
    private int PORT;
    private int TIME_OUT;
    private String BASE_DIRECTORY;


    public ServerConfiguration(){
        AbstractLogger.log("Chargement de la configuration du serveur ...");
        try {
            loadAndDisplayConfiguration();
        }catch (DOMException e){
            e.printStackTrace();
        }
        AbstractLogger.log("Configuration chargée !");
    }

    private void loadAndDisplayConfiguration() {
        XMLParser xmlParser = new XMLParser();
        PORT = Integer.parseInt(xmlParser.serverPort());
        AbstractLogger.log("Port d'écoute : " + PORT);
        TIME_OUT = Integer.parseInt(xmlParser.serverTimeOut());
        AbstractLogger.log("Time out : " + TIME_OUT);
        BASE_DIRECTORY = xmlParser.serverBaseDirectory();
        AbstractLogger.log("Répertoire de base : " + BASE_DIRECTORY);
    }

    public int serverPort(){
        return PORT;
    }

    public int serverTimeOut(){
        return TIME_OUT;
    }

    public String serverBaseDirectory(){
        return BASE_DIRECTORY;
    }


}
