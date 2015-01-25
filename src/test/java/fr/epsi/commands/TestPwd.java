package fr.epsi.commands;

import fr.epsi.commands.Command.Pwd;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.Socket;

import static org.junit.Assert.*;


public class TestPwd {
    private Pwd commande;
    private Socket mockedClientSocket;
    private CommandData mockedCommandData;
    private String urlTestDirectory;

    @Before
    public void setUp(){
    	
		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	urlTestDirectory = os.getUrlEnv(testDirectory);

        String command = "pwd::--::";
        mockedClientSocket = Mockito.mock(Socket.class);
        new ByteArrayInputStream( command.getBytes() );

        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedCommandData = new CommandData(command, urlTestDirectory, mockedClientSocket);

        commande = new Pwd(mockedCommandData);
    }

    @Test
    public void test(){
        commande.execCommand();
        assertTrue(commande.result().equals(urlTestDirectory));
    }
}
