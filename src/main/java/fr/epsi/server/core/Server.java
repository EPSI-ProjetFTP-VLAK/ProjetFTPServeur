package fr.epsi.server.core;

import fr.epsi.server.client.Client;
import fr.epsi.server.thread.ListeningThread;
import fr.epsi.utils.AbstractLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    private Thread listeningThread;
    private List clients;

    public Server() {
        AbstractLogger.log("Initialisation du serveur de fichiers ...");
        serverConfiguration = new ServerConfiguration();

        createServerSocket();

        listeningThread = new ListeningThread(serverSocket);
        clients = new ArrayList<Client>();
    }

    public void startServer() {
        AbstractLogger.log("Démarrage du serveur ...");
        listeningThread.start();
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
        listeningThread.interrupt();

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractLogger.log("Serveur arrêté ...");
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setListeningThread(Thread listeningThread) {
        this.listeningThread = listeningThread;
    }

    public List getClients() {
        return clients;
    }

    public void addClient(Client newClient) {
        this.clients.add(newClient);
    }
}
