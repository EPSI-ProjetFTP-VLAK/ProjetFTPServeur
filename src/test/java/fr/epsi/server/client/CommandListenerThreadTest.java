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
        String os = System.getProperty("os.name");
        String testEnvironementPath = "";

        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }

        mockedClientSocket = Mockito.mock(Socket.class);

        InputStream InputStream = new ByteArrayInputStream("ls::--::".getBytes(StandardCharsets.UTF_8));
        OutputStream mockedOutputStream = new ByteArrayOutputStream();

        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        Mockito.doReturn(mockedOutputStream).when(mockedClientSocket).getOutputStream();

        commandListenerThread = new CommandListenerThread(mockedClientSocket, testEnvironementPath);
    }

    @Test
    public void canInterceptCommand() throws InterruptedException {
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(1000);
        commandListenerThread.stopThread();
        commandListenerThread.join();


        assertEquals(1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void dontInterceptMalformedCommand() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("i'm a fake ::--:: command !".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(1000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());
    }
}
