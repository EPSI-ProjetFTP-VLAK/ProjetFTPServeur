package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection implements Runnable
{
    private ServerSocket serverSocket;
    private Socket socket;

    public ClientConnection(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
                // TODO Implémenter l'authentification
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
