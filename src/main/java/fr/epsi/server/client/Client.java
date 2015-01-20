package fr.epsi.server.client;
import fr.epsi.server.core.ServerManager;
import org.joda.time.DateTime;

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
        this.commandListenerThread = new CommandListenerThread(socket);
        this.commandListenerThread.start();
        this.locationOnTheServer = ServerManager.getFTPServer().getServerBaseDirectory();
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
}
