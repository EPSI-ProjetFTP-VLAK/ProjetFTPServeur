package fr.epsi.commandFactory;

public class FormattedDataFromCommand {
	
	public static void formatted(String dataFiles){
		String[] everyData = dataFiles.split("\n");
		String[] everyFiles = null;
		for(int i=5; i < everyData.length-2; i++)
		{
			everyFiles[i-5]=everyData[i];
		}
	}

}
