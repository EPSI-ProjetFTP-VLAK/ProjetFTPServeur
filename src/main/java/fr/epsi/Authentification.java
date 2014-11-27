package fr.epsi;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class Authentification implements  Runnable{
    private Socket socket;
    private XMLParser xmlParser;
    private boolean authentificationIsValid;

    public Authentification(Socket acceptationSocket, XMLParser xmlParser){
        this.socket = acceptationSocket;
        this.xmlParser = xmlParser;
        this.authentificationIsValid = false;
    }

    @Override
    public void run() {
       isUsernameAndPasswordAreValid(xmlParser.parseUsersXMLFile(), getUsernameAndPasswordFromSocket());
    }

    //TO_DO lire les données de la socket et les retourné
    private String getUsernameAndPasswordFromSocket(){
        return "";
    }

    //TO_DO regarder si les données de la socket sont dans le ficihier de conf si c'est le cas passer l'attribut a TRUE
    private void isUsernameAndPasswordAreValid(Map<String, String> usersList, String usernameAndPasswordFromSocket){
        this.authentificationIsValid = true;
    }

    public boolean authentificationIsValid(){
        return this.authentificationIsValid;
    }
}
