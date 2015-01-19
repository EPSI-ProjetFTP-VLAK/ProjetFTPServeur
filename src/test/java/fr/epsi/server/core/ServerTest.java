package fr.epsi.server.core;

import fr.epsi.server.client.Client;
import fr.epsi.server.thread.ListeningThread;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest extends TestCase {

    private Server server;
    private ServerSocket mockedServerSocket;
    private Socket mockedClientSocket;
    private ListeningThread mockedListeningThread;

    @Before
    public void setUp() throws Exception {
        server = new Server();

        mockedServerSocket = Mockito.mock(ServerSocket.class);
        mockedClientSocket = Mockito.mock(Socket.class);
        mockedListeningThread = Mockito.mock(ListeningThread.class);
    }

    @After
    public void tearDown() throws Exception {
        server.stopServer();

        assertTrue(server.getServerSocket().isClosed());
    }

    public void testListensToClients() throws Exception {
        assertFalse(server.getServerSocket().isClosed());

        server.setListeningThread(mockedListeningThread);
        server.startServer();

        Mockito.verify(mockedListeningThread).start();

        assertFalse(server.getServerSocket().isClosed());
    }

    public void testAddClientToList() throws Exception {
        assertTrue(server.getClients().isEmpty());
        Client newClient = new Client("Vive le reggae", mockedClientSocket);
        server.addClient(newClient);
        assertEquals(1, server.getClients().size());
    }
}