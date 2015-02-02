package fr.epsi.server.client;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.AbstractLogger;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String username;
    private Socket socket;
    private Socket socketUpload;
    private Socket socketDownload;
    private DateTime connectionTime;
    private String locationOnTheServer;
    private CommandListenerThread commandListenerThread;
    private ListenerUpload uploadListenerThread;
    private ListenerDownload downloadListenerThread;

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.connectionTime = new DateTime();

        this.locationOnTheServer = ServerManager.getFTPServer().getServerBaseDirectory();
        this.commandListenerThread = new CommandListenerThread(socket, locationOnTheServer);
        this.commandListenerThread.startThread();
    }

    public String username(){
        return this.username;
    }

    public DateTime connectionTime() {
        return this.connectionTime;
    }

    public Socket clientSocket() {
        return this.socket;
    }

    public void disconnectClient() {
        try {
            this.socket.shutdownOutput();
            this.socket.close();
            this.socketDownload.shutdownOutput();
            this.socketDownload.close();
            this.socketUpload.shutdownOutput();
            this.socketUpload.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractLogger.log("Déconnexion du client " + username);
        commandListenerThread.stopThread();
        uploadListenerThread.stopThread();
        downloadListenerThread.stopThread();
    }

    public void clientSetUploadThread(Socket clientSocket) {
        socketUpload = clientSocket;
        uploadListenerThread = new ListenerUpload(socketUpload, locationOnTheServer);
        uploadListenerThread.startThread();

        System.out.println("Thread upload démmaré socket upload connecté");
    }

    public void clientSetDownloadThread(Socket Socket) {
        socketDownload = Socket;
        downloadListenerThread = new ListenerDownload(socketDownload, locationOnTheServer);
        downloadListenerThread.startThread();
        System.out.println("Thread download démmaré socket download connecté");
    }
}
