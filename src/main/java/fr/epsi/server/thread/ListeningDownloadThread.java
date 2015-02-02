package fr.epsi.server.thread;

import fr.epsi.server.client.Client;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningDownloadThread extends ThreadMaster {
    private ServerSocket serverSocket;
    boolean exceptionCatch;

    public ListeningDownloadThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        exceptionCatch = false;

        while (!serverSocket.isClosed() && !exceptionCatch) {
            try {
                Socket clientSocket = serverSocket.accept();

                for (Client client : ServerManager.getFTPServer().getClients()){
                    if(client.clientSocket().getInetAddress().getHostAddress().equals(clientSocket.getInetAddress().getHostAddress())){
                        client.clientSetDownloadThread(clientSocket);
                    }
                }

            } catch (IOException e) {
                exceptionCatch=true;
                e.printStackTrace();
            }finally {
                String logToDisplay = "socket serveur fermé !\n";
                AbstractLogger.log(logToDisplay);
            }
        }
    }
}