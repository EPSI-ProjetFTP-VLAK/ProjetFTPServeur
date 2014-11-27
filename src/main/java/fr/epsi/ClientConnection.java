package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection implements Runnable
{
    private ServerSocket serverSocket;
    private Socket socket;
    private XMLParser xmlParser;
    private boolean stop;

    public ClientConnection(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.socket = null;
        this.xmlParser = new XMLParser();
        this.stop = false;
    }

    public void run() {
        try {
            while (!stop) {
                this.socket = serverSocket.accept();

                System.out.println("Un nouveau client essaye de ce connexter au serveur de fichier ...");

                //TO_DO Implementer l'authentification au SRV
                Thread authentificationThread = new Thread(new Authentification(socket, xmlParser));
                authentificationThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.stop = true;
    }
}
