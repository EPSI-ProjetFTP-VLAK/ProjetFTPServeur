package fr.epsi.utils;
import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractLoggerTest {
	
	private String url;

	@Before
	public void setup() throws IOException {
		ConfigOS os = new ConfigOS();
		String fileLogs = "/logs.txt";
		url = os.getUrl(fileLogs);
	}
	
	@Test
	public void logsIsPresentInFile(){
		
		File file = new File(url);
		AbstractLogger.log("Bonjour utilisateur");

			assertTrue(file.exists());
			assertEquals(AbstractLogger.readLogInFile().substring(AbstractLogger.logPrefix().length()),"Bonjour utilisateur");
		
	}

}
