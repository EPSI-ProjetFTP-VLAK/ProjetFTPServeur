package fr.epsi.commands;

import fr.epsi.commands.Command.Error;
import fr.epsi.commands.Core.CommandData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.Socket;

import static org.junit.Assert.*;


public class TestError {
    private Error commande;
    private Socket mockedClientSocket;
    private CommandData mockedCommandData;

    @Before
    public void setUp(){
        String os = System.getProperty("os.name");
        String testEnvironementPath = "";

        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }

        String command = "fakeeeeee : !a ad av535";
        mockedClientSocket = Mockito.mock(Socket.class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );

        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedCommandData = new CommandData(command, testEnvironementPath, mockedClientSocket);

        commande = new Error(mockedCommandData);
    }

    @Test
    public void test(){
        commande.execCommand();
        assertTrue(commande.result().equals("error"));
    }
}
