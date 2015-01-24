package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.Command.Copy;
import fr.epsi.commands.Core.CommandData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestCopy {
	
	private Copy commande;
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

		String sourceString = "/copy.txt";
		String destinationString =  "/test/copy.txt";
		String command = "rm::--::" + destinationString + "::--::" + sourceString;

		try {
			new File(testEnvironementPath + sourceString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		new File(testEnvironementPath + destinationString).delete();

		if(!new File(testEnvironementPath + "/test").exists())
			new File(testEnvironementPath + "/test").mkdir();

		mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );

		try {
			Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		mockedCommandData = new CommandData(command, testEnvironementPath, mockedClientSocket);

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
