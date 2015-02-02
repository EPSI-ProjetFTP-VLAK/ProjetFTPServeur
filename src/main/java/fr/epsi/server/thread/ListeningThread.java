package fr.epsi.server.thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

public class ListeningThread extends ThreadMaster {
    private ServerSocket serverSocket;
    boolean exceptionCatch;

    public ListeningThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        exceptionCatch = false;

        while (!serverSocket.isClosed() && !exceptionCatch) {
            try {
                 Socket clientSocket = serverSocket.accept();
                sendAcceptationMessage(clientSocket);

                AuthenticationThread authenticationThread = new AuthenticationThread(clientSocket);
                authenticationThread.startThread();
            } catch (IOException e) {
                exceptionCatch=true;
                e.printStackTrace();
            }finally {
                String logToDisplay = "socket serveur fermé !\n";
                AbstractLogger.log(logToDisplay);
            }
        }
    }

    private void sendAcceptationMessage(Socket clientSocket) {
        try {
            if(clientSocket.getOutputStream() != null){
                PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
                clientSocketOutput.println("Hello world !");
                clientSocketOutput.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
