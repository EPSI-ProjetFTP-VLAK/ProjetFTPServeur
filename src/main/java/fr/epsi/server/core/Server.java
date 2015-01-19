package fr.epsi.server.core;

import fr.epsi.server.client.Client;
import fr.epsi.server.thread.ListeningThread;
import fr.epsi.utils.AbstractLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    private Thread listeningThread;
    private List<Client> clients;

    public Server() {
        AbstractLogger.log("Initialisation du serveur de fichiers ...");
        serverConfiguration = new ServerConfiguration();

        createServerSocket();

        listeningThread = new ListeningThread(serverSocket);
        clients = new ArrayList<Client>();
    }

    public void run(){
        while(true){
            checkForDisconectedPeers();
        }
    }

    public void checkForDisconectedPeers(){
        for (int i = 0; i < clients.size(); i++){
            if(!clients.get(i).clientSocket().isConnected()){
                AbstractLogger.log("Connexion perdu avec le client : " + clients.get(i).username());
                clients.remove(i);
            }
        }
    }

    public void disconectAllPeers(){
        try {
            for (int i = 0; i < clients.size(); i++){
                clients.get(i).clientSocket().close();
                clients.remove(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        listeningThread.start();
        this.start();
        AbstractLogger.log("Serveur démmaré ...");
    }

    private void createServerSocket() {
        AbstractLogger.log("Initialisation de la socket d'écoute ...");

        try {
            serverSocket = new ServerSocket(serverConfiguration.serverPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        AbstractLogger.log("Arrêt du serveur ...");
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listeningThread.interrupt();
        this.interrupt();
        disconectAllPeers();
        AbstractLogger.log("Serveur arrêté ...");
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getServerBaseDirectory(){
        return serverConfiguration.serverBaseDirectory();
    }

    public void setListeningThread(Thread listeningThread) {
        this.listeningThread = listeningThread;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public synchronized void addClient(Client newClient) {
        this.clients.add(newClient);
    }
}
