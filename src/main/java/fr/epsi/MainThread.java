package fr.epsi;

import java.io.IOException;
import java.net.ServerSocket;

public class MainThread
{
    public static void main(String[] args)
    {
        ServerSocket serverSocket;
        XMLParser xmlParser = new XMLParser();
        int PORT = Integer.parseInt(xmlParser.serverPort());

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
