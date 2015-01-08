package fr.epsi.server.client;

import org.joda.time.DateTime;

import java.net.Socket;
import java.util.Queue;

public class Client {
    private String username;
    private Socket socket;
    private DateTime connectionTime;
    //private Queue<Command> commandQueue;

    public Client() {
    }

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.connectionTime = new DateTime();
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
