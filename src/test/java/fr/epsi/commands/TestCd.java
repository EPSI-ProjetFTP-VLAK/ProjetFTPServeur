package fr.epsi.commands;

import fr.epsi.server.client.CommandListenerThread;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TestCd {
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

        InputStream InputStream = new ByteArrayInputStream("cd::--::/testCd".getBytes(StandardCharsets.UTF_8));
        OutputStream mockedOutputStream = new ByteArrayOutputStream();

        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        Mockito.doReturn(mockedOutputStream).when(mockedClientSocket).getOutputStream();

        commandListenerThread = new CommandListenerThread(mockedClientSocket, testEnvironementPath);
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

        String os = System.getProperty("os.name");
        String testEnvironementPath = "";

        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }

        System.out.println(commandListenerThread.locationOfTheClientOnTheServer());
        assertEquals(commandListenerThread.locationOfTheClientOnTheServer().replace("\\", "/"), testEnvironementPath + "/testCd");
        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());

    }
}
