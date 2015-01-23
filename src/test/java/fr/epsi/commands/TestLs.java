package fr.epsi.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class TestLs {
    private Ls command;
    private Socket mockedClientSocket;
    private CommandData mockedCommandData;

    @Before
    public void setup() throws IOException {
        String os = System.getProperty("os.name");
        System.out.println(os);
        String testEnvironementPath = "";

        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }


        mockedClientSocket = Mockito.mock(Socket.class);
        String command = "ls::--::";
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
        Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedCommandData = new CommandData("ls::--::", testEnvironementPath, mockedClientSocket);

        this.command = new Ls(mockedCommandData);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory() throws IOException {
        this.command.execCommand();
        assertTrue(!this.command.result().equals("empty-folder"));
    }
}
