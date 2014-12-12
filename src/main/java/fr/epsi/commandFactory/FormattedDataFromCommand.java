package fr.epsi.commandFactory;

public class FormattedDataFromCommand {
	
	public static String[][] formatted(String dataFiles){
		String[] everyData = dataFiles.split("\n");
		String[] everyFiles = new String[everyData.length-8];
		for(int i=6; i < everyData.length-2; i++)
		{
			everyFiles[i-6]=everyData[i];
		}
		
		String[][] lineFormatted = new String[everyFiles.length][4];
		
		for(int i=0; i<everyFiles.length; i++)
		{
			String[] lineWithoutSpaces = everyFiles[i].split(" ");
			lineFormatted[i][0] = lineWithoutSpaces[0]; //date
			lineFormatted[i][1] = lineWithoutSpaces[2]; //hour
			if(lineWithoutSpaces[6].trim().equals("<DIR>"))
			{
				lineFormatted[i][2] = lineWithoutSpaces[6].trim(); //contain "<DIR>" 
				lineFormatted[i][3] = lineWithoutSpaces[lineWithoutSpaces.length-1]; //folder name
			}
			else
			{
				lineFormatted[i][2] = lineWithoutSpaces[lineWithoutSpaces.length-2]; //file size
				lineFormatted[i][3] = lineWithoutSpaces[lineWithoutSpaces.length-1]; //filename
			}
			
		}
		
		return lineFormatted;
		
	}

}
