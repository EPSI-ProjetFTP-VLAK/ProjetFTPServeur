package fr.epsi.commandFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class CommandPrintShould {
	
	AllCommands commandManager = new AllCommands();
	TypeCommand command;
	public static String path = "Z:\\application2014-2015\\ftp-serveur\\src\\test\\resources\\images\\";
	
	@Before
	public void initCommandDir(){
		TypeCommand command = CommandFactory.callCommand("dir", path);
		command.run();
	}
	
	@Test
	public void printFolderImages(){
		String[] listFiles={"image1","image2","image3"};
		
	}
	
	

}
