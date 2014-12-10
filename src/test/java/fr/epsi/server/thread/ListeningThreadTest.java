package fr.epsi.server.thread;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.net.ServerSocket;
import java.net.Socket;

public class ListeningThreadTest extends TestCase {

    private ServerSocket mockedServerSocket;
    private Socket mockedClientSocket;
    private ListeningThread listeningThread;

    @Before
    public void setUp() throws Exception {
        mockedServerSocket = Mockito.mock(ServerSocket.class);
        mockedClientSocket = Mockito.mock(Socket.class);

        listeningThread = new ListeningThread(mockedServerSocket);
    }

    @After
    public void tearDown() throws Exception {
        listeningThread.interrupt();

        assertTrue(mockedServerSocket.isClosed());
    }

    public void testAcceptsNewClient() throws Exception {
        Mockito.when(mockedServerSocket.accept()).thenReturn(mockedClientSocket);
        Mockito.when(mockedServerSocket.isClosed())
                .thenReturn(false)
                .thenReturn(true);

        listeningThread.start();

        listeningThread.join();

        Mockito.verify(mockedServerSocket).accept();
    }
}