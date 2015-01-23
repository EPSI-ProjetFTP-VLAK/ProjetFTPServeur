/*
import java.io.File;
import java.io.IOException;

import fr.epsi.commands.Copy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCopy {
	
	private Copy commande;
	private File fileSource, fileDestination;
	
	@Before
	public void setUp(){
		
		String destinationString = "Z:/application2014-2015/serveurFTP/target/test-classes/test/testCopie.txt";
		String sourceString = "Z:/application2014-2015/serveurFTP/target/test-classes/testCopie.txt";
		commande = new Copy();
		fileSource = new File(sourceString);
		fileDestination = new File(destinationString);

		commande.setSourcePath(sourceString);
		commande.setDestinationPath(destinationString);
		try {
			fileSource.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void fileIsCopyAtPath(){
		assertFalse(commande.destinationDirectory.exists());
		commande.execCommand();
		assertTrue(commande.destinationDirectory.exists());
	}
	
	 @Test
	 public void outputReturnIsOk(){
		 fileDestination.delete();
		 assertFalse(commande.destinationDirectory.exists());
		 commande.execCommand();
		 assertTrue(commande.result().equals("cp : OK"));
	 }
	
	@After
	public void deleteFileCopied(){
		fileDestination.delete();
		fileSource.delete();
	}

}*/
