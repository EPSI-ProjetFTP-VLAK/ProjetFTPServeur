package fr.epsi.server.thread;

import fr.epsi.server.client.Client;
import fr.epsi.server.core.Server;
import fr.epsi.server.core.ServerManager;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.naming.ldap.SortKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServerManager.class)
public class AuthenticationThreadTest extends TestCase {

    private AuthenticationThread authenticationThread;
    private Server mockedServer;
    private List<Client> mockedClients;
    private Socket mockedClientSocket;
    private static ServerManager mockedServerManager;

    @Before
    public void setUp() throws Exception {
        mockedClients = new ArrayList<Client>();

        mockedServer = Mockito.spy(new Server());

        mockedClientSocket = Mockito.mock(Socket.class);
        mockedServer.setClients(mockedClients);

        PowerMockito.mockStatic(ServerManager.class);
        PowerMockito.when(ServerManager.getFTPServer()).thenReturn(mockedServer);

        authenticationThread = new AuthenticationThread(mockedClientSocket);

        mockedServerManager = Mockito.mock(ServerManager.class);
    }

    @Override
    public void tearDown() throws Exception {
        //mockedServer.stopServer();
    }

    public void testAuthenticationSuccess() throws Exception {
        InputStream mockedInputStream = new ByteArrayInputStream("test test".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedInputStream).when(mockedClientSocket).getInputStream();

        ByteArrayOutputStream mockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockedClientSocket.getOutputStream()).thenReturn(mockedOutputStream);

        assertEquals(0, ServerManager.getFTPServer().getClients().size());

        authenticationThread.start();
        authenticationThread.join();

        assertEquals(1, ServerManager.getFTPServer().getClients().size());

        assertTrue(mockedOutputStream.toString().contains("AUTH : OK"));
    }

    public void testAuthenticationError() throws Exception {
        InputStream mockedInputStream = new ByteArrayInputStream("test fail".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedInputStream).when(mockedClientSocket).getInputStream();

        ByteArrayOutputStream mockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockedClientSocket.getOutputStream()).thenReturn(mockedOutputStream);

        assertEquals(0, ServerManager.getFTPServer().getClients().size());

        authenticationThread.start();
        authenticationThread.join();

        assertEquals(0, ServerManager.getFTPServer().getClients().size());

        assertTrue(mockedOutputStream.toString().contains("AUTH : NOK"));
    }

    public void testDoubleAuthenticationError() throws Exception {
        InputStream mockedInputStream = new ByteArrayInputStream("test test".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedInputStream).when(mockedClientSocket).getInputStream();

        ByteArrayOutputStream mockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockedClientSocket.getOutputStream()).thenReturn(mockedOutputStream);

        assertEquals(0, ServerManager.getFTPServer().getClients().size());

        authenticationThread.start();
        authenticationThread.join();
        authenticationThread.interrupt();

        assertEquals(1, ServerManager.getFTPServer().getClients().size());
        assertTrue(mockedOutputStream.toString().contains("AUTH : OK"));

        Socket anotherMockedSocket = Mockito.mock(Socket.class);
        InputStream AnothermockedInputStream = new ByteArrayInputStream("test test".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(AnothermockedInputStream).when(anotherMockedSocket).getInputStream();
        authenticationThread = new AuthenticationThread(anotherMockedSocket);
        ByteArrayOutputStream anotherMockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(anotherMockedSocket.getOutputStream()).thenReturn(anotherMockedOutputStream);


        authenticationThread.start();
        authenticationThread.join();
        authenticationThread.interrupt();

        assertEquals(1, ServerManager.getFTPServer().getClients().size());
        assertTrue(anotherMockedOutputStream.toString().contains("AUTH : NOK"));
    }
}