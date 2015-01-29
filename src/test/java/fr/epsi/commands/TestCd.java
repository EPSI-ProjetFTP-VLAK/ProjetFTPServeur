package fr.epsi.commands;

import fr.epsi.server.client.CommandListenerThread;
import fr.epsi.utils.ConfigOS;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TestCd {
	
    private CommandListenerThread commandListenerThread;

    @Before
    public void setUp() throws IOException {
    	
    	String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

        Socket mockedClientSocket = Mockito.mock(Socket.class);

        InputStream InputStream = new ByteArrayInputStream("cd::--::/testCd".getBytes(StandardCharsets.UTF_8));
        OutputStream mockedOutputStream = new ByteArrayOutputStream();

        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        Mockito.doReturn(mockedOutputStream).when(mockedClientSocket).getOutputStream();

        commandListenerThread = new CommandListenerThread(mockedClientSocket, urlTestDirectory);
    }

    @Test
    public void ClientIsWellDeplaced() {
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        try {
            commandListenerThread.startThread();
            Thread.sleep(1000);
            commandListenerThread.stopThread();
            commandListenerThread.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String testDirectory = "EnvTest";
        ConfigOS os = new ConfigOS();
        String urlTestDirectory = os.getUrlEnv(testDirectory);

        System.out.println(commandListenerThread.locationOfTheClientOnTheServer());
        assertEquals(commandListenerThread.locationOfTheClientOnTheServer().replace("\\", "/"), urlTestDirectory + "/testCd");
        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());

    }
}
