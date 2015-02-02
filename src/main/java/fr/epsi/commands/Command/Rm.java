package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

public class Rm extends MasterCommand {
	private String prefixAnswer = "rm : ";

	public Rm(CommandData commandData){
		super(commandData);
	}

	@Override
	public void execCommand(){
		if(destinationDirectory().exists())
			destinationDirectory.delete();

		isExecuted = true;
	}

	@Override
	public String result(){
		return fileAsBeenDeleted();
	}

	public String fileAsBeenDeleted() {
		return (!destinationDirectory.exists()) ? prefixAnswer + "OK" : prefixAnswer + "NOK";
	}
}
