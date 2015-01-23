package fr.epsi.commands;

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
		String os = System.getProperty("os.name");
		String testEnvironementPath = "";

		String testDirectoryName = "testMkdir/";
		String command = "mkdir::--::" + testDirectoryName;

		if(os.contains("Windows")){
			testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
		}else{
			testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
		}

		mockedClientSocket = Mockito.mock(Socket.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream( command.getBytes() );
		Mockito.when(mockedClientSocket.getInputStream()).thenReturn(inputStream);
		Mockito.when(mockedClientSocket.isConnected()).thenReturn(true);

		mockedCommandData = new CommandData(command, testEnvironementPath,  mockedClientSocket);

		this.command = new Mkdir(mockedCommandData);
	}

	@After
	public void tearDown(){
		String fileToDelete = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6) + "/testMkdir";
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
