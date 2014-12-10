package fr.epsi.server.thread;

import fr.epsi.utils.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthenticationThread extends Thread {

    private Socket clientSocket;

    public AuthenticationThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        if (!clientSocket.isClosed()) {
            answerToCredentialsRequest(readCredentialsFromSocket());
        }
    }

    private String readCredentialsFromSocket() {
        String credentials = "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            credentials = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credentials;
    }

    private void answerToCredentialsRequest(String credentials){
        try {
            PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
            clientSocketOutput.println("AUTH : " + (isAuthenticated(credentials) ? "OK" : "NOK"));
            clientSocketOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAuthenticated(String credentials) {
        XMLParser xmlParser = new XMLParser();
        String[] splittedCredentials = credentials.split(" ");

        return xmlParser.parseAndGetUsersXMLFile().get(splittedCredentials[0]).equals(splittedCredentials[1]);
    }
}
