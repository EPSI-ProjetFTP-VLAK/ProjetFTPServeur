package fr.epsi.server.core;

import fr.epsi.server.client.Client;
import fr.epsi.server.thread.ListeningDownloadThread;
import fr.epsi.server.thread.ListeningThread;
import fr.epsi.server.thread.ListeningUploadThread;
import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends ThreadMaster{
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    private ServerSocket serverSocketUpload;
    private ServerSocket serverSocketDownload;
    private Thread listeningThread;
    private ListeningUploadThread listenerUpload;
    private ListeningDownloadThread listenerDownload;
    private List<Client> clients;

    public Server() {
        AbstractLogger.log("Initialisation du serveur de fichiers ...");
        serverConfiguration = new ServerConfiguration();

        createServerSocket();

        listeningThread = new ListeningThread(serverSocket);
        listenerDownload = new ListeningDownloadThread(serverSocketDownload);
        listenerUpload = new ListeningUploadThread(serverSocketUpload);

        clients = new ArrayList<Client>();
    }

    public void run(){
        while(!stop){
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
                clients.get(i).disconnectClient();
                clients.get(i).clientSocket().close();
                clients.remove(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        listeningThread.start();
        listenerDownload.startThread();
        listenerUpload.startThread();

        this.startThread();
        AbstractLogger.log("Serveur démmaré ...");
    }

    private void createServerSocket() {
        AbstractLogger.log("Initialisation de la socket d'écoute ...");

        try {
            int realPort = serverConfiguration.serverPort();
            serverSocket = new ServerSocket(realPort);
            serverSocketDownload = new ServerSocket(realPort + 1);
            serverSocketUpload = new ServerSocket(realPort + 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        AbstractLogger.log("Arrêt du serveur ...");

        try {
            disconectAllPeers();
            try {
                listeningThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listeningThread.interrupt();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stopThread();

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
