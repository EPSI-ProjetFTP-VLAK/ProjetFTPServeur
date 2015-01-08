package fr.epsi.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningThread extends Thread {

    private ServerSocket serverSocket;

    public ListeningThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();

                AuthenticationThread authenticationThread = new AuthenticationThread(clientSocket);
                authenticationThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
