/*package fr.epsi.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestMkdir {
	
	private Mkdir command;
	 @Before
	    public void setup() throws URISyntaxException{

		 	URL testEnvironnementFolderURL =  this.getClass().getClassLoader().getResource("EnvTest");
	        File testEnironnementFolder = new File(testEnvironnementFolderURL.toURI());
	        String testEnvironnementPath = testEnironnementFolder.toString();
	        
	        this.command = new Mkdir();
	        this.command.setSourcePath(testEnvironnementPath+"\\titi");
	    }
	 
	 @Test
	 public void createDirectory(){
		 this.command.execCommand();
		 assertTrue(this.command.getFile());
	 }

}
*/