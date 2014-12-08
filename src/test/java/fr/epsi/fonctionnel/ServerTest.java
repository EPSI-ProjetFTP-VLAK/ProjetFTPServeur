package fr.epsi.fonctionnel;

import fr.epsi.server.core.ServerManager;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ServerTest {
    private ServerManager server;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream logger = null;

    @Test
    public void LeServeurSeLanceUnClientDuFichierSeConnecte() throws InterruptedException {
        // Mock System.out
        logger = System.out;
        //System.setOut(new PrintStream(outContent));

        logger.println("Launching server...");
        server = new ServerManager();
        server.startServer();
        logger.println("Server launched...");

        logger.println("Starting client simulator...");
        ClientSimulator simulatedClient = new ClientSimulator();
        simulatedClient.start();

        logger.println("Waiting for complete connexion...");
        while (!simulatedClient.isInterrupted()) {}

        logger.println("Connexion ended...");

        // Release mock
        //System.setOut(logger);

        //System.out.println(outContent.toString());
    }
}
