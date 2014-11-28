package fr.epsi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class AuthentificationThread implements Runnable{
    private String dataFromSocket;
    private Socket socket;
    private XMLParser xmlParser;

    public AuthentificationThread(Socket acceptationSocket, XMLParser xmlParser){
        this.socket = acceptationSocket;
        this.dataFromSocket = getDataFromSocket();
        this.xmlParser = xmlParser;
    }

    public void run(){
        System.out.println("Tentative d'authentification");
        if(isUsernameAndPasswordAreValid()){
            Thread clientThread = new Thread(new ClientThread(getLoginFromSocketData(), socket));
            clientThread.start();
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
