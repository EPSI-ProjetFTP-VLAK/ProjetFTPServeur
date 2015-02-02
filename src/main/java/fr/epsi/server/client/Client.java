package fr.epsi.server.client;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.AbstractLogger;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String username;
    private Socket socket;
    private DateTime connectionTime;
    private String locationOnTheServer;
    private CommandListenerThread commandListenerThread;

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.connectionTime = new DateTime();

        this.locationOnTheServer = ServerManager.getFTPServer().getServerBaseDirectory();
        this.commandListenerThread = new CommandListenerThread(socket, locationOnTheServer);
        this.commandListenerThread.startThread();
    }

    public String locationOnTheServer() { return this.locationOnTheServer; }

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
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractLogger.log("Déconnexion du client " + username);
        commandListenerThread.stopThread();
    }
}
