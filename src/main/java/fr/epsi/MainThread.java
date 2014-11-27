package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class MainThread
{
    public static int PORT = 4000;

    public static void main(String[] args)
    {
        ServerSocket serverSocket;
        List<Thread> clientList = new ArrayList<Thread>();

        System.out.println("Démarrage du serveur de fichier...");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré...");


            Thread connectClientThread = new Thread(new ClientConnection(serverSocket));
            connectClientThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
