package fr.epsi.server.client;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.Socket;

import static org.junit.Assert.*;


public class ClientTest {
    private Client mockedClient;
    private Socket mockedClientSocket;

    @Before
    public void setUp(){
        mockedClientSocket = Mockito.mock(Socket.class);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedClient = new Client("test", mockedClientSocket);
    }

    @Test
    public void tcheckEverythingSetUpWhileCreateNewClient(){
        assertTrue(mockedClient.username().equals("test"));
        assertTrue(mockedClient.clientSocket().isConnected());
    }

    
}
