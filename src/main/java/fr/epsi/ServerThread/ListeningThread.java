package fr.epsi.ServerThread;

import fr.epsi.Utils.AbstractLogger;

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
            waitOneSecond();
            AbstractLogger.log("Le Thread a fait une itération d'écoute !");
        }
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void launchAuthentificationThread(){
        AbstractLogger.log("Un nouveau client essaye de ce connexter au serveur de fichier ...");
        AuthentificationThread authentificatorThread = new AuthentificationThread(socket);
        authentificatorThread.start();
    }

    private void waitingForNewClient(){
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
