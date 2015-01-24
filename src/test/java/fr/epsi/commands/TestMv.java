package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.epsi.commands.Command.Mv;
import fr.epsi.commands.Core.CommandData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestMv {
	
	private Mv commande;
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

		String sourceString = "/move.txt";
		String destinationString =  "/test/move.txt";
		String command = "rm::--::" + destinationString + "::--::" + sourceString;

		try {
			new File(testEnvironementPath + sourceString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
