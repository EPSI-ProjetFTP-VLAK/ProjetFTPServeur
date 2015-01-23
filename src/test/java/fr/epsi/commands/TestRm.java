package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.CommandData;
import fr.epsi.commands.Ls;
import fr.epsi.commands.Rm;
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
		String os = System.getProperty("os.name");
		String testEnvironementPath = "";

		if(os.contains("Windows")){
			testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
		}else{
			testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
		}

		String destinationString =  "/doc.txt";
		String command = "rm::--::" + destinationString;

		try {
			new File(testEnvironementPath + destinationString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
		Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		mockedCommandData = new CommandData(command, testEnvironementPath, mockedClientSocket);

		commande = new Rm(mockedCommandData);
	}
	
	@Test
	public void deleteFileFromPath(){
		assertTrue(commande.destinationDirectory.exists());
		commande.execCommand();
		assertFalse(commande.destinationDirectory.exists());
	}

	@Test
	public void SocketReturnIsOkay(){
		assertTrue(commande.destinationDirectory.exists());
		commande.execCommand();
		assertFalse(commande.destinationDirectory.exists());
		assertTrue(commande.result().equals("rm : OK"));
	}
	
}
