package fr.epsi.server.client;

import fr.epsi.server.core.Server;
import fr.epsi.server.core.ServerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.Socket;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServerManager.class)
public class ClientTest {
    private Client mockedClient;
    private static ServerManager mockedServerManager;

    @Before
    public void setUp(){
        Server mockedServer = Mockito.mock(Server.class);
        Mockito.when(mockedServer.getServerBaseDirectory()).thenReturn("D:/FTPServer");


        Socket mockedClientSocket = Mockito.mock(Socket.class);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        PowerMockito.mockStatic(ServerManager.class);
        PowerMockito.when(ServerManager.getFTPServer()).thenReturn(mockedServer);

        mockedClient = new Client("test", mockedClientSocket);
    }

    @Test
    public void tcheckEverythingSetUpWhileCreateNewClient(){
        assertTrue(mockedClient.username().equals("test"));
        assertTrue(mockedClient.clientSocket().isConnected());
    }

    
}
