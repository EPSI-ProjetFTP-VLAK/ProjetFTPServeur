package fr.epsi.server.thread;

import fr.epsi.utils.AbstractLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningThread extends Thread
{
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean keepListening;

    public ListeningThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.keepListening = true;
    }

    public void run() {
        while(keepListening){
            waitingForNewClient();
            launchAuthentificationThread();
            AbstractLogger.log("Le Thread a fait une itération d'écoute !");
        }
    }

    private void launchAuthentificationThread(){
        AbstractLogger.log("Un nouveau client essaye de ce connecter au serveur de fichier ...");
        AuthentificationThread authentificatorThread = new AuthentificationThread(socket);
        authentificatorThread.start();
        interrupt();
    }

    private void waitingForNewClient(){
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
