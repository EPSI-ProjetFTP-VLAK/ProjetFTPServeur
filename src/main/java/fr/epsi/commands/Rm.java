package fr.epsi.commands;

public class Rm extends MasterCommand {
	private String prefixAnswer = "rm : ";

	public Rm(CommandData commandData){
		super(commandData);
	}

	@Override
	public void execCommand(){
		destinationDirectory.delete();
	}

	@Override
	public String result(){
		return fileAsBeenDeleted();
	}

	public String fileAsBeenDeleted() {
		return (!destinationDirectory.exists()) ? prefixAnswer + "OK" : prefixAnswer + "NOK";
	}
}
