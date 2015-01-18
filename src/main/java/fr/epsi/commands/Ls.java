package fr.epsi.commands;

import java.io.File;

public class Ls extends MasterCommand{
    private File[] filesList;
    private final String prefixAnswer = "ls" + wordDelimiter + "answer:";
    private final String returnCodeForEmptyFolder = "empty" + wordDelimiter + "folder";

    public String formatedFilesListString(){
        String formatedFilesList = prefixAnswer;

        for(File aFile : this.filesList){
            formatedFilesList += aFile.toString() + wordDelimiter;
        }

        if (formatedFilesList.equals(prefixAnswer)){
            formatedFilesList += returnCodeForEmptyFolder;
        }
        return formatedFilesList;
    }

    @Override
    public void execCommand() {
        this.filesList = sourceDirectory().listFiles();
    }

    @Override
    public String result(){
        return formatedFilesListString();
    }


}
