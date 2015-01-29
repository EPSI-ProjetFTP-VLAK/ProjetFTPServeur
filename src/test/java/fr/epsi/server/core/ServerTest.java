package fr.epsi.server.core;

import fr.epsi.server.client.Client;
import fr.epsi.server.thread.ListeningThread;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.ServerSocket;
import java.net.Socket;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServerManager.class)
public class ServerTest extends TestCase {

    private Server server;
    private Socket mockedClientSocket;
    private ListeningThread mockedListeningThread;
    private static ServerManager mockedServerManager;

    @Before
    public void setUp() throws Exception {
        server = new Server();

        PowerMockito.mockStatic(ServerManager.class);
        PowerMockito.when(ServerManager.getFTPServer()).thenReturn(server);

        ServerSocket mockedServerSocket = Mockito.mock(ServerSocket.class);
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
        Client newClient = new Client("New Client", mockedClientSocket);
        server.addClient(newClient);
        assertEquals(1, server.getClients().size());
    }
}