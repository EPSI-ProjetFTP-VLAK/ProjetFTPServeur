package fr.epsi.server.thread;

import fr.epsi.server.client.Client;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;
import fr.epsi.utils.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthenticationThread extends ThreadMaster {

    private Socket clientSocket;

    public AuthenticationThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        if (!clientSocket.isClosed()) {
            String[] credentials = readCredentialsFromSocket();
            boolean isAuthenticated = isAuthenticated(credentials);
            
            if (isAuthenticated) {
                ServerManager.getFTPServer().addClient(new Client(credentials[0], clientSocket));
                AbstractLogger.log(credentials[0] + " est maintenant connecté !");
            }

            answerToCredentialsRequest(isAuthenticated);
        }else{
        	this.stopThread();
        }
    }

    private String[] readCredentialsFromSocket() {
        String credentials = "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            credentials = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credentials.split(" ");
    }

    private boolean isAuthenticated(String[] credentials) {
        XMLParser xmlParser = new XMLParser();

        return xmlParser.parseAndGetUsersXMLFile().get(credentials[0]).equals(credentials[1]) && !clientIsAlreadyConnected(credentials);
    }

    private boolean clientIsAlreadyConnected(String[] credentials){
        boolean clientAlreadyConnected = false;

        for(Client client : ServerManager.getFTPServer().getClients()){
            if (client.username().equals(credentials[0])){
                clientAlreadyConnected = true;
            }
        }

        return clientAlreadyConnected;
    }

    private void answerToCredentialsRequest(boolean isAuthenticated){
        try {
            PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
            clientSocketOutput.println("AUTH : " + (isAuthenticated ? "OK" : "NOK"));
            clientSocketOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
