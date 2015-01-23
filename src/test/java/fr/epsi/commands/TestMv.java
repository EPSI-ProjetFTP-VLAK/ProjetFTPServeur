/*import java.io.File;
import java.io.IOException;

import fr.epsi.commands.Mv;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMv {
	
	private Mv commande;
	private String sourceString = "Z:/application2014-2015/serveurFTP/target/test-classes/testCopie.txt";
	private String destinationString = "Z:/application2014-2015/serveurFTP/target/test-classes/test/testCopie.txt";

	
	@Before
	public void setUp(){
		
		commande = new Mv();
		commande.setSourcePath(sourceString);
		commande.setDestinationPath(destinationString);
		try {
			new File(sourceString).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void fileIsMoveInPath(){
		assertTrue(commande.sourceDirectory.exists());
		commande.execCommand();
		assertTrue(commande.destinationDirectory.exists());
		assertFalse(commande.sourceDirectory.exists());
	}
	
	@After
	public void initFilesInDirectory(){
		new File(sourceString).delete();
	}
}*/
