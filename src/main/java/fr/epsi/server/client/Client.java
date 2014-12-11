package fr.epsi.server.client;

import org.joda.time.DateTime;

import java.net.Socket;

public class Client {
    private String username;
    private Socket socket;
    private DateTime connectionTime;

    public Client() {
    }

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.connectionTime = new DateTime();
    }
}
