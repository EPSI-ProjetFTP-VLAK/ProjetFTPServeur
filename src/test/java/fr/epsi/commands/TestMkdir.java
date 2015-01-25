package fr.epsi.commands;

import fr.epsi.commands.Command.Mkdir;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.utils.ConfigOS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class TestMkdir {
	private Mkdir command;
	private Socket mockedClientSocket;
	private CommandData mockedCommandData;

	@Before
	public void setup() throws IOException {
	
		String testDirectoryName = "testMkdir/";
		String command = "mkdir::--::" + testDirectoryName;

		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String urlTestDirectory = os.getUrlEnv(testDirectory);

		mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
		Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		mockedCommandData = new CommandData(command, urlTestDirectory,  mockedClientSocket);

		this.command = new Mkdir(mockedCommandData);
	}

	@After
	public void tearDown(){
		String testDirectory = "EnvTest";
    	ConfigOS os = new ConfigOS();
    	String url = os.getUrlEnv(testDirectory);

		String fileToDelete = url + "/testMkdir";
		File file = new File(fileToDelete);
		file.delete();
	}

	@Test
	public void canCreateADirectory() throws IOException {
		assertTrue(!this.command.getFile());
		this.command.execCommand();
		assertTrue(this.command.getFile());
	}
}
