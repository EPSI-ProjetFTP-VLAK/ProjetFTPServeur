package fr.epsi.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class TestLs {
    private ICommand command;
    private Socket mockedClientSocket;

    @Before
    public void setup() throws IOException {
        String testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        mockedClientSocket = Mockito.mock(Socket.class);
        String command = "ls";
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
        Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        this.command = new Ls(mockedClientSocket);
        this.command.setSourcePath(testEnvironementPath);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory() throws IOException {
        this.command.execCommand();
        assertTrue(!this.command.result().equals("empty-folder"));
    }
}
