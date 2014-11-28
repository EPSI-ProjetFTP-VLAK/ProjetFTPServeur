package fr.epsi;

import java.net.Socket;

public class ClientThread implements Runnable{
    private String username;
    private Socket socket;
    private boolean stop;

    public ClientThread(String username, Socket socket){
        this.socket = socket;
        this.username = username;
        this.stop = false;
    }

    public void run(){
        helloImANewClient();
        while (!stop){

        }
    }

    private void helloImANewClient(){
        System.out.println("Salut je suis un nouveau client authentifiée et mon nom est : " + username);
    }
}
