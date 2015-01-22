package fr.epsi.commands;

public class Mkdir extends MasterCommand {
	
	private boolean createDirectory = false;

	public Mkdir(CommandData commandData){
		super(commandData);
	}

	 @Override
    public void execCommand(){
		if(!destinationDirectory.exists())
			 this.createDirectory = destinationDirectory.mkdir();
	 }
	 
	 public boolean getFile(){
		 return createDirectory;
	 }


}


