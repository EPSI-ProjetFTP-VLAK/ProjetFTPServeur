package fr.epsi.server.client;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class CommandListenerThreadTest{
    private CommandListenerThread commandListenerThread;
    private Socket mockedClientSocket;

    @Before
    public void setUp() throws IOException{
        mockedClientSocket = Mockito.mock(Socket.class);
        String command = "ls-";
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
        Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        commandListenerThread = new CommandListenerThread(mockedClientSocket);
        commandListenerThread.start();
    }

    @Test
    public void canInterceptCommand() throws InterruptedException {
        Thread.sleep(500);
        commandListenerThread.stopListener();
        commandListenerThread.join();
        assertEquals(0, commandListenerThread.numberOfCommandCatch());
    }
}
