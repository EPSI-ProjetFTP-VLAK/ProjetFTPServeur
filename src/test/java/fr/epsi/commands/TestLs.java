package fr.epsi.commands;

import fr.epsi.commands.Command.Ls;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class TestLs {
    private Ls command;

    @Before
    public void setup() throws IOException {
    	
    	String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

        Socket mockedClientSocket = Mockito.mock(Socket.class);
        String command = "ls::--::";
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
        Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        CommandData mockedCommandData = new CommandData("ls::--::", urlTestDirectory, mockedClientSocket);

        this.command = new Ls(mockedCommandData);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory() throws IOException {
        this.command.execCommand();
        assertTrue(!this.command.result().equals("empty-folder"));
    }
}
