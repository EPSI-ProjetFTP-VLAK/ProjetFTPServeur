package fr.epsi.commands;

import java.io.File;

public class Ls extends MasterCommand{
    private File[] listFiles;

    @Override
    public void execCommand() {
        listFiles = sourceDirectory().listFiles();
    }

    public File[] getFiles() {
        return listFiles;
    }
}
