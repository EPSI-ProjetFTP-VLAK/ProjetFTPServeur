package fr.epsi.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MasterCommand implements ICommand{
    protected Path sourcePath;
    protected File sourceDirectory;

    protected final String wordDelimiter = "-";

    @Override
    public void setSourcePath(String path){
        this.sourcePath = Paths.get(path);
        setSourceDirectory();
    }

    public void setSourceDirectory(){
      sourceDirectory = new File(sourcePath.toString());
    }

    public File sourceDirectory(){
        return sourceDirectory;
    }

    @Override
    public void execCommand() {
    }

    @Override
    public String result(){
        return "unknow-request";
    }
}
