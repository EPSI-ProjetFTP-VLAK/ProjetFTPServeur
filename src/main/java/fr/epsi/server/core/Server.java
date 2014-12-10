package fr.epsi.server.core;

import fr.epsi.server.thread.ListeningThread;
import fr.epsi.utils.AbstractLogger;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    private Thread listeningThread;

    public Server(){
        AbstractLogger.log("Initialisation du serveur de fichiers ...");
        serverConfiguration = new ServerConfiguration();

        AbstractLogger.log("Initialisation de la socket d'écoute ...");
        createServerSocket();

        listeningThread = new ListeningThread(serverSocket);
    }

    public void startServer() {
        listeningThread.start();
    }

    private void createServerSocket() {
        try {
            serverSocket = new ServerSocket(serverConfiguration.serverPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        listeningThread.interrupt();

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Thread getListeningThread() {
        return listeningThread;
    }
}
