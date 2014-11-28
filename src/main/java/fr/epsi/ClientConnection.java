package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection implements Runnable
{
    private ServerSocket serverSocket;
    private Socket socket;
    private XMLParser xmlParser;

    public ClientConnection(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.socket = null;
        this.xmlParser = new XMLParser();
    }

    public void run() {
        try {
            socket = serverSocket.accept();

            System.out.println("Un nouveau client essaye de ce connexter au serveur de fichier ...");

            Thread authentificatorThread = new Thread(new AuthentificationThread(socket, xmlParser));
            authentificatorThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
