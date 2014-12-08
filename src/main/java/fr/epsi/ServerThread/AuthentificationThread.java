package fr.epsi.ServerThread;

import fr.epsi.Utils.AbstractLogger;
import fr.epsi.Utils.XMLParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class AuthentificationThread extends Thread{
    private String dataFromSocket;
    private Socket socket;
    private XMLParser xmlParser;

    public AuthentificationThread(Socket acceptationSocket){
        this.socket = acceptationSocket;
        this.dataFromSocket = getDataFromSocket();
        this.xmlParser = new XMLParser();
    }

    public void run(){
        AbstractLogger.log("Tentative d'authentification");
        if(isUsernameAndPasswordAreValid()){
            Thread clientThread = new Thread(new ClientThread(getLoginFromSocketData(), socket));
            clientThread.start();
        }else{
            AbstractLogger.log("Echéc de l'authentification !");
        }
    }

    private String getDataFromSocket(){
        String dataFromSocket = "";

        try{
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataFromSocket = inputReader.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }

        return dataFromSocket;
    }

    public boolean isUsernameAndPasswordAreValid(){
        return xmlParser.parseAndGetUsersXMLFile().get(getLoginFromSocketData()).equals(getPasswordFromSocketData());
    }

    private String getPasswordFromSocketData() {
        return this.dataFromSocket.split(" ")[1];
    }

    private String getLoginFromSocketData() {
        return  this.dataFromSocket.split(" ")[0];
    }
}
