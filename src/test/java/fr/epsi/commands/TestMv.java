package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.Command.Mv;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestMv {
	
	private Mv commande;


	@Before
	public void setUp(){
		
		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

		String sourceString = "/move.txt";
		String destinationString =  "/test/move.txt";
		String command = "rm::--::" + destinationString + "::--::" + sourceString;

		try {
			new File(urlTestDirectory + sourceString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!new File(urlTestDirectory + "/test").exists())
            new File(urlTestDirectory + "/test").mkdir();

		Socket mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );

		try {
			Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		CommandData mockedCommandData = new CommandData(command, urlTestDirectory, mockedClientSocket);

		commande = new Mv(mockedCommandData);
	}
	
	@Test
	public void fileIsMoveInPath() {
		assertTrue(commande.sourceDirectory().exists());
		commande.execCommand();
		assertTrue(commande.destinationDirectory().exists());
		assertFalse(commande.sourceDirectory().exists());
	}

	@Test
	public void SocketReturnIsOkay(){
		assertTrue(commande.sourceDirectory().exists());
		commande.execCommand();
		assertFalse(commande.sourceDirectory().exists());
		assertTrue(commande.destinationDirectory().exists());
		assertTrue(commande.result().equals("mv : OK"));
	}
	
	@After
	public void initFilesInDirectory(){
		commande.destinationDirectory().delete();
	}
}
