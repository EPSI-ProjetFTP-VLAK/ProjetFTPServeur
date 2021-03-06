package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Command.Rm;
import fr.epsi.utils.ConfigOS;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;


public class TestRm {
	private Rm commande;
	private Socket mockedClientSocket;
	private CommandData mockedCommandData;
	
	@Before
	public void setUp() throws IOException {
	
		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);
    	
		String destinationString =  "/doc.txt";
		String command = "rm::--::" + destinationString;

		try {
			new File(urlTestDirectory + destinationString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
		Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		mockedCommandData = new CommandData(command, urlTestDirectory, mockedClientSocket);

		commande = new Rm(mockedCommandData);
	}
	
	@Test
	public void deleteFileFromPath(){
		assertTrue(commande.destinationDirectory().exists());
		commande.execCommand();
		assertFalse(commande.destinationDirectory().exists());
	}

	@Test
	public void SocketReturnIsOkay(){
		assertTrue(commande.destinationDirectory().exists());
		commande.execCommand();
		assertFalse(commande.destinationDirectory().exists());
		assertTrue(commande.result().equals("rm : OK"));
	}
	
}
