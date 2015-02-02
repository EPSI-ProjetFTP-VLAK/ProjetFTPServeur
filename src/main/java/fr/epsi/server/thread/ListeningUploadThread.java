package fr.epsi.server.thread;

import fr.epsi.server.client.Client;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningUploadThread extends ThreadMaster {
    private ServerSocket serverSocket;
    boolean exceptionCatch;

    public ListeningUploadThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        exceptionCatch = false;

        while (!serverSocket.isClosed() && !exceptionCatch) {
            try {
                Socket clientSocket = serverSocket.accept();
                attributeSocketToTheCorrectClient(clientSocket);
            } catch (IOException e) {
                exceptionCatch=true;
                e.printStackTrace();
            }finally {
                if(exceptionCatch)
                    AbstractLogger.log("socket serveur fermé !\n");
            }
        }
    }

    private void attributeSocketToTheCorrectClient(Socket clientSocket) {
        boolean correspondanceMatch = false;
        for (Client client : ServerManager.getFTPServer().getClients()){
            if(socketsIPcorrespondingToClient(clientSocket, client)){
                client.clientSetUploadThread(clientSocket);
                correspondanceMatch = true;
            }
        }

        if(correspondanceMatch){
            AbstractLogger.log("Connexion Entrante : nouvelle hôte " + clientSocket.getInetAddress().getHostAddress());
        }else{
            AbstractLogger.log("Connexion Entrante : Aucune correspondance trouvé pour l'hôte " + clientSocket.getInetAddress().getHostAddress() + " fermeture du fluxs");
        }
    }

    private boolean socketsIPcorrespondingToClient(Socket clientSocket, Client client) {
        return client.clientSocket().getInetAddress().getHostAddress().equals(clientSocket.getInetAddress().getHostAddress());
    }
}
