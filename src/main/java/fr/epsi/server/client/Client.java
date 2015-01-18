package fr.epsi.server.client;

import fr.epsi.server.core.Server;
import fr.epsi.server.core.ServerManager;
import org.joda.time.DateTime;

import java.net.Socket;
import java.util.Queue;

public class Client {
    private String username;
    private Socket socket;
    private DateTime connectionTime;
    private CommandListenerThread commandListenerThread;

    public Client() {
    }

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.connectionTime = new DateTime();
        this.commandListenerThread = new CommandListenerThread(socket);
        this.commandListenerThread.start();
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
}
