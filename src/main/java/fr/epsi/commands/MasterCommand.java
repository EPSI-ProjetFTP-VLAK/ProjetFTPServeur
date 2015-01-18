package fr.epsi.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MasterCommand implements ICommand{
    private Path sourcePath;
    private File sourceDirectory;

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
    public String execCommand() {
        return "";
    }
}
