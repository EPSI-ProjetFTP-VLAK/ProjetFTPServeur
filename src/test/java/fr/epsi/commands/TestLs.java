package fr.epsi.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class TestLs {
    private ICommand command;
    private Socket mockedClientSocket;
    private CommandData mockedCommandData;

    @Before
    public void setup() throws IOException {
        String testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        mockedClientSocket = Mockito.mock(Socket.class);
        String command = "ls";
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
        Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedCommandData = new CommandData("ls-", mockedClientSocket);

        this.command = new Ls(mockedCommandData);
        this.command.setSourcePath(testEnvironementPath);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory() throws IOException {
        this.command.execCommand();
        assertTrue(!this.command.result().equals("empty-folder"));
    }
}
