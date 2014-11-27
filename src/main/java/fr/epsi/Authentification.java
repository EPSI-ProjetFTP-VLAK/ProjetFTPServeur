package fr.epsi;

import java.net.Socket;

public class Authentification implements  Runnable{
    private Socket socket;
    private XMLParser xmlParser;

    public Authentification(Socket acceptationSocket, XMLParser xmlParser){
        this.socket = acceptationSocket;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run() {
        xmlParser.parseUsersXMLFile();
    }
}
