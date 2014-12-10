package fr.epsi.server.core;
import fr.epsi.server.thread.ListeningThread;
import fr.epsi.utils.AbstractLogger;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    private Thread listeningThread;

    public Server(){
        AbstractLogger.log("Initialisation du serveur de fichiers ...");
        serverConfiguration = new ServerConfiguration();
        initializeServerSocket();
        listeningThread = new ListeningThread(serverSocket);
    }

    private void initializeServerSocket(){
        AbstractLogger.log("Connexion du serveur au réseau local ...");

        try {
            serverSocket = new ServerSocket(serverConfiguration.serverPort());
            AbstractLogger.log("Serveur connecté !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchListeningThread(){
        listeningThread.start();
        AbstractLogger.log("Serveur démarre");
    }

    public void startServer() {
        launchListeningThread();
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
