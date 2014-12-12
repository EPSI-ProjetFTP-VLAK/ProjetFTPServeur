package fr.epsi.commandFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;

public class CommandPrintShould {
	
	AllCommands commandManager = new AllCommands();
	TypeCommand command;
	public static String path = "C:\\Users\\arthur\\git\\ProjectFTPServeurCommands\\ProjetFTPServeur\\src\\test\\resources\\images\\";
	
	@Before
	public void initCommandDir(){
		command = CommandFactory.callCommand("dir", path);
		//command.run();
	}
	
	@Test
	public void printFolderImages(){
		String[] listFiles={"..", "image1","image2","image3"};
		
		String dataFiles = command.run();
		String[][] dataFormatted = FormattedDataFromCommand.formatted(dataFiles);
		
		String[] FilesNameCommand = {dataFormatted[0][3], dataFormatted[1][3], dataFormatted[2][3], dataFormatted[3][3]};//
		
		assertArrayEquals(FilesNameCommand, listFiles);
		
	}
	
	

}
