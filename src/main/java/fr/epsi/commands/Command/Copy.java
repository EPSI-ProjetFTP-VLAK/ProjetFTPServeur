package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Copy extends MasterCommand {
	private String prefixAnswer = "cp : ";
	private Path fileCopied;
	
	public Copy(CommandData commandData){
		super(commandData);
	}

	public String copyFile() {
		return (Files.exists(fileCopied)) ? prefixAnswer + "OK" : prefixAnswer + "NOK";
	}
	
	@Override
    public void execCommand() {
		try {
			fileCopied = Files.copy(sourceDirectory.toPath(), destinationDirectory.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			isExecuted = true;
		}
	}
	
	@Override
	public String result(){
		return copyFile();
	}
}
