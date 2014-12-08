package test.fonctionnel;

import fr.epsi.ServerCore.ServerManager;
import org.junit.Test;

public class ServerTest {
    private ServerManager server;

    @Test
    public void LeServeurCeLanceUnClientDuFichierCeConnecte() throws InterruptedException {
        ClientSimulator simulatedClient = new ClientSimulator();
        simulatedClient.start();

        server = new ServerManager();
        server.loadServer();
        server.startServer();


        while(true){
            System.out.print("");
        }

    }
}
