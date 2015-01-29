package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.Command.Copy;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestCopy {
	
	private Copy commande;

	@Before
	public void setUp(){
		
		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

		String sourceString = "/copy.txt";
		String destinationString =  "/test/copy.txt";
		String command = "rm::--::" + destinationString + "::--::" + sourceString;

		try {
			new File(urlTestDirectory + sourceString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		new File(urlTestDirectory + destinationString).delete();

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

		commande = new Copy(mockedCommandData);
	}
	
	@Test 
	public void fileIsCopyAtPath(){
		assertFalse(commande.destinationDirectory().exists());
		commande.execCommand();
		assertTrue(commande.destinationDirectory().exists());
	}
	
	 @Test
	 public void outputReturnIsOk(){
		 commande.destinationDirectory().delete();
		 assertFalse(commande.destinationDirectory().exists());
		 commande.execCommand();
		 assertTrue(commande.result().equals("cp : OK"));
	 }
	
	@After
	public void deleteFileCopied(){
		commande.destinationDirectory().delete();
		commande.sourceDirectory().delete();
	}
}
