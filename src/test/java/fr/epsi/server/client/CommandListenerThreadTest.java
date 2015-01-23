package fr.epsi.server.client;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CommandListenerThreadTest{
    private CommandListenerThread commandListenerThread;
    private Socket mockedClientSocket;

    @Before
    public void setUp() throws IOException {
        mockedClientSocket = Mockito.mock(Socket.class);

        InputStream InputStream = new ByteArrayInputStream("ls::--::".getBytes(StandardCharsets.UTF_8));
        OutputStream mockedOutputStream = new ByteArrayOutputStream();

        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        Mockito.doReturn(mockedOutputStream).when(mockedClientSocket).getOutputStream();

        commandListenerThread = new CommandListenerThread(mockedClientSocket, "D:/FTPFolder");
    }

    @Test
    public void canInterceptCommand() throws InterruptedException {
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.start();
        commandListenerThread.join();
        commandListenerThread.stopListener();

        assertEquals(1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void dontInterceptMalformedCommand() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("i'm a fake ::--:: command !".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.start();
        commandListenerThread.stopListener();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());
    }
}
