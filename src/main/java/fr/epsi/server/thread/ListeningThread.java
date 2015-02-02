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
                sendNewClientsToAuthentification();
            } catch (IOException e) {
                exceptionCatch=true;
                e.printStackTrace();
            }finally {
                if(exceptionCatch)
                    AbstractLogger.log("socket serveur fermé !\n");
            }

            log();
        }
    }

    private void log() {

    }

    private void sendNewClientsToAuthentification() throws IOException {
        Socket clientSocket = serverSocket.accept();
        sendAcceptationMessage(clientSocket);

        AuthenticationThread authenticationThread = new AuthenticationThread(clientSocket);
        authenticationThread.startThread();

        AbstractLogger.log("Connexion Principale : nouvelle hôte " + clientSocket.getInetAddress().getHostAddress());
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
