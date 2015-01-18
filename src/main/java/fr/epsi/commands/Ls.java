package fr.epsi.commands;

import java.io.File;

public class Ls extends MasterCommand{
    private File[] listFiles;

    @Override
    public String execCommand() {
        return sourceDirectory().listFiles().toString();
    }
}
