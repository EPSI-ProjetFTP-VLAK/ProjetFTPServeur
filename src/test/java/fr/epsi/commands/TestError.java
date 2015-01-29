package fr.epsi.commands;

import fr.epsi.commands.Command.Error;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.Socket;

import static org.junit.Assert.*;


public class TestError {
    private Error commande;

    @Before
    public void setUp(){
    	
    	String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

        String command = "fakeeeeee : !a ad av535";
        Socket mockedClientSocket = Mockito.mock(Socket.class);
        new ByteArrayInputStream( command.getBytes() );

        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        CommandData mockedCommandData = new CommandData(command, urlTestDirectory, mockedClientSocket);

        commande = new Error(mockedCommandData);
    }

    @Test
    public void test(){
        commande.execCommand();
        assertTrue(commande.result().equals("error"));
    }
}
