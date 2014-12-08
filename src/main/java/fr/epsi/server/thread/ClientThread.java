package fr.epsi.server.thread;

import fr.epsi.utils.AbstractLogger;

import java.net.Socket;

public class ClientThread extends Thread {
    private String username;
    private Socket socket;
    private boolean stop;

    public ClientThread(String username, Socket socket){
        this.socket = socket;
        this.username = username;
        this.stop = false;
    }

    public void run() {
        helloImANewClient();

        interrupt();
    }

    private void helloImANewClient(){
        AbstractLogger.log("Salut je suis un nouveau client authentifiée et mon nom est : " + username);
    }
}
