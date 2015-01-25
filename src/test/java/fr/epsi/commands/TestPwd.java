package fr.epsi.commands;

import fr.epsi.commands.Command.Error;
import fr.epsi.commands.Command.Pwd;
import fr.epsi.commands.Core.CommandData;
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
    private String testEnvironementPath;

    @Before
    public void setUp(){
        String os = System.getProperty("os.name");

        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }

        String command = "pwd::--::";
        mockedClientSocket = Mockito.mock(Socket.class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );

        Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

        mockedCommandData = new CommandData(command, testEnvironementPath, mockedClientSocket);

        commande = new Pwd(mockedCommandData);
    }

    @Test
    public void test(){
        commande.execCommand();
        assertTrue(commande.result().equals(testEnvironementPath));
    }
}
