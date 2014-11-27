package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;

public class MainThread
{
    public static int PORT = 4000;

    public static void main(String[] args)
    {
        ServerSocket serverSocket;

        System.out.println("Démarrage du serveur de fichier...");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré...");

            // TODO Liste des clients
            Thread t = new Thread(new ClientConnection(serverSocket));

            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
